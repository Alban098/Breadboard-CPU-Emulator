/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.tokenizing.token;

import java.util.Objects;

/**
 * Represents a Variable
 *
 * <p>.var alias ?value that as been tokenized
 */
public class Variable extends Token {

  /** The alias of this variable */
  private final String alias;
  /** The value of this variable */
  private final int value;

  /**
   * Creates a new Variable
   *
   * @param alias the alias of the variable
   * @param value the value of teh variable, 0 if null
   * @param sourceFileLine the line of the token in the source file
   */
  public Variable(String alias, String value, int sourceFileLine) {
    super(sourceFileLine);
    this.alias = alias;
    this.value = value == null ? 0 : Integer.parseInt(value, 16) & 0xFF;
  }

  /**
   * Returns the alias of the variable
   *
   * @return the alias of the variable
   */
  public String getAlias() {
    return alias;
  }

  /**
   * Returns the value of the variable
   *
   * @return the value of the variable
   */
  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Var " + alias + "=" + String.format("%02X", value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Variable variable = (Variable) o;
    return getSourceFileLine() == variable.getSourceFileLine()
        && value == variable.value
        && Objects.equals(alias, variable.alias);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, value, getSourceFileLine());
  }
}
