/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.constant;

public enum AddressingMode {
  IMM("\\#[0-9a-fA-F]{1,2}|\\#\\([a-zA-Z0-9_-]+\\)"),
  ABS(
      "\\$[0-9a-fA-F]{1,2}\\([a-zA-Z0-9_-]+\\){1}|\\$\\([a-zA-Z0-9_-]+\\){1}[0-9a-fA-F]{2}|\\$[0-9a-fA-F]{3,4}|\\$\\([a-zA-Z0-9_-]+\\)|[a-zA-Z0-9_-]+|@[a-zA-Z0-9_-]+"),
  Z_P("\\$[0-9a-fA-F]{1,2}"),
  NON(""),
  IDX("(\\$[0-9a-fA-F]{1,2}|\\$\\([a-zA-Z0-9_-]+\\)), A");

  private final String regEx;

  AddressingMode(String regEx) {
    this.regEx = regEx;
  }

  public String format(int arg) {
    return String.format(regEx, arg);
  }

  public static AddressingMode resolve(String arg) {
    for (AddressingMode mode : values()) {
      if (arg.matches(mode.regEx)) {
        return mode;
      }
    }
    throw new IllegalArgumentException("Unable to deduce addressing mode for arg '" + arg + "'");
  }
}
