/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.mapping.token;

import assembler.tokenizing.token.MemoryBlock;

/**
 * Represents a Memory Block
 *
 * <p>.block $(addr)
 *
 * <p>FF FF FF ... that as been tokenized and mapped
 */
public class MappedMemoryBlock extends MappedToken {

  /** The token that is mapped by this object */
  private final MemoryBlock token;

  /**
   * Creates a new Mapped Memory Block
   *
   * @param address the base address of the block
   * @param token the block to map
   */
  public MappedMemoryBlock(int address, MemoryBlock token) {
    super(address, token.getSourceFileLine());
    this.token = token;
  }

  /**
   * Returns the reference token
   *
   * @return the reference token
   */
  public MemoryBlock getToken() {
    return token;
  }

  @Override
  public String toString() {
    return String.format("[$%04X] .block $(%04X)", getAddress(), getAddress());
  }
}
