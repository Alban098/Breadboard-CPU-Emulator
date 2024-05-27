/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.mapping.token;

/** Base class for a Mapped Token */
public abstract class MappedToken {

  /** The base address of the token */
  private final int address;
  /** The line of the token in the source file */
  private final int sourceFileLine;

  /**
   * Creates a new MappedToken
   *
   * @param address the base address of the token
   * @param sourceFileLine the line of the token in the source file
   */
  public MappedToken(int address, int sourceFileLine) {
    this.address = address;
    this.sourceFileLine = sourceFileLine;
  }

  /**
   * Returns the base address of the token
   *
   * @return the base address of the token
   */
  public int getAddress() {
    return address;
  }

  /**
   * Returns the line of the token in the source file
   *
   * @return the line of the token in the source file
   */
  public int getSourceFileLine() {
    return sourceFileLine;
  }
}
