/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

public final class Bus implements Formattable {

  private int state = 0;

  public int read() {
    return state & 0xFF;
  }

  public int read16() {
    return state & 0xFFFF;
  }

  public void write(int value) {
    this.state |= value & 0xFF;
  }

  public void write16(int value) {
    this.state |= value & 0xFFFF;
  }

  public void update() {
    this.state = 0;
  }

  @Override
  public String hexString() {
    return String.format("0x%04X", state);
  }

  @Override
  public String binaryString() {
    return String.format("%16s", Integer.toBinaryString(state)).replaceAll(" ", "0");
  }
}
