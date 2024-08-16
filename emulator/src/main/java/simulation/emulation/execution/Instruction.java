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
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, HALT, PC_E | CU_RST}}),
  NOP_40(
      0x40,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_41(
      0x41,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_42(
      0x42,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_43(
      0x43,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_44(
      0x44,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_45(
      0x45,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_46(
      0x46,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_47(
      0x47,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_48(
      0x48,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_49(
      0x49,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_4A(
      0x4A,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_4B(
      0x4B,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_4C(
      0x4C,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_4D(
      0x4D,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_4E(
      0x4E,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_4F(
      0x4F,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_50(
      0x50,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_51(
      0x51,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_52(
      0x52,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_53(
      0x53,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_54(
      0x54,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_55(
      0x55,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_56(
      0x56,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_57(
      0x57,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_58(
      0x58,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_59(
      0x59,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_5A(
      0x5A,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_5B(
      0x5B,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_5C(
      0x5C,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_5D(
      0x5D,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_5E(
      0x5E,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_5F(
      0x5F,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_60(
      0x60,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_61(
      0x61,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_62(
      0x62,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_63(
      0x63,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_64(
      0x64,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_65(
      0x65,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_66(
      0x66,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_67(
      0x67,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_68(
      0x68,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_69(
      0x69,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_6A(
      0x6A,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_6B(
      0x6B,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_6C(
      0x6C,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_6D(
      0x6D,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_6E(
      0x6E,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_6F(
      0x6F,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_70(
      0x70,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_71(
      0x71,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_72(
      0x72,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_73(
      0x73,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_74(
      0x74,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_75(
      0x75,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_76(
      0x76,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_77(
      0x77,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_78(
      0x78,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_79(
      0x79,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_7A(
      0x7A,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_7B(
      0x7B,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_7C(
      0x7C,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_7D(
      0x7D,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_7E(
      0x7E,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_7F(
      0x7F,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_80(
      0x80,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_81(
      0x81,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_82(
      0x82,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_83(
      0x83,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_84(
      0x84,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_85(
      0x85,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_86(
      0x86,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_87(
      0x87,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_88(
      0x88,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_89(
      0x89,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_8A(
      0x8A,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_8B(
      0x8B,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_8C(
      0x8C,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_8D(
      0x8D,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_8E(
      0x8E,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_8F(
      0x8F,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_90(
      0x90,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_91(
      0x91,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_92(
      0x92,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_93(
      0x93,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_94(
      0x94,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_95(
      0x95,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_96(
      0x96,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_97(
      0x97,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_98(
      0x98,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_99(
      0x99,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_9A(
      0x9A,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_9B(
      0x9B,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_9C(
      0x9C,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_9D(
      0x9D,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_9E(
      0x9E,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_9F(
      0x9F,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_A0(
      0xA0,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_A1(
      0xA1,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_A2(
      0xA2,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_A3(
      0xA3,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_A4(
      0xA4,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_A5(
      0xA5,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_A6(
      0xA6,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_A7(
      0xA7,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_A8(
      0xA8,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_A9(
      0xA9,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_AA(
      0xAA,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_AB(
      0xAB,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_AC(
      0xAC,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_AD(
      0xAD,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_AE(
      0xAE,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_AF(
      0xAF,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_B0(
      0xB0,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_B1(
      0xB1,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_B2(
      0xB2,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_B3(
      0xB3,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_B4(
      0xB4,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_B5(
      0xB5,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_B6(
      0xB6,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_B7(
      0xB7,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_B8(
      0xB8,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_B9(
      0xB9,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_BA(
      0xBA,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_BB(
      0xBB,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_BC(
      0xBC,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_BD(
      0xBD,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_BE(
      0xBE,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_BF(
      0xBF,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_C0(
      0xC0,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_C1(
      0xC1,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_C2(
      0xC2,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_C3(
      0xC3,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_C4(
      0xC4,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_C5(
      0xC5,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_C6(
      0xC6,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_C7(
      0xC7,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_C8(
      0xC8,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_C9(
      0xC9,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_CA(
      0xCA,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_CB(
      0xCB,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_CC(
      0xCC,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_CD(
      0xCD,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_CE(
      0xCE,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_CF(
      0xCF,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_D0(
      0xD0,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_D1(
      0xD1,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_D2(
      0xD2,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_D3(
      0xD3,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_D4(
      0xD4,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_D5(
      0xD5,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_D6(
      0xD6,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_D7(
      0xD7,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_D8(
      0xD8,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_D9(
      0xD9,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_DA(
      0xDA,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_DB(
      0xDB,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_DC(
      0xDC,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_DD(
      0xDD,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_DE(
      0xDE,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_DF(
      0xDF,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_E0(
      0xE0,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_E1(
      0xE1,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_E2(
      0xE2,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_E3(
      0xE3,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_E4(
      0xE4,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_E5(
      0xE5,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_E6(
      0xE6,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_E7(
      0xE7,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_E8(
      0xE8,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_E9(
      0xE9,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_EA(
      0xEA,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_EB(
      0xEB,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_EC(
      0xEC,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_ED(
      0xED,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_EE(
      0xEE,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_EF(
      0xEF,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_F0(
      0xF0,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_F1(
      0xF1,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_F2(
      0xF2,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_F3(
      0xF3,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_F4(
      0xF4,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_F5(
      0xF5,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_F6(
      0xF6,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_F7(
      0xF7,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_F8(
      0xF8,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_F9(
      0xF9,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_FA(
      0xFA,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_FB(
      0xFB,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_FC(
      0xFC,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_FD(
      0xFD,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_FE(
      0xFE,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}}),
  NOP_FF(
      0xFF,
      "NOP",
      AddressingMode.IMP,
      1,
      new int[][] {new int[] {MAR_IN_16 | PC_OUT_16, RAM_OUT | IR_IN, PC_E, CU_RST}});

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
