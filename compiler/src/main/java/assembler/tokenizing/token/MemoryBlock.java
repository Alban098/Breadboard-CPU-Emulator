/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.tokenizing.token;

import assembler.tokenizing.exception.MalformedAddressException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Memory Block
 *
 * <p>.block $(addr)
 *
 * <p>FF FF FF ... that as been tokenized
 */
public class MemoryBlock extends Token {

  /** The address of the memory block */
  private final int address;
  /** The content of the memory block */
  private final List<Integer> data = new ArrayList<>();

  /**
   * Creates a new Memory Block
   *
   * @param address the address of the memory block
   * @param sourceFileLine the line of the token in the source file
   * @throws MalformedAddressException when the passed address is not of the form '$(XX)'
   */
  public MemoryBlock(String address, int sourceFileLine) throws MalformedAddressException {
    super(sourceFileLine);
    if (!address.startsWith("$")) {
      throw new MalformedAddressException();
    }
    this.address = Integer.parseInt(address.replaceAll("[$()]", ""), 16) & 0xFF;
  }

  /**
   * Returns the address of the memory block
   *
   * @return the address of the memory block
   */
  public int getAddress() {
    return address;
  }

  /**
   * Returns the content of the memory block
   *
   * @return the content of the memory block
   */
  public List<Integer> getData() {
    return data;
  }

  /**
   * Add an entry to the content of the memory block
   *
   * @param value the data to add, will be trimmed to 8 bits
   */
  public void addData(int value) {
    data.add(value & 0xFF);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("Block ");
    builder
        .append(String.format("%02X", address))
        .append(" [")
        .append(String.format("%X", data.size()))
        .append("Bytes] { ");
    data.forEach(data -> builder.append(String.format("%02X ", data)));
    builder.append("}");
    return builder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MemoryBlock that = (MemoryBlock) o;
    return getSourceFileLine() == that.getSourceFileLine()
        && address == that.address
        && Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, data, getSourceFileLine());
  }
}
