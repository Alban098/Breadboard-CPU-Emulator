/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.constant;

import java.util.HashMap;
import java.util.Map;

public final class Signals {

  public static final int PC_E = 1;
  public static final int PC_IN = 1 << 1;
  public static final int PC_IN_HL = 1 << 2;
  public static final int MAR_IN = 1 << 3;
  public static final int MAR_IN_HL = 1 << 4;
  public static final int MAR_IN_PC = 1 << 5;
  public static final int MAR_IN_STACK = 1 << 6;
  public static final int RAM_IN = 1 << 7;
  public static final int RAM_OUT = 1 << 8;
  public static final int IR_IN = 1 << 9;
  public static final int A_IN = 1 << 10;
  public static final int A_OUT = 1 << 11;
  public static final int B_IN = 1 << 12;
  public static final int B_OUT = 1 << 13;
  public static final int HL_IN_LOW = 1 << 14;
  public static final int HL_IN_HIGH = 1 << 15;
  public static final int STACK_PUSH = 1 << 16;
  public static final int STACK_POP = 1 << 17;
  public static final int ALU_OUT = 1 << 18;
  public static final int SUB = 1 << 19;
  public static final int OUT_IN = 1 << 20;
  public static final int CU_RST = 1 << 21;
  public static final int SR_LATCH = 1 << 22;
  public static final int HALT = 1 << 23;

  private static final Map<Integer, String> NAMES = new HashMap<>();

  static {
    NAMES.put(PC_E, "C_E");
    NAMES.put(PC_IN, "C_IN");
    NAMES.put(PC_IN_HL, "C_IN_HL");
    NAMES.put(MAR_IN, "MR_IN");
    NAMES.put(MAR_IN_HL, "MR_IN_HL");
    NAMES.put(MAR_IN_PC, "MR_IN_PC");
    NAMES.put(MAR_IN_STACK, "MR_IN_STACK");
    NAMES.put(RAM_IN, "RAM_IN");
    NAMES.put(RAM_OUT, "RAM_OUT");
    NAMES.put(IR_IN, "IR_IN");
    NAMES.put(A_IN, "A_IN");
    NAMES.put(A_OUT, "A_OUT");
    NAMES.put(B_IN, "B_IN");
    NAMES.put(B_OUT, "B_OUT");
    NAMES.put(HL_IN_LOW, "HL_IN_LOW");
    NAMES.put(HL_IN_HIGH, "HL_IN_HIGH");
    NAMES.put(STACK_PUSH, "STACK_PUSH");
    NAMES.put(STACK_POP, "STACK_POP");
    NAMES.put(ALU_OUT, "ALU_OUT");
    NAMES.put(SUB, "SUB");
    NAMES.put(OUT_IN, "OUT_IN");
    NAMES.put(CU_RST, "CU_RST");
    NAMES.put(SR_LATCH, "SR_LATCH");
    NAMES.put(HALT, "HLT");
  }

  private Signals() {}

  public static boolean isActiveInState(int signal, int value) {
    return (value & signal) == signal;
  }

  public static Map<Integer, String> values() {
    return NAMES;
  }
}
