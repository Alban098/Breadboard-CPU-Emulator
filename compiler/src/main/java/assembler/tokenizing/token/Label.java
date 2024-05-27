/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.tokenizing.token;

import java.util.Objects;

/**
 * Represents a Constant
 *
 * <p>:ALIAS that as been tokenized
 */
public class Label extends Token {

  /** The alias of this label */
  private final String alias;
  /** The operation this label references */
  private Operation referenceOperation;

  /**
   * Creates a new Label
   *
   * @param alias the alias of the label
   * @param sourceFileLine the line of the token in the source file
   */
  public Label(String alias, int sourceFileLine) {
    super(sourceFileLine);
    this.alias = alias;
  }

  /**
   * Returns the alias of the label
   *
   * @return the alias of the label
   */
  public String getAlias() {
    return alias;
  }

  /**
   * Returns the Operation this label references
   *
   * @return the Operation this label references
   */
  public Operation getReferenceOperation() {
    return referenceOperation;
  }

  /**
   * Sets the Operation this label should reference
   *
   * @param referenceOperation the Operation this label should reference
   */
  public void setReferenceOperation(Operation referenceOperation) {
    this.referenceOperation = referenceOperation;
  }

  @Override
  public String toString() {
    return "Label '" + alias + "' ref. " + referenceOperation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Label label = (Label) o;
    return getSourceFileLine() == label.getSourceFileLine()
        && Objects.equals(alias, label.alias)
        && Objects.equals(referenceOperation, label.referenceOperation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, referenceOperation, getSourceFileLine());
  }
}
