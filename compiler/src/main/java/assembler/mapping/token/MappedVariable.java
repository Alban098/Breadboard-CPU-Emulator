/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.mapping.token;

import assembler.tokenizing.token.Variable;

/**
 * Represents a Variable
 *
 * <p>.var alias ?value that as been tokenized and mapped
 */
public class MappedVariable extends MappedToken {

  /** The token that is mapped by this object */
  private final Variable token;

  /**
   * Creates a new Mapped Variable
   *
   * @param address the base address of the variable to map
   * @param token the variable to map
   */
  public MappedVariable(int address, Variable token) {
    super(address, token.getSourceFileLine());
    this.token = token;
  }

  /**
   * Returns the reference token
   *
   * @return the reference token
   */
  public Variable getToken() {
    return token;
  }

  @Override
  public String toString() {
    return String.format("[$%04X] %s", getAddress(), token.toString());
  }
}
