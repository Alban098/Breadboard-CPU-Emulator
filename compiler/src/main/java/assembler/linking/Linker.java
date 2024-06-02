/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.linking;

import assembler.constant.ConstantType;
import assembler.linking.token.LinkedMemoryBlock;
import assembler.linking.token.LinkedOperation;
import assembler.linking.token.LinkedToken;
import assembler.linking.token.LinkedVariable;
import assembler.mapping.Mapper;
import assembler.mapping.MappingResult;
import assembler.mapping.token.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This component will execute the Linking stage of the compilation. It will take all mapped tokens
 * and resolved constants, variables and labels references and replace them with the relevant values
 * or addresses
 */
public class Linker {

  private static final Logger LOG = LoggerFactory.getLogger(Linker.class);

  /** Holds all tokens that have already been linked */
  private final List<LinkedToken> linkedTokens = new ArrayList<>();
  /** Has an error been encountered during this stage */
  private boolean hasErrors = false;

  /**
   * Execute the linking stage of the compilation
   *
   * @param mappingResult the output of the mapping stage, as return by {@link
   *     Mapper#getMappingResult()}
   */
  public void linkTokens(MappingResult mappingResult) {
    linkMemoryBlocks(mappingResult.getMemoryBlocks());
    linkVariables(mappingResult.getVariables());
    linkOperations(mappingResult);
  }

  /**
   * Returns a List of all linked tokens, At this point, they can be written to a binary file to be
   * executed
   *
   * @return a List of all linked tokens
   */
  public List<LinkedToken> getLinkingResult() {
    return linkedTokens;
  }

  /**
   * Returns whether errors have been encountered or not
   *
   * @return whether errors have been encountered or not
   */
  public boolean hasErrors() {
    return hasErrors;
  }

  /**
   * Links memory blocks, no computation needed, this will only convert a {@link MappedMemoryBlock}
   * into a {@link LinkedMemoryBlock} without altering anything
   *
   * @param memoryBlocks a List of {@link MappedMemoryBlock} to link
   */
  private void linkMemoryBlocks(List<MappedMemoryBlock> memoryBlocks) {
    LOG.info("Linking memory blocks");
    for (MappedMemoryBlock memoryBlock : memoryBlocks) {
      int[] data = new int[memoryBlock.getToken().getData().size()];
      for (int i = 0; i < data.length; i++) {
        data[i] = memoryBlock.getToken().getData().get(i) & 0xFF;
      }
      linkedTokens.add(new LinkedMemoryBlock(memoryBlock.getAddress(), data));
    }
  }

  /**
   * Links variables, no computation needed, this will only convert a {@link MappedVariable} into a
   * {@link LinkedVariable} without altering anything
   *
   * @param variables a List of {@link MappedVariable} to link
   */
  private void linkVariables(List<MappedVariable> variables) {
    LOG.info("Linking variables");
    for (MappedVariable variable : variables) {
      linkedTokens.add(new LinkedVariable(variable.getAddress(), variable.getToken().getValue()));
    }
  }

  /**
   * Links operations, every Variable and Label references will be replaced by their actual
   * addresses, Constant references will be replaced by their actual values
   *
   * @param mappingResult The result of the Mapping stage
   */
  private void linkOperations(MappingResult mappingResult) {
    LOG.info("Linking instructions");
    for (MappedOperation operation : mappingResult.getOperations()) {
      int line = operation.getSourceFileLine();
      String arg = operation.getToken().getArg();
      int[] parsed = new int[0];
      if (arg != null && arg.startsWith("@")) {
        if (operation.getToken().getInstruction().acceptLabels()) {
          parsed = handleLabel(mappingResult.getLabels(), arg, line);
        } else {
          LOG.error(
              "-----> Line {} - Instruction '{}' does not support Label dereference",
              line,
              operation.getToken().getInstruction().getName());
          this.hasErrors = true;
        }
      } else {
        switch (operation.getToken().getInstruction().getAddressingMode()) {
          case IMM -> parsed = handleImmediate(line, arg, mappingResult);
          case ABS -> parsed = handleAbsolute(line, arg, mappingResult);
          case Z_P -> parsed = handleZeroPage(line, arg, mappingResult);
          case IMP -> parsed = new int[0];
          case IDX -> parsed = handleIndexed(line, arg, mappingResult);
        }
      }
      linkedTokens.add(
          new LinkedOperation(
              operation.getAddress(), operation.getToken().getInstruction(), parsed));
    }
  }

