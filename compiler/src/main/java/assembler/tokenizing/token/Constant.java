/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.tokenizing.token;

import assembler.constant.ConstantType;
import assembler.tokenizing.exception.MalformedValueException;
import java.util.Objects;

/**
 * Represents a Constant
 *
 * <p>.const alias value that as been tokenized
 */
public class Constant extends Token {

  /** The alias of the constant */
  private final String alias;
  /** The value of the constant */
  private final int value;

  private final ConstantType type;

  /**
   * Creates a new Constant
   *
   * @param alias the alias of the constant
   * @param value the value of the constant
   * @param sourceFileLine the line of the token in the source file
   */
  public Constant(String type, String alias, String value, int sourceFileLine)
      throws MalformedValueException {
    super(sourceFileLine);
    this.alias = alias;
    if ("byte".equals(type)) {
      this.type = ConstantType.BYTE;
    } else if ("word".equals(type)) {
      this.type = ConstantType.WORD;
    } else {
      throw new MalformedValueException();
    }
    this.value =
        Integer.parseInt(value == null ? "0" : value.replaceAll("#", ""), 16)
            & (this.type == ConstantType.BYTE ? 0xFF : 0xFFFF);
  }

  /**
   * Returns the alias of the constant
   *
   * @return the alias of the constant
   */
  public String getAlias() {
    return alias;
  }

  /**
   * Returns the value of the constant
   *
   * @return the value of the constant
   */
  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return switch (type) {
      case BYTE -> "Constant 1 byte '" + alias + "' = " + String.format("#%02X", value);
      case WORD -> "Constant 2 bytes '" + alias + "' = " + String.format("#%04X", value);
    };
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Constant constant = (Constant) o;
    return value == constant.value
        && getSourceFileLine() == constant.getSourceFileLine()
        && Objects.equals(alias, constant.alias);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, value, getSourceFileLine());
  }

  public ConstantType getType() {
    return type;
  }
}
