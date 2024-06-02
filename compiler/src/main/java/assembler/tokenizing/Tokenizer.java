/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.tokenizing;

import assembler.constant.AddressingMode;
import assembler.constant.ConstantType;
import assembler.constant.Instruction;
import assembler.tokenizing.exception.MalformedAddressException;
import assembler.tokenizing.exception.MalformedValueException;
import assembler.tokenizing.token.*;
import java.util.ArrayList;
import java.util.List;
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
    AddressingMode addressingMode = AddressingMode.NON;
    String arg = null;
    Instruction decoded = null;
    if (tokens.length > 1) {
      StringBuilder argB = new StringBuilder();
      for (int i = 1; i < tokens.length; i++) {
        argB.append(tokens[i]).append(" ");
      }
      arg = argB.toString().trim();

      try {
        addressingMode = AddressingMode.resolve(arg);
        // IF the addressing mode is ABSOLUTE but the referenced constant is of type byte, switch it
        // to ZERO PAGE
        if (addressingMode == AddressingMode.ABS && arg.matches("\\$\\([a-zA-Z0-9_-]+\\)")) {
          String constant = arg.replaceAll("[$()]", "");
          if (result.getConstants().stream()
              .anyMatch(
                  cst -> cst.getAlias().equals(constant) && cst.getType() == ConstantType.BYTE)) {
            addressingMode = AddressingMode.Z_P;
          }
        }
      } catch (IllegalArgumentException e) {
        LOG.error("-----> Line {} - Unable to deduce addressing mode for argument '{}'", line, arg);
        this.hasErrors = true;
        return;
      }
    }

    try {
      decoded = Instruction.find(operand, addressingMode);
    } catch (IllegalArgumentException e) {
      LOG.error(
          "-----> Line {} - Unable to find instruction '{}' with addressing mode '{}'",
          line,
          operand,
          addressingMode.name());
      this.hasErrors = true;
    }
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
      LOG.error(
          "-----> Line {} - .block must be followed by a memory address '.block $(XXXX)", line);
      this.hasErrors = true;
      return;
    }
    currentMode = Mode.MEMORY_BLOCK;
    try {
      if (tokens[1].startsWith("$(") && tokens[1].matches("\\$\\([0-9A-Fa-f]{1,4}\\)")) {
        currentMemoryBlock = new MemoryBlock(tokens[1], line);
      } else {
        LOG.error(
            "-----> Line {} - {} is invalid, addresses must be formatted like '$(FFFF)'",
            line,
            tokens[1]);
        this.hasErrors = true;
        return;
      }
    } catch (MalformedAddressException e) {
      LOG.error(
          "-----> Line {} - {} is invalid, addresses must be formatted like '$(FFFF)'",
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
      LOG.error("-----> Line {} - '{}' is not a valid 8 bits hexadecimal value", line, tokens[2]);
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
    if (tokens.length != 4) {
      LOG.error(
          "-----> Line {} - .const must be followed by its type, alias and optional value", line);
      this.hasErrors = true;
      return;
    }
    try {
      result.add(new Constant(tokens[1], tokens[2], tokens[3], line));
    } catch (NumberFormatException e) {
      LOG.error("-----> Line {} - '{}' is not a valid hexadecimal value", line, tokens[2]);
      this.hasErrors = true;
    } catch (MalformedValueException e) {
      LOG.error("-----> Line {} - '{}' is not a valid constant type", line, tokens[1]);
      this.hasErrors = true;
    }
  }
}
