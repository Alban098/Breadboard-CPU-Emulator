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

public class MemoryBlock extends Token {

  private final int address;
  private final List<Integer> data = new ArrayList<>();

  public MemoryBlock(String address) throws MalformedAddressException {
    if (!address.startsWith("$")) {
      throw new MalformedAddressException();
    }
    this.address = Integer.parseInt(address.replaceAll("[$()]", ""), 16) & 0xFF;
  }

  public int getAddress() {
    return address;
  }

  public List<Integer> getData() {
    return data;
  }

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
    return address == that.address && Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, data);
  }
}
