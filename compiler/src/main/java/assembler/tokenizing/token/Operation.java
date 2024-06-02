/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.tokenizing.token;

import assembler.constant.Instruction;
import java.util.Objects;

/** Represents an Operation see {@link Instruction} that as been tokenized */
public class Operation extends Token {

  /** The instruction this operation represents */
  private final Instruction instruction;
  /** An optional argument if the instruction requires it */
  private final String arg;

  /**
   * Creates a new Operation
   *
   * @param instruction the instruction to represent
   * @param arg the optional argument if the instruction
   * @param sourceFileLine the line of the token in the source file
   */
  public Operation(Instruction instruction, String arg, int sourceFileLine) {
    super(sourceFileLine);
    this.instruction = instruction;
    this.arg = arg;
  }

  /**
   * Return the instruction represented by this Operation
   *
   * @return the instruction represented by this Operation
   */
  public Instruction getInstruction() {
    return instruction;
  }

  /**
   * Returns the argument of this operation
   *
   * @return the argument of this operation
   */
  public String getArg() {
    return arg;
  }

  @Override
  public String toString() {
    return "["
        + String.format("%02X", instruction.getOpcode())
        + "] {"
        + instruction.getAddressingMode()
        + "} "
        + instruction.getName()
        + " "
        + (arg == null ? "" : arg);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Operation operation = (Operation) o;
    return getSourceFileLine() == operation.getSourceFileLine()
        && instruction == operation.instruction
        && Objects.equals(arg, operation.arg);
  }

  @Override
  public int hashCode() {
    return Objects.hash(instruction, arg, getSourceFileLine());
  }
}
