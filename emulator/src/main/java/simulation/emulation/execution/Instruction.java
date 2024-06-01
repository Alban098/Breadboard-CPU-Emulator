/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.execution;

import static simulation.emulation.constant.Signals.*;

import simulation.emulation.constant.AddressingMode;
import simulation.emulation.constant.Flag;

public enum Instruction {
  NOP(0x00, "NOP", 1, new int[][] {new int[] {MAR_IN_PC, RAM_OUT | IR_IN | PC_E, CU_RST}}),

  LDA_IMM(
      0x01,
      "LDA #%02X",
      2,
      new int[][] {
        new int[] {MAR_IN_PC, RAM_OUT | IR_IN | PC_E, MAR_IN_PC, RAM_OUT | A_IN, PC_E | CU_RST}
      }),
  LDA_ZP(
      0x02,
      "LDA $%02X",
      2,
      new int[][] {
        new int[] {
          MAR_IN_PC,
          RAM_OUT | IR_IN | PC_E,
          MAR_IN_PC,
          RAM_OUT | HL_IN_LOW,
          HL_IN_HIGH,
          MAR_IN_HL,
          RAM_OUT | A_IN,
          PC_E | CU_RST
        }
      }),
  LDA_ABS(
      0x03,
      "LDA $%04X",
      3,
      new int[][] {
        new int[] {
          MAR_IN_PC,
          RAM_OUT | IR_IN | PC_E,
          MAR_IN_PC,
          RAM_OUT | HL_IN_HIGH | PC_E,
          MAR_IN_PC,
          RAM_OUT | HL_IN_LOW,
          MAR_IN_HL,
          RAM_OUT | A_IN,
          PC_E | CU_RST
        }
      }),

  LDB_IMM(
      0x04,
      "LDB #%02X",
      2,
      new int[][] {
        new int[] {MAR_IN_PC, RAM_OUT | IR_IN | PC_E, MAR_IN_PC, RAM_OUT | B_IN, PC_E | CU_RST}
      }),
  LDB_ZP(
      0x05,
      "LDB $%02X",
      2,
      new int[][] {
        new int[] {
          MAR_IN_PC,
          RAM_OUT | IR_IN | PC_E,
          MAR_IN_PC,
          RAM_OUT | HL_IN_LOW,
          HL_IN_HIGH,
          MAR_IN_HL,
          RAM_OUT | B_IN,
          PC_E | CU_RST
        }
      }),
  LDB_ABS(
      0x06,
      "LDB $%04X",
      3,
      new int[][] {
        new int[] {
          MAR_IN_PC,
          RAM_OUT | IR_IN | PC_E,
          MAR_IN_PC,
          RAM_OUT | HL_IN_HIGH | PC_E,
          MAR_IN_PC,
          RAM_OUT | HL_IN_LOW,
          MAR_IN_HL,
          RAM_OUT | B_IN,
          PC_E | CU_RST
        }
      }),

  STA_ZP(0x07, "STA $%02X", 2, new int[][] {new int[] {}}),
  STA_ABS(0x08, "STA $%04X", 3, new int[][] {new int[] {}}),

  STB_ZP(0x09, "STB $%02X", 2, new int[][] {new int[] {}}),
  STB_ABS(0x0A, "STB $%04X", 3, new int[][] {new int[] {}}),

  ADD_IMM(0x0B, "ADD #%02X", 2, new int[][] {new int[] {}}),
  ADD_ZP(0x0C, "ADD $%02X", 2, new int[][] {new int[] {}}),
  ADD_ABS(0x0D, "ADD $%04X", 3, new int[][] {new int[] {}}),
  ADD_IDX(0x0E, "ADD $%02X, A", 2, new int[][] {new int[] {}}),

  SUB_IMM(0x0F, "SUB #%02X", 2, new int[][] {new int[] {}}),
  SUB_ZP(0x10, "SUB $%02X", 2, new int[][] {new int[] {}}),
  SUB_ABS(0x11, "SUB $%04X", 3, new int[][] {new int[] {}}),
  SUB_IDX(0x12, "SUB $%02X, A", 2, new int[][] {new int[] {}}),

  INC_ABS(0x13, "INC $%04X", 3, new int[][] {new int[] {}}),
  DEC_ABS(0x14, "DEC $%04X", 3, new int[][] {new int[] {}}),

  CLS(0x15, "CLS", 1, new int[][] {new int[] {}}),

  CMP(0x16, "CMP", 1, new int[][] {new int[] {}}),
  CMP_IMM(0x17, "CMP #%02X", 2, new int[][] {new int[] {}}),
  CMP_ZP(0x18, "CMP $%02X", 2, new int[][] {new int[] {}}),
  CMP_ABS(0x19, "CMP $%04X", 3, new int[][] {new int[] {}}),

