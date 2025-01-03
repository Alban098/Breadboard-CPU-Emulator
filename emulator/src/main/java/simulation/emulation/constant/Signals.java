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
  public static final int PC_IN_16 = 1 << 1;
  public static final int PC_OUT_16 = 1 << 2;
  public static final int PC_OUT_HIGH = 1 << 3;
  public static final int MAR_IN_16 = 1 << 4;
  public static final int RAM_IN = 1 << 5;
  public static final int RAM_OUT = 1 << 6;
  public static final int STATUS_OUT = 1 << 7;
  public static final int STATUS_IN = 1 << 8;
  public static final int IR_IN = 1 << 9;
  public static final int A_IN = 1 << 10;
  public static final int A_OUT = 1 << 11;
  public static final int B_IN = 1 << 12;
  public static final int HL_IN_LOW = 1 << 13;
  public static final int HL_IN_HIGH = 1 << 14;
  public static final int HL_OUT_16 = 1 << 15;
  public static final int STACK_PUSH = 1 << 16;
  public static final int STACK_POP = 1 << 17;
  public static final int STACK_OUT_16 = 1 << 18;
  public static final int ALU_OUT = 1 << 19;
  public static final int SUB = 1 << 20;
  public static final int OUT_IN = 1 << 21;
  public static final int CU_RST = 1 << 22;
  public static final int HALT = 1 << 23;

  private static final Map<Integer, String> NAMES = new HashMap<>();
  private static final Map<Integer, Boolean> ACTIVE_LOW = new HashMap<>();

  static {
    NAMES.put(PC_E, "PC_E");
    NAMES.put(PC_IN_16, "PC_IN_16");
    NAMES.put(PC_OUT_16, "PC_OUT_16");
    NAMES.put(PC_OUT_HIGH, "PC_OUT_HIGH");
    NAMES.put(MAR_IN_16, "MAR_IN_16");
    NAMES.put(RAM_IN, "RAM_IN");
    NAMES.put(RAM_OUT, "RAM_OUT");
    NAMES.put(STATUS_OUT, "STATUS_OUT");
    NAMES.put(STATUS_IN, "STATUS_IN");
    NAMES.put(IR_IN, "IR_IN");
    NAMES.put(A_IN, "A_IN");
    NAMES.put(A_OUT, "A_OUT");
    NAMES.put(B_IN, "B_IN");
    NAMES.put(HL_IN_LOW, "HL_IN_LOW");
    NAMES.put(HL_IN_HIGH, "HL_IN_HIGH");
    NAMES.put(HL_OUT_16, "HL_OUT_16");
    NAMES.put(STACK_PUSH, "STACK_PUSH");
    NAMES.put(STACK_POP, "STACK_POP");
    NAMES.put(STACK_OUT_16, "STACK_OUT_16");
    NAMES.put(ALU_OUT, "ALU_OUT");
    NAMES.put(SUB, "SUB");
    NAMES.put(OUT_IN, "OUT_IN");
    NAMES.put(CU_RST, "CU_RST");
    NAMES.put(HALT, "HLT");

    ACTIVE_LOW.put(PC_E, false);
    ACTIVE_LOW.put(PC_IN_16, true);
    ACTIVE_LOW.put(PC_OUT_16, true);
    ACTIVE_LOW.put(PC_OUT_HIGH, true);
    ACTIVE_LOW.put(MAR_IN_16, true);
    ACTIVE_LOW.put(RAM_IN, false);
    ACTIVE_LOW.put(RAM_OUT, true);
    ACTIVE_LOW.put(STATUS_OUT, true);
    ACTIVE_LOW.put(STATUS_IN, true);
    ACTIVE_LOW.put(IR_IN, true);
    ACTIVE_LOW.put(A_IN, true);
    ACTIVE_LOW.put(A_OUT, true);
    ACTIVE_LOW.put(B_IN, true);
    ACTIVE_LOW.put(HL_IN_LOW, true);
    ACTIVE_LOW.put(HL_IN_HIGH, true);
    ACTIVE_LOW.put(HL_OUT_16, true);
    ACTIVE_LOW.put(STACK_PUSH, false);
    ACTIVE_LOW.put(STACK_POP, false);
    ACTIVE_LOW.put(STACK_OUT_16, true);
    ACTIVE_LOW.put(ALU_OUT, true);
    ACTIVE_LOW.put(SUB, false);
    ACTIVE_LOW.put(OUT_IN, false);
    ACTIVE_LOW.put(CU_RST, false);
    ACTIVE_LOW.put(HALT, false);
  }

  private Signals() {}

  public static boolean isActiveInState(int signal, int value) {
    return (value & signal) == signal;
  }

  public static Map<Integer, String> values() {
    return NAMES;
  }

  public static boolean isActiveLow(int signal) {
    return ACTIVE_LOW.getOrDefault(signal, false);
  }
}
