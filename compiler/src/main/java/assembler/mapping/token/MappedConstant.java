/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.mapping.token;

import assembler.tokenizing.token.Constant;

/**
 * Represents a Constant
 *
 * <p>.const alias value that as been tokenized and mapped
 */
public class MappedConstant extends MappedToken {

  /** The token that is mapped by this object */
  private final Constant token;

  /**
   * Creates a new Mapped Constant
   *
   * @param address the base address of the constant to map
   * @param token the constant to map
   */
  public MappedConstant(int address, Constant token) {
    super(address, token.getSourceFileLine());
    this.token = token;
  }

  /**
   * Returns the reference token
   *
   * @return the reference token
   */
  public Constant getToken() {
    return token;
  }

  @Override
  public String toString() {
    return token.toString();
  }
}
