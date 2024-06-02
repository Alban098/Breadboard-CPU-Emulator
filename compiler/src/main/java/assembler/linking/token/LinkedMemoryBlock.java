/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.linking.token;

/**
 * Represents a Memory Block
 *
 * <p>.block $(addr)
 *
 * <p>FF FF FF ... that as been tokenized, mapped and linked, at this point it is valid
 */
public class LinkedMemoryBlock extends LinkedToken {

  /** the content of the block */
  private final int[] data;

  /**
   * Creates a new LinkedMemoryBlock
   *
   * @param address the base address of the memory block
   * @param data the content of the memory block
   */
  public LinkedMemoryBlock(int address, int[] data) {
    super(address);
    this.data = data;
  }

  /**
   * Return the content of the memory block
   *
   * @return the content of the memory block
   */
  public int[] getData() {
    return data;
  }

  @Override
  public String toString() {
    return String.format("[$%04X] .block $(%02X)", getAddress(), getAddress());
  }
}
