/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.tokenizing;

/** Tokenizer modes */
public enum Mode {
  /** In this mode, all data is interpreted as data part of a Memory block */
  MEMORY_BLOCK,
  /** Default behavior */
  CODE,
}
