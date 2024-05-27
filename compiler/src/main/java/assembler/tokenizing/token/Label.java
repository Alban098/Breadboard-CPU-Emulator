/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.tokenizing.token;

import java.util.Objects;

public class Label extends Token {

  private final String alias;
  private Operation referenceOperation;

  public Label(String alias) {
    this.alias = alias;
  }

  public String getAlias() {
    return alias;
  }

  public Operation getReferenceOperation() {
    return referenceOperation;
  }

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
    return Objects.equals(alias, label.alias)
        && Objects.equals(referenceOperation, label.referenceOperation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, referenceOperation);
  }
}