  PHA(0x1A, "PHA", 1, new int[][] {new int[] {}}),
  PHB(0x1B, "PHB", 1, new int[][] {new int[] {}}),
  PHS(0x1C, "PHS", 1, new int[][] {new int[] {}}),

  PLA(0x1D, "PLA", 1, new int[][] {new int[] {}}),
  PLB(0x1E, "PLB", 1, new int[][] {new int[] {}}),
  PLS(0x1F, "PLS", 1, new int[][] {new int[] {}}),

  JSR_ZP(0x20, "JSR $%02X", 2, new int[][] {new int[] {}}),
  JSR_ABS(0x21, "JSR $%04X", 3, new int[][] {new int[] {}}),
  RTS(0x22, "RTS", 1, new int[][] {new int[] {}}),
  JMP_ZP(0x23, "JMP $%02X", 2, new int[][] {new int[] {}}),
  JMP_ABS(0x24, "JMP $%04X", 3, new int[][] {new int[] {}}),
  JMP_IDX(0x25, "JMP $%02X, A", 2, new int[][] {new int[] {}}),

  BCC_ZP(
      0x26,
      "BCC $%04X",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BCC_ABS(
      0x27,
      "BCC $%04X",
      3,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BCC_IDX(
      0x28,
      "BCC $%02X, A",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),

  BCS_ZP(
      0x29,
      "BCS $%04X",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BCS_ABS(
      0x2A,
      "BCS $%04X",
      3,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BCS_IDX(
      0x2B,
      "BCS $%02X, A",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),

  BEQ_ZP(
      0x2C,
      "BEQ $%04X",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BEQ_ABS(
      0x2D,
      "BEQ $%04X",
      3,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BEQ_IDX(
      0x2E,
      "BEQ $%02X, A",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),

  BNE_ZP(
      0x2F,
      "BNE $%04X",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BNE_ABS(
      0x30,
      "BNE $%04X",
      3,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BNE_IDX(
      0x31,
      "BNE $%02X, A",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),

  BMI_ZP(
      0x32,
      "BMI $%04X",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BMI_ABS(
      0x33,
      "BMI $%04X",
      3,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BMI_IDX(
      0x34,
      "BMI $%02X, A",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),

  BPL_ZP(
      0x35,
      "BPL $%04X",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BPL_ABS(
      0x36,
      "BPL $%04X",
      3,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BPL_IDX(
      0x37,
      "BPL $%02X, A",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),

  BOC_ZP(
      0x38,
      "BOC $%04X",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BOC_ABS(
      0x39,
      "BOC $%04X",
      3,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BOC_IDX(
      0x3A,
      "BOC $%02X, A",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),

  BOS_ZP(
      0x3B,
      "BOS $%04X",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BOS_ABS(
      0x3C,
      "BOS $%04X",
      3,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  BOS_IDX(
      0x3D,
      "BOS $%02X, A",
      2,
      new int[][] {
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0},
        new int[] {0}
      }),
  HLT(0x3F, "HLT", 1, new int[][] {new int[] {HALT, PC_E | CU_RST}});

  private final int opcode;
  private final String format;
  private final int size;
  private final int[][] microcode;

  /**
   * @param opcode
   * @param size
   * @param microcode [x][y][z] x : status flags (not present defaults to y = 0) y : Control Unit
   *     Micro Step (starting after the first 2 mandatory fetch steps) z : signals (order is
   *     irrelevant)
   */
  Instruction(int opcode, String format, int size, int[][] microcode) {
    this.opcode = opcode;
    this.format = format;
    this.size = size;
    int flagStateCount = (1 << Flag.values().length);
    // microsteps are coded over 4 bits, so 16 possible values
    this.microcode = new int[flagStateCount][16];
    if (microcode.length > 0) {
      for (int flagState = 0; flagState < flagStateCount; flagState++) {
        int[] steps = microcode[microcode.length > flagState ? flagState : 0];
        if (steps == null) {
          steps = microcode[0];
        }
        if (steps.length > 0) {
          for (int step = 0; step < steps.length && step < 16; step++) {
            this.microcode[flagState][step] = steps[step];
          }
        }
      }
    }
  }

  public int getOpcode() {
    return opcode;
  }

  public int getSize() {
    return size;
  }

  public int getSignals(int step, int flags) {
    if (step >= 0 && step <= this.microcode.length) {
      return this.microcode[flags][step];
    }
    return 0;
  }

  public String getFormat() {
    return format;
  }

  public int[][] getMicrocode() {
    return microcode;
  }

  public AddressingMode getAddressingMode() {
    if (format.endsWith("$%02X, A")) {
      return AddressingMode.IDX;
    }
    if (format.endsWith("$%02X")) {
      return AddressingMode.Z_P;
    }
    if (format.endsWith("$%04X")) {
      return AddressingMode.IMM;
    }
    if (format.endsWith("#%02X")) {
      return AddressingMode.IMP;
    }
    return AddressingMode.IMM;
  }
}
