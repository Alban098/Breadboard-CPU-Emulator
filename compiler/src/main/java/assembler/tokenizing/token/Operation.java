/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.tokenizing.token;

import assembler.Instruction;
import java.util.Objects;

public class Operation extends Token {

  private final Instruction instruction;
  private final String arg;

  public Operation(Instruction instruction, String arg) {
    this.instruction = instruction;
    this.arg = arg;
  }

  public Instruction getInstruction() {
    return instruction;
  }

  public String getArg() {
    return arg;
  }

  @Override
  public String toString() {
    return "["
        + String.format("%02X", instruction.getOpcode())
        + "] "
        + instruction.name()
        + " "
        + (arg == null ? "" : arg);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Operation operation = (Operation) o;
    return instruction == operation.instruction && Objects.equals(arg, operation.arg);
  }

  @Override
  public int hashCode() {
    return Objects.hash(instruction, arg);
  }
}
