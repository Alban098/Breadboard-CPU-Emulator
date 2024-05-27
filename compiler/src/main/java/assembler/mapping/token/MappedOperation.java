/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.mapping.token;

import assembler.Instruction;
import assembler.tokenizing.token.Operation;

/** Represents an Operation see {@link Instruction} that as been tokenized and mapped */
public class MappedOperation extends MappedToken {

  /** The token that is mapped by this object */
  private final Operation token;

  /**
   * Creates a new Mapped Operation
   *
   * @param address the base address of the operation to map
   * @param token the operation to map
   */
  public MappedOperation(int address, Operation token) {
    super(address, token.getSourceFileLine());
    this.token = token;
  }

  /**
   * Returns the reference token
   *
   * @return the reference token
   */
  public Operation getToken() {
    return token;
  }

  @Override
  public String toString() {
    return String.format("[$%02X] %s", getAddress(), token.toString());
  }
}
