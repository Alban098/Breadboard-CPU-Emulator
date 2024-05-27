/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.execution;

import simulation.emulation.constant.Flag;
import simulation.emulation.constant.Signal;

public enum Instruction {
  NOP(0x00, "NOP", 1, new Signal[][][] {new Signal[][] {new Signal[] {Signal.C_E, Signal.CU_RST}}}),
  LDA_IMM(
      0x01,
      "LDA %02X",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.A_IN},
          new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  LDA_ABS(
      0x02,
      "LDA $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.A_IN},
          new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  LDB_IMM(
      0x03,
      "LDB %02X",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.B_IN},
          new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  LDB_ABS(
      0x04,
      "LDB $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.B_IN},
          new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  OUT_IMM(
      0x05,
      "OUT %02X",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.OUT_IN},
          new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  OUT_ABS(
      0x06,
      "OUT $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.OUT_IN},
          new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  OUT(
      0x07,
      "OUT",
      1,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.A_OUT, Signal.OUT_IN},
          new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  STA_IMM(
      0x08,
      "STA %02X",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.A_OUT, Signal.RAM_IN},
          new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  STA_ABS(
      0x09,
      "STA $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.A_OUT, Signal.RAM_IN},
          new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  ADD_IMM(
      0x0A,
      "ADD %02X",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.B_IN},
          new Signal[] {Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH},
          new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  ADD_ABS(
      0x0B,
      "ADD $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.B_IN},
          new Signal[] {Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH},
          new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  SUB_IMM(
      0x0C,
      "SUB %02X",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.B_IN, Signal.SUB},
          new Signal[] {Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH, Signal.SUB},
          new Signal[] {Signal.C_E, Signal.CU_RST, Signal.SUB}
        }
      }),
  SUB_ABS(
      0x0D,
      "SUB $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.B_IN, Signal.SUB},
          new Signal[] {Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH, Signal.SUB},
          new Signal[] {Signal.C_E, Signal.CU_RST, Signal.SUB}
        }
      }),
  CMP_IMM(
      0x0E,
      "CMP %02X",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.B_IN, Signal.SUB},
          new Signal[] {Signal.SR_LATCH, Signal.SUB},
          new Signal[] {Signal.C_E, Signal.CU_RST, Signal.SUB}
        }
      }),
  CMP_ABS(
      0x0F,
      "CMP $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.B_IN, Signal.SUB},
          new Signal[] {Signal.SR_LATCH, Signal.SUB},
          new Signal[] {Signal.C_E, Signal.CU_RST, Signal.SUB}
        }
      }),
  CMP(
      0x10,
      "CMP",
      1,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.SR_LATCH, Signal.SUB},
          new Signal[] {Signal.C_E, Signal.CU_RST, Signal.SUB}
        }
      }),
  JMP_IMM(
      0x11,
      "JMP %02X",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        }
      }),
  JMP_ABS(
      0x12,
      "JMP $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        }
      }),
  JMA(
      0x13,
      "JMA",
      1,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.A_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        }
      }),
  BCS_IMM(
      0x14,
      "BCS %02X",
      2,
      new Signal[][][] {
        new Signal[][] {new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}},
        new Signal[][] { //   N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        }
      }),
  BCS_ABS(
      0x15,
      "BCS $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}},
        new Signal[][] { //   N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        }
      }),
  BCC_IMM(
      0x16,
      "BCC %02X",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //   N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  BCC_ABS(
      0x17,
      "BCC $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //   N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  BEQ_IMM(
      0x18,
      "BEQ %02X",
      2,
      new Signal[][][] {
        new Signal[][] {new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}},
        new Signal[][] { //   N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        }
      }),
  BEQ_ABS(
      0x19,
      "BEQ $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}},
        new Signal[][] { //   N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        }
      }),
  BNE_IMM(
      0x1A,
      "BNE %02X",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //   N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  BNE_ABS(
      0x1B,
      "BNE $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //   N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  BMI_IMM(
      0x1C,
      "BMI %02X",
      2,
      new Signal[][][] {
        new Signal[][] {new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}},
        new Signal[][] { //   N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        }
      }),
  BMI_ABS(
      0x1D,
      "BMI $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}},
        new Signal[][] { //   N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        }
      }),
  BPL_IMM(
      0x1E,
      "BPL %02X",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //   N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  BPL_ABS(
      0x1F,
      "BPL $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //   N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  BOS_IMM(
      0x20,
      "BOS %02X",
      2,
      new Signal[][][] {
        new Signal[][] {new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}},
        new Signal[][] { //   N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
      }),
  BOS_ABS(
      0x21,
      "BOS $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}},
        new Signal[][] { //   N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        }
      }),
  BOC_IMM(
      0x22,
      "BOC %02X",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //   N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  BOC_ABS(
      0x23,
      "BOC $(%02X)",
      2,
      new Signal[][][] {
        new Signal[][] {
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //   N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  C
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { //  CN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // Z N
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZC
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // ZCN
          new Signal[] {Signal.C_E},
          new Signal[] {Signal.C_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.MAR_IN},
          new Signal[] {Signal.RAM_OUT, Signal.C_IN},
          new Signal[] {Signal.CU_RST}
        },
        new Signal[][] { // O
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O  N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O C
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // O CN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZ N
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZC
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        },
        new Signal[][] { // OZCN
          new Signal[] {Signal.C_E}, new Signal[] {Signal.C_E, Signal.CU_RST}
        }
      }),
  HLT(
      0x3F,
      "HLT",
      1,
      new Signal[][][] {
        new Signal[][] {new Signal[] {Signal.HLT}, new Signal[] {Signal.C_E, Signal.CU_RST}}
      });

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
  Instruction(int opcode, String format, int size, Signal[][][] microcode) {
    this.opcode = opcode;
    this.format = format;
    this.size = size;
    int flagStateCount = (1 << Flag.values().length);
    // microsteps are coded over 4 bits, so 16 possible values
    this.microcode = new int[flagStateCount][16];
    for (int flagState = 0; flagState < flagStateCount; flagState++) {
      this.microcode[flagState][0] = Signal.C_OUT.getMask() | Signal.MAR_IN.getMask();
      this.microcode[flagState][1] = Signal.RAM_OUT.getMask() | Signal.IR_IN.getMask();
    }

    if (microcode.length > 0) {
      for (int flagState = 0; flagState < flagStateCount; flagState++) {
        Signal[][] steps = microcode[microcode.length > flagState ? flagState : 0];
        if (steps == null) {
          steps = microcode[0];
        }
        if (steps.length > 0) {
          for (int additionalStep = 0;
              additionalStep < steps.length && additionalStep < 14;
              additionalStep++) {
            Signal[] signals = steps[additionalStep];
            int mask = 0;
            for (Signal signal : signals) {
              mask |= signal.getMask();
            }
            this.microcode[flagState][2 + additionalStep] = mask;
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