  private int[] handleImmediate(int line, String arg, MappingResult mappingResult) {
    if (arg.startsWith(("#("))) {
      return new int[] {handlePlainConstant(mappingResult.getConstants(), arg, line, false)};
    } else {
      return new int[] {handlePlainValue(arg, line)};
    }
  }

  private int[] handleAbsolute(int line, String arg, MappingResult mappingResult) {
    int value;
    if (arg.matches("\\$\\([a-zA-Z0-9_-]+\\)")) { // 2 bytes constants
      value = handlePlainConstant(mappingResult.getConstants(), arg.substring(1), line, false);
    } else if (arg.matches(
        "\\$[0-9a-fA-F]{1,2}\\([a-zA-Z0-9_-]+\\)")) { // 1 byte constant as LSB of address
      String[] split = arg.split("\\(");
      int page = handlePlainValue(split[0], line);
      value =
          ((page & 0xFF) << 8)
              | (handlePlainConstant(mappingResult.getConstants(), split[1], line, true) & 0xFF);
    } else if (arg.matches(
        "\\$\\([a-zA-Z0-9_-]+\\)[0-9a-fA-F]{2}")) { // 1 byte constant as MSB of address
      String[] split = arg.split("\\)");
      int offset = handlePlainValue(split[1], line);
      value =
          ((handlePlainConstant(mappingResult.getConstants(), split[0], line, true) & 0xFF) << 8)
              | (offset & 0xFF);
    } else if (arg.matches("[a-zA-Z0-9_-]+")) { // variable
      value = handlePlainVariable(mappingResult.getVariables(), arg, line);
    } else { // raw 2 bytes value
      value = handlePlainAddress(arg, line);
    }
    return new int[] {(value & 0xFF00) >> 8, value & 0x00FF};
  }

  private int[] handleZeroPage(int line, String arg, MappingResult mappingResult) {
    if (arg.startsWith(("$("))) {
      return new int[] {
        handlePlainConstant(mappingResult.getConstants(), arg.substring(1), line, true)
      };
    } else {
      return new int[] {Integer.parseInt(arg.replaceAll("[$()]", ""), 16) & 0xFF};
    }
  }

  private int[] handleIndexed(int line, String arg, MappingResult mappingResult) {
    if (arg.startsWith(("$("))) {
      return new int[] {
        handlePlainConstant(
            mappingResult.getConstants(), arg.substring(1).split(",")[0], line, true)
      };
    } else {
      return new int[] {Integer.parseInt(arg.split(",")[0].replaceAll("[$()]", ""), 16) & 0xFF};
    }
  }

  /**
   * Dereferences a variable alias into its address
   *
   * @param variables a List of {@link MappedVariable} that will be used to dereference the variable
   * @param variableName the argument to parse should be the variable alias
   * @param line the line number of the instruction to dereference the argument of, int the source
   *     file
   * @return the parsed address to replace the reference with
   */
  private int handlePlainVariable(List<MappedVariable> variables, String variableName, int line) {
    MappedVariable variable =
        variables.stream()
            .filter(var -> var.getToken().getAlias().equals(variableName))
            .findFirst()
            .orElse(null);
    if (variable != null) {
      LOG.info(
          "-----> Resolved variable '{}' to address '${}'",
          variableName,
          String.format("%04X", variable.getAddress()));
      return variable.getAddress() & 0xFFFF;
    } else {
      LOG.error("-----> Line {} - Unable to resolve variable '{}'", line, variableName);
      this.hasErrors = true;
      return 0;
    }
  }

