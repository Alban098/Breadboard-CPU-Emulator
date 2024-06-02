/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.constant;

public enum AddressingMode {
  IMM("#%02X"),
  ABS("$%04X"),
  Z_P("$%02X"),
  IMP(""),
  IDX("$%02X, A");

  private final String format;

  AddressingMode(String format) {
    this.format = format;
  }

  public String format(int arg) {
    return String.format(format, arg);
  }
}
