package simulation.emulation.execution;

import simulation.emulation.constant.Flag;
import simulation.emulation.constant.Signal;

public enum Instruction {
    NOP(0x00, "NOP", "No operation", 1, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    LDA_IMM(0x01, "LDA %02X", "LOAD A immediate, loads the next byte into the A register", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.A_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    LDA_ABS(0x02, "LDA $(%02X)", "LOAD A absolute, loads the value at the address of the next byte in the A register", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.A_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    LDB_IMM(0x03, "LDB %02X", "LOAD B immediate, loads the next byte into the B register", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    LDB_ABS(0x04, "LDB $(%02X)", "LOAD B absolute, loads the value at the address of the next byte in the B register", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    LDS_IMM(0x05, "LDS %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.SR_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    LDS_ABS(0x06, "LDS $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.SR_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    OUT_IMM(0x07, "OUT %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.OUT_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    OUT_ABS(0x08, "OUT $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.OUT_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    OUT(0x09, "OUT", "", 1, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.A_OUT, Signal.OUT_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    STA_IMM(0x0A, "STA %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.A_OUT, Signal.RAM_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    STA_ABS(0x0B, "STA $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.A_OUT, Signal.RAM_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    STB_IMM(0x0C, "STB %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.B_OUT, Signal.RAM_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    STB_ABS(0x0D, "STB $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.B_OUT, Signal.RAM_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    STS_IMM(0x0E, "STS %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.SR_OUT, Signal.RAM_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    STS_ABS(0x0F, "STS $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.SR_OUT, Signal.RAM_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    CLS(0x10, "CLS", "", 1, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.SR_RST},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    ADD_IMM(0x11, "ADD %02X", "ADD immediate, Add the next byte to the A register", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN},
                            new Signal[]{Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    ADD_ABS(0x12, "ADD $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN},
                            new Signal[]{Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    SUB_IMM(0x13, "SUB %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN, Signal.SUB},
                            new Signal[]{Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH, Signal.SUB},
                            new Signal[]{Signal.C_E, Signal.CU_RST, Signal.SUB}
                    }
            }
    ),
    SUB_ABS(0x14, "SUB $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN, Signal.SUB},
                            new Signal[]{Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH, Signal.SUB},
                            new Signal[]{Signal.C_E, Signal.CU_RST, Signal.SUB}
                    }
            }
    ),
    XOR_IMM(0x15, "XOR %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN, Signal.XOR},
                            new Signal[]{Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH, Signal.XOR},
                            new Signal[]{Signal.C_E, Signal.CU_RST, Signal.XOR}
                    }
            }
    ),
    XOR_ABS(0x16, "XOR $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN, Signal.XOR},
                            new Signal[]{Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH, Signal.XOR},
                            new Signal[]{Signal.C_E, Signal.CU_RST, Signal.XOR}
                    }
            }
    ),
    AND_IMM(0x17, "AND %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN, Signal.AND},
                            new Signal[]{Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH, Signal.AND},
                            new Signal[]{Signal.C_E, Signal.CU_RST, Signal.AND}
                    }
            }
    ),
    AND_ABS(0x18, "AND $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN, Signal.AND},
                            new Signal[]{Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH, Signal.AND},
                            new Signal[]{Signal.C_E, Signal.CU_RST, Signal.AND}
                    }
            }
    ),
    ORA_IMM(0x19, "ORA %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN, Signal.OR},
                            new Signal[]{Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH, Signal.OR},
                            new Signal[]{Signal.C_E, Signal.CU_RST, Signal.OR}
                    }
            }
    ),
    ORA_ABS(0x1A, "ORA $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN, Signal.OR},
                            new Signal[]{Signal.ALU_OUT, Signal.A_IN, Signal.SR_LATCH, Signal.OR},
                            new Signal[]{Signal.C_E, Signal.CU_RST, Signal.OR}
                    }
            }
    ),
    CMP_IMM(0x1B, "CMP %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN, Signal.SUB},
                            new Signal[]{Signal.SR_LATCH, Signal.SUB},
                            new Signal[]{Signal.C_E, Signal.CU_RST, Signal.SUB}
                    }
            }
    ),
    CMP_ABS(0x1C, "CMP $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN, Signal.SUB},
                            new Signal[]{Signal.SR_LATCH, Signal.SUB},
                            new Signal[]{Signal.C_E, Signal.CU_RST, Signal.SUB}
                    }
            }
    ),
    CMP(0x1D, "CMP", "", 1, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.SR_LATCH, Signal.SUB},
                            new Signal[]{Signal.C_E, Signal.CU_RST, Signal.SUB}
                    }
            }
    ),
    JMP_IMM(0x1E, "JMP %02X", "JMP immediate, jump to the address in the next byte", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    }
            }
    ),
    JMP_ABS(0x1F, "JMP $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    }
            }
    ),
    JMA(0x20, "JMA", "", 1, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.A_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    }
            }
    ),
    BCS_IMM(0x21, "BCS %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    }
            }
    ),
    BCS_ABS(0x22, "BCS $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    }
            }
    ),
    BCC_IMM(0x23, "BCC %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    BCC_ABS(0x24, "BCC $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    BEQ_IMM(0x25, "BEQ %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    }
            }
    ),
    BEQ_ABS(0x26, "BEQ $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    }
            }
    ),
    BNE_IMM(0x27, "BNE %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    BNE_ABS(0x28, "BNE $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    BMI_IMM(0x29, "BMI %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    }
            }
    ),
    BMI_ABS(0x2A, "BMI $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    }
            }
    ),
    BPL_IMM(0x2B, "BPL %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    BPL_ABS(0x2C, "BPL $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    BOS_IMM(0x2D, "BOS %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
            }
    ),
    BOS_ABS(0x2E, "BOS $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    }
            }
    ),
    BOC_IMM(0x2F, "BOC %02X", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    BOC_ABS(0x30, "BOC $(%02X)", "", 2, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //   N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //  CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // Z N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ // ZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    },
                    new Signal[][]{ //O
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O  N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O C
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //O CN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZ N
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZC
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    },
                    new Signal[][]{ //OZCN
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    HLT(0x3F, "HLT", "HALT, Hold the CPU from further execution", 1, new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.HLT},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    );

    private final int opcode;
    private final String format;
    private final String desc;
    private final int size;
    private final int[][] microcode;


    /**
     * @param opcode
     * @param desc
     * @param size
     * @param microcode [x][y][z]
     *                  x : status flags (not present defaults to y = 0)
     *                  y : Control Unit Micro Step (starting after the first 2 mandatory fetch steps)
     *                  z : signals (order is irrelevant)
     */
    Instruction(int opcode, String format, String desc, int size, Signal[][][] microcode) {
        this.opcode = opcode;
        this.format = format;
        this.desc = desc;
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
                    for (int additionalStep = 0; additionalStep < steps.length && additionalStep < 14; additionalStep++) {
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

    public String getDesc() {
        return desc;
    }
}