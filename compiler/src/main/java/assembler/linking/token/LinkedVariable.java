/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.linking.token;

/**
 * Represents a Variable
 *
 * <p>.var alias ?value that as been tokenized, mapped and linked, at this point it is valid
 */
public class LinkedVariable extends LinkedToken {

  /** The initial value of that variable 0 if none provided */
  private final int data;

  /**
   * Creates a new Linked Variable
   *
   * @param address the resolved address
   * @param data the initial value
   */
  public LinkedVariable(int address, int data) {
    super(address);
    this.data = data;
  }

  /**
   * Returns the initial value of the Variable
   *
   * @return the initial value of the Variable
   */
  public int getData() {
    return data;
  }

  @Override
  public String toString() {
    return String.format("[$%02X] var=%02X", getAddress(), data);
  }
}
