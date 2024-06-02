/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.linking.token;

import assembler.constant.Instruction;

/**
 * Represents an Operation see {@link Instruction} that as been tokenized, mapped and linked, at
 * this point it is valid
 */
public class LinkedOperation extends LinkedToken {

  /** The referenced {@link Instruction} referenced by this Operation */
  private final Instruction instruction;

  /** An optional argument, in case {@link Instruction#getSize()} > 1 */
  private final int[] args;

  /**
   * Creates a new LinkedOperation
   *
   * @param address the address of the Operation
   * @param instruction the {@link Instruction} to compile to
   * @param args optional arguments in case {@link Instruction#getSize()} == 2
   */
  public LinkedOperation(int address, Instruction instruction, int[] args) {
    super(address);
    this.instruction = instruction;
    this.args = args;
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
   * Return the optional argument, null if {@link Instruction#getSize()} > 1
   *
   * @return the optional argument, null if {@link Instruction#getSize()} > 1
   */
  public int[] getArgs() {
    return args;
  }

  @Override
  public String toString() {
    int arg = 0;
    for (int i = 0; i < args.length; i++) {
      arg |= args[i] << (i * 8);
    }
    return String.format("[$%04X]", getAddress()) + String.format(instruction.format(arg));
  }
}
