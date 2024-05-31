/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.execution;

import static simulation.emulation.constant.Signals.*;

import simulation.emulation.constant.Flag;

public enum Instruction {
  NOP(0x00, "NOP", 1, new int[][] {new int[] {PC_E | CU_RST}}),
  LDA_IMM(
      0x01,
      "LDA %02X",
      2,
      new int[][] {new int[] {PC_E, MAR_IN_PC, RAM_OUT | A_IN, PC_E | CU_RST}}),
  LDA_ABS(
      0x02,
      "LDA $(%02X)",
      2,
      new int[][] {new int[] {PC_E, MAR_IN_PC, RAM_OUT | MAR_IN, RAM_OUT | A_IN, PC_E | CU_RST}}),
  LDB_IMM(0x03, "LDB %02X", 2, new int[][] {new int[] {0}}),
  LDB_ABS(0x04, "LDB $(%02X)", 2, new int[][] {new int[] {0}}),
  OUT_IMM(0x05, "OUT %02X", 2, new int[][] {new int[] {0}}),
  OUT_ABS(0x06, "OUT $(%02X)", 2, new int[][] {new int[] {0}}),
  OUT(0x07, "OUT", 1, new int[][] {new int[] {0}}),
  STA_IMM(0x08, "STA %02X", 2, new int[][] {new int[] {0}}),
  STA_ABS(0x09, "STA $(%02X)", 2, new int[][] {new int[] {0}}),
  ADD_IMM(0x0A, "ADD %02X", 2, new int[][] {new int[] {0}}),
  ADD_ABS(0x0B, "ADD $(%02X)", 2, new int[][] {new int[] {0}}),
  SUB_IMM(0x0C, "SUB %02X", 2, new int[][] {new int[] {0}}),
  SUB_ABS(0x0D, "SUB $(%02X)", 2, new int[][] {new int[] {0}}),
  CMP_IMM(0x0E, "CMP %02X", 2, new int[][] {new int[] {0}}),
  CMP_ABS(0x0F, "CMP $(%02X)", 2, new int[][] {new int[] {0}}),
  CMP(0x10, "CMP", 1, new int[][] {new int[] {0}}),
  JMP_IMM(0x11, "JMP %02X", 2, new int[][] {new int[] {0}}),
  JMP_ABS(0x12, "JMP $(%02X)", 2, new int[][] {new int[] {0}}),
  JMA(0x13, "JMA", 1, new int[][] {new int[] {0}}),
  BCS_IMM(
      0x14,
      "BCS %02X",
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
      0x15,
      "BCS $(%02X)",
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
  BCC_IMM(
      0x16,
      "BCC %02X",
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
      0x17,
      "BCC $(%02X)",
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
  BEQ_IMM(
      0x18,
      "BEQ %02X",
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
      0x19,
      "BEQ $(%02X)",
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
  BNE_IMM(
      0x1A,
      "BNE %02X",
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
      0x1B,
      "BNE $(%02X)",
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
  BMI_IMM(
      0x1C,
      "BMI %02X",
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
      0x1D,
      "BMI $(%02X)",
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
  BPL_IMM(
      0x1E,
      "BPL %02X",
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
      0x1F,
      "BPL $(%02X)",
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
  BOS_IMM(
      0x20,
      "BOS %02X",
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
      0x21,
      "BOS $(%02X)",
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
  BOC_IMM(
      0x22,
      "BOC %02X",
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
      0x23,
      "BOC $(%02X)",
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
    for (int flagState = 0; flagState < flagStateCount; flagState++) {
      this.microcode[flagState][0] = MAR_IN_PC;
      this.microcode[flagState][1] = RAM_OUT | IR_IN;
    }

    if (microcode.length > 0) {
      for (int flagState = 0; flagState < flagStateCount; flagState++) {
        int[] steps = microcode[microcode.length > flagState ? flagState : 0];
        if (steps == null) {
          steps = microcode[0];
        }
        if (steps.length > 0) {
          for (int additionalStep = 0;
              additionalStep < steps.length && additionalStep < 14;
              additionalStep++) {
            this.microcode[flagState][2 + additionalStep] = steps[additionalStep];
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
}
