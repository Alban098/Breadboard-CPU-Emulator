/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.linking;

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
    linkOperations(
        mappingResult.getOperations(),
        mappingResult.getVariables(),
        mappingResult.getConstants(),
        mappingResult.getLabels());
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
   * @param operations a List of {@link MappedOperation} to link
   * @param variables a List of {@link MappedVariable} that will be used to dereference variables
   * @param constants a List of {@link MappedConstant} that will be used to dereference constants
   * @param labels a List of {@link MappedLabel} that will be used to dereference labels
   */
  private void linkOperations(
      List<MappedOperation> operations,
      List<MappedVariable> variables,
      List<MappedConstant> constants,
      List<MappedLabel> labels) {
    LOG.info("Linking instructions");
    for (MappedOperation operation : operations) {
      int line = operation.getSourceFileLine();
      String arg = operation.getToken().getArg();
      Integer parsed = null;
      if (arg != null) {
        if (arg.startsWith("$(")) {
          parsed = handlePlainAddress(arg, line);
        } else if (arg.startsWith("@")) {
          if (operation.getToken().getInstruction().acceptLabels()) {
            parsed = handleLabel(labels, arg, line);
          } else {
            LOG.error(
                "-----> Line {} - Instruction '{}' does not support Label dereference",
                line,
                operation.getToken().getInstruction().getName());
            this.hasErrors = true;
          }
        } else if (arg.startsWith("*")) {
          parsed = handlePlainVariable(variables, arg, line);
        } else if (arg.startsWith("_")) {
          parsed = handlePlainConstant(constants, arg, line);
        } else if (arg.startsWith("$")) {
          parsed = handleDereferenceAlias(variables, constants, arg, line);
        } else if (arg.matches("[0-9A-Fa-f]{1,2}")) {
          parsed = handlePlainValue(arg, line);
        } else {
          LOG.error("-----> Line {} - Malformed argument '{}'", line, arg);
          this.hasErrors = true;
        }
      }
      linkedTokens.add(
          new LinkedOperation(
              operation.getAddress(), operation.getToken().getInstruction(), parsed));
    }
  }

  /**
   * Dereferences an alias that is prefixed with a dereference operator '$' into its actual value or
   * address
   *
   * @param variables a List of {@link MappedVariable} that will be used to dereference variables
   * @param constants a List of {@link MappedConstant} that will be used to dereference constants
   * @param arg the argument to parse should be the variable / constant alias, with a '_' prepended
   *     for constants, prefixed with '$'
   * @param line the line number of the instruction to dereference the argument of, int the source
   *     file
   * @return the parsed value to replace the reference with
   */
  private Integer handleDereferenceAlias(
      List<MappedVariable> variables, List<MappedConstant> constants, String arg, int line) {
    String name = arg.substring(1);
    if (name.startsWith("_")) {
      return handlePlainConstant(constants, name, line);
    }
    return handlePlainVariable(variables, arg, line);
  }

  /**
   * Dereferences a variable alias into its address
   *
   * @param variables a List of {@link MappedVariable} that will be used to dereference the variable
   * @param arg the argument to parse should be the variable alias
   * @param line the line number of the instruction to dereference the argument of, int the source
   *     file
   * @return the parsed address to replace the reference with
   */
  private Integer handlePlainVariable(List<MappedVariable> variables, String arg, int line) {
    String variableName = arg.substring(1);
    MappedVariable variable =
        variables.stream()
            .filter(var -> var.getToken().getAlias().equals(variableName))
            .findFirst()
            .orElse(null);
    if (variable != null) {
      LOG.info(
          "-----> Resolved variable '{}' to address '${}'",
          variableName,
          String.format("%02X", variable.getAddress()));
      return variable.getAddress();
    } else {
      LOG.error("-----> Line {} - Unable to resolve variable '{}'", line, variableName);
      this.hasErrors = true;
      return null;
    }
  }

  /**
   * Parse a hexadecimal string into an Integer value
   *
   * @param arg the argument to parse
   * @param line the line number of the instruction to parse the argument of, int the source file
   * @return the parsed value to replace the reference with
   */
  private Integer handlePlainValue(String arg, int line) {
    try {
      return Integer.parseInt(arg, 16) & 0xFF;
    } catch (NumberFormatException e) {
      LOG.error("-----> Line {} - Unable to resolve value '{}'", line, arg);
    }
    return null;
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
  private Integer handlePlainConstant(List<MappedConstant> constants, String arg, int line) {
    String constantName = arg.substring(1);
    MappedConstant constant =
        constants.stream()
            .filter(cst -> cst.getToken().getAlias().equals(constantName))
            .findFirst()
            .orElse(null);
    if (constant != null) {
      LOG.info(
          "-----> Resolved constant '{}' to value '${}'",
          constantName,
          String.format("%02X", constant.getToken().getValue()));
      return constant.getToken().getValue();
    } else {
      LOG.error("-----> Line {} - Unable to resolve constant '{}'", line, constantName);
      this.hasErrors = true;
      return null;
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
  private Integer handleLabel(List<MappedLabel> labels, String arg, int line) {
    String labelName = arg.substring(1);
    MappedLabel label =
        labels.stream()
            .filter(lbl -> lbl.getToken().getAlias().equals(labelName))
            .findFirst()
            .orElse(null);
    if (label != null) {
      LOG.info("-----> Resolving Label '{}' to address '${}'", labelName, label.getAddress());
      return label.getAddress();
    } else {
      LOG.error("-----> Line {} - Unable to resolve Label '{}'", line, labelName);
      this.hasErrors = true;
      return null;
    }
  }

  /**
   * Parse a hexadecimal string into an Integer address
   *
   * @param arg the argument to parse
   * @param line the line number of the instruction to parse the argument of, int the source file
   * @return the parsed address to replace the reference with, null if unable to parse
   */
  private Integer handlePlainAddress(String arg, int line) {
    try {
      return Integer.parseInt(arg.replaceAll("[$()]", ""), 16) & 0xFF;
    } catch (NumberFormatException e) {
      this.hasErrors = true;
      LOG.error("-----> Line {} - Unable to resolve value '{}'", line, arg);
    }
    return null;
  }
}
