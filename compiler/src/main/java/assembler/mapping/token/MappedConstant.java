/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.mapping.token;

import assembler.tokenizing.token.Constant;

public class MappedConstant extends MappedToken {

  private final Constant token;

  public MappedConstant(int address, Constant token) {
    super(address);
    this.token = token;
  }

  public Constant getToken() {
    return token;
  }

  @Override
  public String toString() {
    return token.toString();
  }
}