  /**
   * Parse a hexadecimal string into an Integer value
   *
   * @param arg the argument to parse
   * @param line the line number of the instruction to parse the argument of, int the source file
   * @return the parsed value to replace the reference with
   */
  private int handlePlainValue(String arg, int line) {
    try {
      arg = arg.replaceAll("[#$]", "");
      return Integer.parseInt(arg, 16) & 0xFF;
    } catch (NumberFormatException e) {
      LOG.error("-----> Line {} - Unable to resolve value '{}'", line, arg);
    }
    return 0;
  }

  /**
   * Dereferences a constant alias into its actual value
   *
   * @param constants a List of {@link MappedConstant} that will be used to dereference the constant
   * @param arg the argument to parse should be the variable alias
   * @param line the line number of the instruction to dereference the argument of, int the source
   *     file
   * @return the parsed value to replace the reference with
   */
  private int handlePlainConstant(
      List<MappedConstant> constants, String arg, int line, boolean enforce8bit) {
    String constantName = arg.replaceAll("[#$()]", "");
    MappedConstant constant =
        constants.stream()
            .filter(cst -> cst.getToken().getAlias().equals(constantName))
            .findFirst()
            .orElse(null);
    if (constant != null) {
      if (enforce8bit && constant.getToken().getType() != ConstantType.BYTE) {
        LOG.error("-----> Line {} - Unable to resolve byte constant '{}'", line, constantName);
        this.hasErrors = true;
        return 0;
      }
      switch (constant.getToken().getType()) {
        case BYTE -> LOG.info(
            "-----> Resolved constant '{}' to value '${}'",
            constantName,
            String.format("%02X", constant.getToken().getValue()));
        case WORD -> LOG.info(
            "-----> Resolved constant '{}' to value '${}'",
            constantName,
            String.format("%04X", constant.getToken().getValue()));
      }

      return constant.getToken().getValue();
    } else {
      LOG.error("-----> Line {} - Unable to resolve constant '{}'", line, constantName);
      this.hasErrors = true;
      return 0;
    }
  }

  /**
   * Dereferences a label alias into its actual address
   *
   * @param labels a List of {@link MappedLabel} that will be used to dereference the label
   * @param arg the argument to parse should be the variable alias
   * @param line the line number of the instruction to dereference the argument of, int the source
   *     file
   * @return the parsed address to replace the reference with
   */
  private int[] handleLabel(List<MappedLabel> labels, String arg, int line) {
    String labelName = arg.substring(1);
    MappedLabel label =
        labels.stream()
            .filter(lbl -> lbl.getToken().getAlias().equals(labelName))
            .findFirst()
            .orElse(null);
    if (label != null) {
      LOG.info("-----> Resolving Label '{}' to address '${}'", labelName, label.getAddress());
      return new int[] {(label.getAddress() & 0xFF00) >> 8, label.getAddress() & 0x00FF};
    } else {
      LOG.error("-----> Line {} - Unable to resolve Label '{}'", line, labelName);
      this.hasErrors = true;
      return new int[0];
    }
  }

  /**
   * Parse a hexadecimal string into an Integer address
   *
   * @param arg the argument to parse
   * @param line the line number of the instruction to parse the argument of, int the source file
   * @return the parsed address to replace the reference with
   */
  private int handlePlainAddress(String arg, int line) {
    try {
      return Integer.parseInt(arg.replaceAll("\\$", ""), 16) & 0xFFFF;
    } catch (NumberFormatException e) {
      this.hasErrors = true;
      LOG.error("-----> Line {} - Unable to resolve value '{}'", line, arg);
    }
    return 0;
  }
}
