/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.linking.token;

import assembler.Instruction;

/**
 * Represents an Operation see {@link Instruction} that as been tokenized, mapped and linked, at
 * this point it is valid
 */
public class LinkedOperation extends LinkedToken {

  /** The referenced {@link Instruction} referenced by this Operation */
  private final Instruction instruction;

  /** An optional argument, in case {@link Instruction#getSize()} == 2 */
  private final Integer arg;

  /**
   * Creates a new LinkedOperation
   *
   * @param address the address of the Operation
   * @param instruction the {@link Instruction} to compile to
   * @param arg an optional argument in case {@link Instruction#getSize()} == 2
   */
  public LinkedOperation(int address, Instruction instruction, Integer arg) {
    super(address);
    this.instruction = instruction;
    this.arg = arg;
  }

  /**
   * Return the referenced {@link Instruction}
   *
   * @return the referenced {@link Instruction}
   */
  public Instruction getInstruction() {
    return instruction;
  }

  /**
   * Return the optional argument, null if {@link Instruction#getSize()} != 2
   *
   * @return the optional argument, null if {@link Instruction#getSize()} != 2
   */
  public Integer getArg() {
    return arg;
  }

  @Override
  public String toString() {
    return String.format("[$%02X]", getAddress()) + String.format(instruction.getFormat(), arg);
  }
}
