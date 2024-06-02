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
  NOP(
      0x00,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),

  LDA_IMM(
      0x01,
      "LDA",
      AddressingMode.IMM,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | A_IN | CU_RST | PC_E
        }
      }),
  LDA_ZP(
      0x02,
      "LDA",
      AddressingMode.Z_P,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          HL_IN_HIGH,
          MAR_IN_16 | HL_OUT_16,
          RAM_OUT | A_IN | CU_RST | PC_E
        }
      }),
  LDA_ABS(
      0x03,
      "LDA",
      AddressingMode.ABS,
      3,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_HIGH | PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          MAR_IN_16 | HL_OUT_16,
          RAM_OUT | A_IN | CU_RST | PC_E
        }
      }),

  LDB_IMM(
      0x04,
      "LDB",
      AddressingMode.IMM,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | B_IN | CU_RST | PC_E
        }
      }),
  LDB_ZP(
      0x05,
      "LDB",
      AddressingMode.Z_P,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          HL_IN_HIGH,
          MAR_IN_16 | HL_OUT_16,
          RAM_OUT | B_IN | CU_RST | PC_E
        }
      }),
  LDB_ABS(
      0x06,
      "LDB",
      AddressingMode.ABS,
      3,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_HIGH | PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          MAR_IN_16 | HL_OUT_16,
          RAM_OUT | B_IN | CU_RST | PC_E
        }
      }),

  STA_ZP(
      0x07,
      "STA",
      AddressingMode.Z_P,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          HL_IN_HIGH,
          MAR_IN_16 | HL_OUT_16,
          RAM_IN | A_OUT | CU_RST | PC_E
        }
      }),
  STA_ABS(
      0x08,
      "STA",
      AddressingMode.ABS,
      3,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_HIGH | PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          MAR_IN_16 | HL_OUT_16,
          RAM_IN | A_OUT | CU_RST | PC_E
        }
      }),

  ADD_IMM(
      0x0B,
      "ADD",
      AddressingMode.IMM,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | B_IN,
          ALU_OUT | A_IN | CU_RST | PC_E
        }
      }),
  ADD_ZP(
      0x0C,
      "ADD",
      AddressingMode.Z_P,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          HL_IN_HIGH,
          MAR_IN_16 | HL_OUT_16,
          RAM_OUT | B_IN,
          ALU_OUT | A_IN | CU_RST | PC_E
        }
      }),
  ADD_ABS(
      0x0D,
      "ADD",
      AddressingMode.ABS,
      3,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_HIGH | PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          MAR_IN_16 | HL_OUT_16,
          RAM_OUT | B_IN,
          ALU_OUT | A_IN | CU_RST | PC_E
        }
      }),
  ADD_IDX(
      0x0E,
      "ADD",
      AddressingMode.IDX,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_HIGH,
          A_OUT | HL_IN_LOW,
          MAR_IN_16 | HL_OUT_16,
          RAM_OUT | B_IN,
          ALU_OUT | A_IN | CU_RST | PC_E
        }
      }),

  SUB_IMM(
      0x0F,
      "SUB",
      AddressingMode.IMM,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | B_IN,
          SUB | ALU_OUT | A_IN | CU_RST | PC_E
        }
      }),
  SUB_ZP(
      0x10,
      "SUB",
      AddressingMode.Z_P,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          HL_IN_HIGH,
          MAR_IN_16 | HL_OUT_16,
          RAM_OUT | B_IN,
          SUB | ALU_OUT | A_IN | CU_RST | PC_E
        }
      }),
  SUB_ABS(
      0x11,
      "SUB",
      AddressingMode.ABS,
      3,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_HIGH | PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          MAR_IN_16 | HL_OUT_16,
          RAM_OUT | B_IN,
          SUB | ALU_OUT | A_IN | CU_RST | PC_E
        }
      }),
  SUB_IDX(
      0x12,
      "SUB",
      AddressingMode.IDX,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_HIGH,
          A_OUT | HL_IN_LOW,
          MAR_IN_16 | HL_OUT_16,
          RAM_OUT | B_IN,
          SUB | ALU_OUT | A_IN | CU_RST | PC_E
        }
      }),

  CLS(
      0x14,
      "CLS",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, STATUS_IN | CU_RST | PC_E}}),

  CMP(
      0x15,
      "CMP",
      AddressingMode.IMP,
      1,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, SUB | ALU_OUT | CU_RST | PC_E}
      }),
  CMP_IMM(
      0x16,
      "CMP",
      AddressingMode.IMM,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | B_IN,
          SUB | ALU_OUT | CU_RST | PC_E
        }
      }),
  CMP_ZP(
      0x17,
      "CMP",
      AddressingMode.Z_P,
      2,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          HL_IN_HIGH,
          MAR_IN_16 | HL_OUT_16,
          RAM_OUT | B_IN,
          SUB | ALU_OUT | CU_RST | PC_E
        }
      }),
  CMP_ABS(
      0x18,
      "CMP",
      AddressingMode.ABS,
      3,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_HIGH | PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          MAR_IN_16 | HL_OUT_16,
          RAM_OUT | B_IN,
          SUB | ALU_OUT | CU_RST | PC_E
        }
      }),

  PHA(
      0x19,
      "PHA",
      AddressingMode.IMP,
      1,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          MAR_IN_16 | STACK_OUT_16,
          STACK_PUSH | RAM_IN | A_OUT | CU_RST | PC_E
        }
      }),
  PHS(
      0x1B,
      "PHS",
      AddressingMode.IMP,
      1,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          MAR_IN_16 | STACK_OUT_16,
          STACK_PUSH | RAM_IN | STATUS_OUT | CU_RST | PC_E
        }
      }),

  PLA(
      0x1C,
      "PLA",
      AddressingMode.IMP,
      1,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          STACK_POP,
          MAR_IN_16 | STACK_OUT_16,
          RAM_OUT | A_IN | CU_RST | PC_E
        }
      }),
  PLB(
      0x1D,
      "PLB",
      AddressingMode.IMP,
      1,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          STACK_POP,
          MAR_IN_16 | STACK_OUT_16,
          RAM_OUT | B_IN | CU_RST | PC_E
        }
      }),
  PLS(
      0x1E,
      "PLS",
      AddressingMode.IMP,
      1,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          STACK_POP,
          MAR_IN_16 | STACK_OUT_16,
          RAM_OUT | STATUS_IN | CU_RST | PC_E
        }
      }),

  JSR_ABS(
      0x20,
      "JSR",
      AddressingMode.ABS,
      3,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          MAR_IN_16 | STACK_OUT_16,
          STACK_PUSH | RAM_IN | PC_OUT_16,
          MAR_IN_16 | STACK_OUT_16,
          STACK_PUSH | RAM_IN | PC_OUT_HIGH | PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_HIGH | PC_E,
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | HL_IN_LOW,
          PC_IN_16 | HL_OUT_16 | CU_RST
        }
      }),
  RTS(
      0x21,
      "RTS",
      AddressingMode.IMP,
      1,
      new int[][] {
        new int[] {
          MAR_IN_16 | PC_OUT_16,
          RAM_OUT | IR_IN,
          STACK_POP,
          MAR_IN_16 | STACK_OUT_16,
          RAM_OUT | HL_IN_HIGH,
          STACK_POP,
          MAR_IN_16 | STACK_OUT_16,
          RAM_OUT | HL_IN_LOW,
          HL_OUT_16 | PC_IN_16,
          PC_E,
          PC_E,
          CU_RST | PC_E
        }
      }),
  JMP_ZP(0x22, "JMP", AddressingMode.Z_P, 2, new int[][] {Instructions.JMP_ZP_MICROCODE}),
  JMP_ABS(0x23, "JMP", AddressingMode.ABS, 3, new int[][] {Instructions.JMP_ABS_MICROCODE}),
  JMP_IDX(0x24, "JMP", AddressingMode.IDX, 2, new int[][] {Instructions.JMP_IDX_MICROCODE}),

  BCS_ZP(
      0x25,
      "BCS",
      AddressingMode.Z_P,
      2,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE
      }),
  BCS_ABS(
      0x26,
      "BCS",
      AddressingMode.ABS,
      3,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE
      }),
  BCS_IDX(
      0x27,
      "BCS",
      AddressingMode.IDX,
      2,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE
      }),

  BCC_ZP(
      0x28,
      "BCC",
      AddressingMode.Z_P,
      2,
      new int[][] {
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST}
      }),
  BCC_ABS(
      0x29,
      "BCC",
      AddressingMode.ABS,
      3,
      new int[][] {
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST}
      }),
  BCC_IDX(
      0x2A,
      "BCC",
      AddressingMode.IDX,
      2,
      new int[][] {
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST}
      }),

  BNE_ZP(
      0x2B,
      "BNE",
      AddressingMode.Z_P,
      2,
      new int[][] {
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST}
      }),
  BNE_ABS(
      0x2C,
      "BNE",
      AddressingMode.ABS,
      3,
      new int[][] {
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST}
      }),
  BNE_IDX(
      0x2D,
      "BNE",
      AddressingMode.IDX,
      2,
      new int[][] {
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST}
      }),

  BEQ_ZP(
      0x2E,
      "BEQ",
      AddressingMode.Z_P,
      2,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE
      }),
  BEQ_ABS(
      0x2F,
      "BEQ",
      AddressingMode.ABS,
      3,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE
      }),
  BEQ_IDX(
      0x30,
      "BEQ",
      AddressingMode.IDX,
      2,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE
      }),

  BPL_ZP(
      0x31,
      "BPL",
      AddressingMode.Z_P,
      2,
      new int[][] {
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST}
      }),
  BPL_ABS(
      0x32,
      "BPL",
      AddressingMode.ABS,
      3,
      new int[][] {
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST}
      }),
  BPL_IDX(
      0x33,
      "BPL",
      AddressingMode.IDX,
      2,
      new int[][] {
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST}
      }),

  BMI_ZP(
      0x34,
      "BMI",
      AddressingMode.Z_P,
      2,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST}
      }),
  BMI_ABS(
      0x35,
      "BMI",
      AddressingMode.ABS,
      3,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST}
      }),
  BMI_IDX(
      0x36,
      "BMI",
      AddressingMode.IDX,
      2,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST}
      }),

  BOC_ZP(
      0x37,
      "BOC",
      AddressingMode.Z_P,
      2,
      new int[][] {
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST}
      }),
  BOC_ABS(
      0x38,
      "BOC",
      AddressingMode.ABS,
      3,
      new int[][] {
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST}
      }),
  BOC_IDX(
      0x39,
      "BOC",
      AddressingMode.IDX,
      2,
      new int[][] {
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST}
      }),

  BOS_ZP(
      0x3A,
      "BOS",
      AddressingMode.Z_P,
      2,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE,
        Instructions.JMP_ZP_MICROCODE
      }),
  BOS_ABS(
      0x3B,
      "BOS",
      AddressingMode.ABS,
      3,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E, PC_E | CU_RST},
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE,
        Instructions.JMP_ABS_MICROCODE
      }),
  BOS_IDX(
      0x3C,
      "BOS",
      AddressingMode.IDX,
      2,
      new int[][] {
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, PC_E | CU_RST},
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE,
        Instructions.JMP_IDX_MICROCODE
      }),
  HLT(
      0x3F,
      "HLT",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, HALT, PC_E | CU_RST}});

  private final int opcode;
  private final String name;
  private final AddressingMode addressingMode;
  private final int size;
  private final int[][] microcode;

  /**
   * @param opcode
   * @param size
   * @param microcode [x][y] x : status flags (not present defaults to y = 0) y : Control Unit Micro
   *     Step (starting after the first 2 mandatory fetch steps)
   */
  Instruction(int opcode, String name, AddressingMode addressingMode, int size, int[][] microcode) {
    this.opcode = opcode;
    this.name = name;
    this.addressingMode = addressingMode;
    this.size = size;
    int flagStateCount = (1 << Flag.values().length);
    // microsteps are coded over 4 bits, so 16 possible values
    this.microcode = new int[flagStateCount][16];
    if (microcode.length > 0) {
      for (int flagState = 0; flagState < flagStateCount; flagState++) {
        int[] steps = microcode[microcode.length > flagState ? flagState : 0];
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

  public String format(int arg) {
    return name + " " + addressingMode.format(arg);
  }

  public int[][] getMicrocode() {
    return microcode;
  }

  public AddressingMode getAddressingMode() {
    return addressingMode;
  }
}
