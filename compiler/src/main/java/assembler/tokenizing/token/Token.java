/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.tokenizing.token;

/** Base class for a Token */
public class Token {

  /** The line number of this token in the source file */
  private final int sourceFileLine;

  /**
   * Creates a new Token
   *
   * @param sourceFileLine the line number of this token in the source file
   */
  public Token(int sourceFileLine) {
    this.sourceFileLine = sourceFileLine;
  }

  /**
   * Returns the line number of this token in the source file
   *
   * @return the line number of this token in the source file
   */
  public int getSourceFileLine() {
    return sourceFileLine;
  }
}
