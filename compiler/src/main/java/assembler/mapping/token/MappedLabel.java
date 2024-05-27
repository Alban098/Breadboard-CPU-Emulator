/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.mapping.token;

import assembler.tokenizing.token.Label;

/**
 * Represents a Constant
 *
 * <p>:ALIAS that as been tokenized and mapped
 */
public class MappedLabel extends MappedToken {

  /** The token that is mapped by this object */
  private final Label token;

  /**
   * Creates a new Mapped Label
   *
   * @param token the label to map
   * @param referenceOperation the operation to attach this label to
   */
  public MappedLabel(Label token, MappedOperation referenceOperation) {
    super(referenceOperation.getAddress(), token.getSourceFileLine());
    this.token = token;
  }

  /**
   * Returns the reference token
   *
   * @return the reference token
   */
  public Label getToken() {
    return token;
  }

  @Override
  public String toString() {
    return String.format("[$%02X] %s", getAddress(), token.getAlias());
  }
}
