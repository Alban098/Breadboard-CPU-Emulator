/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.constant;

public enum Signal {
  C_E,
  C_IN,
  C_OUT,
  MAR_IN,
  RAM_IN,
  RAM_OUT,
  IR_IN,
  SR_IN,
  SR_OUT,
  A_IN,
  A_OUT,
  ALU_OUT,
  AND,
  SUB,
  OR,
  XOR,
  B_IN,
  B_OUT,
  OUT_IN,
  CU_RST,
  SR_RST,
  SR_LATCH,
  HLT;
  private final int mask;

  Signal() {
    mask = 1 << this.ordinal();
  }

  public int getMask() {
    return mask;
  }

  public boolean isActiveInState(int value) {
    return (value & mask) == mask;
  }
}
