/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.tokenizing;

import assembler.Instruction;
import assembler.tokenizing.exception.MalformedAddressException;
import assembler.tokenizing.exception.MalformedValueException;
import assembler.tokenizing.token.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This component will execute the Tokenizer stage of the compilation.
 *
 * <p>Rejects invalid operand
 *
 * <p>Rejects invalid arguments
 */
public class Tokenizer {

  private static final Logger LOG = LoggerFactory.getLogger(Tokenizer.class);

  /** Just a cache to convert from text to actual instructions */
  private static final Map<String, Instruction> INSTRUCTION_MAP = new HashMap<>();

  /*
   * Initializes the instruction cache
   */
  static {
    INSTRUCTION_MAP.put("NOP", Instruction.NOP);
    INSTRUCTION_MAP.put("LDA X", Instruction.LDA_IMM);
    INSTRUCTION_MAP.put("LDA $(X)", Instruction.LDA_ABS);
    INSTRUCTION_MAP.put("LDB X", Instruction.LDB_IMM);
    INSTRUCTION_MAP.put("LDB $(X)", Instruction.LDB_ABS);
    INSTRUCTION_MAP.put("OUT X", Instruction.OUT_IMM);
    INSTRUCTION_MAP.put("OUT $(X)", Instruction.OUT_ABS);
    INSTRUCTION_MAP.put("OUT", Instruction.OUT);
    INSTRUCTION_MAP.put("STA X", Instruction.STA_IMM);
    INSTRUCTION_MAP.put("STA $(X)", Instruction.STA_ABS);
    INSTRUCTION_MAP.put("ADD X", Instruction.ADD_IMM);
    INSTRUCTION_MAP.put("ADD $(X)", Instruction.ADD_ABS);
    INSTRUCTION_MAP.put("SUB X", Instruction.SUB_IMM);
    INSTRUCTION_MAP.put("SUB $(X)", Instruction.SUB_ABS);
    INSTRUCTION_MAP.put("CMP X", Instruction.CMP_IMM);
    INSTRUCTION_MAP.put("CMP $(X)", Instruction.CMP_ABS);
    INSTRUCTION_MAP.put("CMP", Instruction.CMP);
    INSTRUCTION_MAP.put("JMP X", Instruction.JMP_IMM);
    INSTRUCTION_MAP.put("JMP $(X)", Instruction.JMP_ABS);
    INSTRUCTION_MAP.put("JMA", Instruction.JMA);
    INSTRUCTION_MAP.put("BCS X", Instruction.BCS_IMM);
    INSTRUCTION_MAP.put("BCS $(X)", Instruction.BCS_ABS);
    INSTRUCTION_MAP.put("BCC X", Instruction.BCC_IMM);
    INSTRUCTION_MAP.put("BCC $(X)", Instruction.BCC_ABS);
    INSTRUCTION_MAP.put("BEQ X", Instruction.BEQ_IMM);
    INSTRUCTION_MAP.put("BEQ $(X)", Instruction.BEQ_ABS);
    INSTRUCTION_MAP.put("BNE X", Instruction.BNE_IMM);
    INSTRUCTION_MAP.put("BNE $(X)", Instruction.BNE_ABS);
    INSTRUCTION_MAP.put("BMI X", Instruction.BMI_IMM);
    INSTRUCTION_MAP.put("BMI $(X)", Instruction.BMI_ABS);
    INSTRUCTION_MAP.put("BPL X", Instruction.BPL_IMM);
    INSTRUCTION_MAP.put("BPL $(X)", Instruction.BPL_ABS);
    INSTRUCTION_MAP.put("BOS X", Instruction.BOS_IMM);
    INSTRUCTION_MAP.put("BOS $(X)", Instruction.BOS_ABS);
    INSTRUCTION_MAP.put("BOC X", Instruction.BOC_IMM);
    INSTRUCTION_MAP.put("BOC $(X)", Instruction.BOC_ABS);
    INSTRUCTION_MAP.put("HLT", Instruction.HLT);
  }

