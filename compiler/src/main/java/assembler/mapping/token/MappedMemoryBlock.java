/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.mapping.token;

import assembler.tokenizing.token.MemoryBlock;

public class MappedMemoryBlock extends MappedToken {

  private final MemoryBlock token;

  public MappedMemoryBlock(int address, MemoryBlock token) {
    super(address);
    this.token = token;
  }

  public MemoryBlock getToken() {
    return token;
  }

  @Override
  public String toString() {
    return String.format("[$%02X] .block $(%02X)", getAddress(), getAddress());
  }
}