  /** Holds all Tokens that have been decoded */
  private final TokenizationResult result = new TokenizationResult();
  /** The current mode of the Tokenizer see {@link Mode} */
  private Mode currentMode = Mode.CODE;
  /** The memory block to fill in case we are in {@link Mode#MEMORY_BLOCK} */
  private MemoryBlock currentMemoryBlock;
  /** The last encountered label, waiting to be linked to an operation */
  private Label labelToReference;
  /** The current line inside the source file */
  private int lineIndex = 1; // Initialize at 1 because of mandatory header
  /** Has an error been encountered during this stage */
  private boolean hasErrors = false;

  /**
   * Convert and extract tokens from a line of the source file
   *
   * @param line the line to tokenize
   */
  public void tokenize(String line) {
    lineIndex++;
    tokenizeLine(line, lineIndex);
  }

  /**
   * Returns the result of the tokenizing stage
   *
   * @return the result of the tokenizing stage
   */
  public TokenizationResult getTokenizationResult() {
    return result;
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
   * Tokenize a line,
   *
   * @param line the line to tokenize
   * @param index the index of the current line in the source file
   */
  private void tokenizeLine(String line, int index) {
    String[] tokens = line.split(" ");
    tokens = sanitizeTokens(tokens);

    // skip blank lines
    if (tokens.length == 0) {
      return;
    }
    switch (currentMode) {
      case MEMORY_BLOCK -> fillMemoryBlock(index, tokens);
      case CODE -> {
        if (tokens[0].startsWith(".block")) {
          tokenizeMemoryBlock(index, tokens);
        } else if (tokens[0].startsWith(".var")) {
          tokenizeVariable(index, tokens);
        } else if (tokens[0].startsWith(".const")) {
          tokenizeConstant(index, tokens);
        } else if (tokens[0].startsWith(":")) {
          tokenizeLabel(index, tokens);
        } else if (!tokens[0].isBlank()) {
          tokenizeInstruction(index, tokens);
        }
      }
    }
  }

  /**
   * Cleans up a line, by removing blank tokens, and ignoring comments
   *
   * @param tokens an array of token to sanitize
   * @return an array of sanitized tokens
   */
  private String[] sanitizeTokens(String[] tokens) {
    List<String> sanitized = new ArrayList<>(tokens.length);
    for (String token : tokens) {
      // Ignore everything that is a comment
      if (token.startsWith("//")) {
        break;
      }
      // Ignore blank entries, that appear with 2 consecutive spaces
      if (!token.isBlank()) {
        sanitized.add(token);
      }
    }
    return sanitized.toArray(new String[0]);
  }

  /**
   * Tokenizes an instruction
   *
   * @param line the line index in the source file
   * @param tokens tokens to tokenize
   */
  private void tokenizeInstruction(int line, String[] tokens) {
    String operand = tokens[0];
    String arg = null;
    Instruction decoded;
    if (tokens.length == 2) {
      arg = tokens[1];
      try {
        checkArg(arg);
      } catch (MalformedValueException e) {
        LOG.error("-----> Line {} - Malformed argument '{}'", line, arg);
        this.hasErrors = true;
        return;
      }
      // '$' means we need to dereference the address, aka indirect addressing
      // - for variables this will replace '$var' by '$(addr)'
      // - for constants this will replace '$_const' by '$(val)'
      if (arg.startsWith("$")) {
        operand += " $(X)";
      } else {
        operand += " X";
      }
    }
    decoded = INSTRUCTION_MAP.get(operand);
    if (decoded == null) {
      LOG.error(
          "-----> Line {} - '{}' is not recognized as an instruction, or isn't compatible with the passed argument",
          line,
          tokens[0]);
      this.hasErrors = true;
      return;
    }
    Operation token = new Operation(decoded, arg, line);
    if (labelToReference != null) {
      labelToReference.setReferenceOperation(token);
      labelToReference = null;
    }
    result.add(token);
  }

  /**
   * Return true if the argument is pseudo valid
   *
   * @param arg the argument to check
   * @return true if valid
   * @throws MalformedValueException if the argument is not valid
   */
  private boolean checkArg(String arg) throws MalformedValueException {
    if (arg.startsWith("$(") && arg.matches("\\$\\([0-9A-Fa-f]{1,2}\\)")) {
      return true;
    } else if (arg.startsWith("@") && arg.matches("@[a-zA-Z]+[a-zA-Z0-9_-]*")) {
      return true;
    } else if (arg.startsWith("*") && arg.matches("\\*[a-zA-Z]+[a-zA-Z0-9_-]*")) {
      return true;
    } else if (arg.startsWith("$") && arg.matches("\\$_?[a-zA-Z]+[a-zA-Z0-9_-]*")) {
      return true;
    } else if (arg.startsWith("_") && arg.matches("_[a-zA-Z]+[a-zA-Z0-9_-]*")) {
      return true;
    } else if (arg.matches("[0-9A-Fa-f]{1,2}")) {
      return true;
    }
    throw new MalformedValueException();
  }

  /**
   * Fills a memory block, this is called when in {@link Mode#MEMORY_BLOCK}, interprets tokens as
   * raw data to add to the current memory block
   *
   * @param line the line index in the source file
   * @param tokens tokens to add to the memory block, should be 8 bits hexadecimal values
   */
  private void fillMemoryBlock(int line, String[] tokens) {
    for (String token : tokens) {
      if (token.equals(".endblock")) {
        currentMode = Mode.CODE;
        currentMemoryBlock = null;
        return;
      }
      try {
        int value = Integer.parseInt(token, 16);
        currentMemoryBlock.addData(value);
      } catch (NumberFormatException e) {
        LOG.error("-----> Line {} - '{}' is not a valid hexadecimal value", line, tokens[0]);
        this.hasErrors = true;
      }
    }
  }

  /**
   * Tokenizes a memory block
   *
   * @param line the line index in the source file
   * @param tokens tokens to tokenize
   */
  private void tokenizeMemoryBlock(int line, String[] tokens) {
    if (tokens.length != 2) {
      LOG.error("-----> Line {} - .block must be followed by a memory address '.block $(F0)", line);
      this.hasErrors = true;
      return;
    }
    currentMode = Mode.MEMORY_BLOCK;
    try {
      if (tokens[1].startsWith("$(") && tokens[1].matches("\\$\\([0-9A-Fa-f]{1,2}\\)")) {
        currentMemoryBlock = new MemoryBlock(tokens[1], line);
      } else {
        LOG.error(
            "-----> Line {} - {} is invalid, addresses must be formatted like '$(FF)'",
            line,
            tokens[1]);
        this.hasErrors = true;
        return;
      }
    } catch (MalformedAddressException e) {
      LOG.error(
          "-----> Line {} - {} is invalid, addresses must be formatted like '$(FF)'",
          line,
          tokens[1]);
      this.hasErrors = true;
      return;
    }
    result.add(currentMemoryBlock);
  }

  /**
   * Tokenizes a label
   *
   * @param line the line index in the source file
   * @param tokens tokens to tokenize
   */
  private void tokenizeLabel(int line, String[] tokens) {
    if (tokens.length != 1) {
      LOG.error(
          "-----> Line {} - {} is invalid, labels must not contains blank space", line, tokens[1]);
      this.hasErrors = true;
      return;
    }
    labelToReference = new Label(tokens[0].replace(":", ""), line);
    result.add(labelToReference);
  }

  /**
   * Tokenizes a variable
   *
   * @param line the line index in the source file
   * @param tokens tokens to tokenize
   */
  private void tokenizeVariable(int line, String[] tokens) {
    if (tokens.length != 2 && tokens.length != 3) {
      LOG.error("-----> Line {} - .var must be followed by its alias and an optional value", line);
      this.hasErrors = true;
      return;
    }
    try {
      result.add(new Variable(tokens[1], tokens.length == 3 ? tokens[2] : null, line));
    } catch (NumberFormatException e) {
      LOG.error("-----> Line {} - '{}' is not a valid hexadecimal value", line, tokens[2]);
      this.hasErrors = true;
    }
  }

  /**
   * Tokenizes a constant
   *
   * @param line the line index in the source file
   * @param tokens tokens to tokenize
   */
  private void tokenizeConstant(int line, String[] tokens) {
    if (tokens.length != 3) {
      LOG.error("-----> Line {} - .const must be followed by its alias and optional value", line);
      this.hasErrors = true;
      return;
    }
    try {
      result.add(new Constant(tokens[1], tokens[2], line));
    } catch (NumberFormatException e) {
      LOG.error("-----> Line {} - '{}' is not a valid hexadecimal value", line, tokens[2]);
      this.hasErrors = true;
    }
  }
}
