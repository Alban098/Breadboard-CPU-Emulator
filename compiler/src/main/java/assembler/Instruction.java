/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler;

public enum Instruction {
  NOP(0x00, "NOP", 1, false),
  LDA_IMM(0x01, "LDA $%02X", 2, false),
  LDA_ABS(0x02, "LDA $(%02X)", 2, false),
  LDB_IMM(0x03, "LDB $%02X", 2, false),
  LDB_ABS(0x04, "LDB $(%02X)", 2, false),
  LDS_IMM(0x05, "LDS $%02X", 2, false),
  LDS_ABS(0x06, "LDS $(%02X)", 2, false),
  OUT_IMM(0x07, "OUT $%02X", 2, false),
  OUT_ABS(0x08, "OUT $(%02X)", 2, false),
  OUT(0x09, "OUT", 1, false),
  STA_IMM(0x0A, "STA $%02X", 2, false),
  STA_ABS(0x0B, "STA $(%02X)", 2, false),
  STB_IMM(0x0C, "STB $%02X", 2, false),
  STB_ABS(0x0D, "STB $(%02X)", 2, false),
  STS_IMM(0x0E, "STS $%02X", 2, false),
  STS_ABS(0x0F, "STS $(%02X)", 2, false),
  CLS(0x10, "CLS", 1, false),
  ADD_IMM(0x11, "ADD $%02X", 2, false),
  ADD_ABS(0x12, "ADD $(%02X)", 2, false),
  SUB_IMM(0x13, "SUB $%02X", 2, false),
  SUB_ABS(0x14, "SUB $(%02X)", 2, false),
  XOR_IMM(0x15, "XOR $%02X", 2, false),
  XOR_ABS(0x16, "XOR $(%02X)", 2, false),
  AND_IMM(0x17, "AND $%02X", 2, false),
  AND_ABS(0x18, "AND $(%02X)", 2, false),
  ORA_IMM(0x19, "ORA $%02X", 2, false),
  ORA_ABS(0x1A, "ORA $(%02X)", 2, false),
  CMP_IMM(0x1B, "CMP $%02X", 2, false),
  CMP_ABS(0x1C, "CMP $(%02X)", 2, false),
  CMP(0x1D, "CMP", 1, false),
  JMP_IMM(0x1E, "JMP $%02X", 2, true),
  JMP_ABS(0x1F, "JMP $(%02X)", 2, true),
  JMA(0x20, "JMA", 1, false),
  BCS_IMM(0x21, "BCS $%02X", 2, true),
  BCS_ABS(0x22, "BCS $(%02X)", 2, true),
  BCC_IMM(0x23, "BCC $%02X", 2, true),
  BCC_ABS(0x24, "BCC $(%02X)", 2, true),
  BEQ_IMM(0x25, "BEQ $%02X", 2, true),
  BEQ_ABS(0x26, "BEQ $(%02X)", 2, true),
  BNE_IMM(0x27, "BNE $%02X", 2, true),
  BNE_ABS(0x28, "BNE $(%02X)", 2, true),
  BMI_IMM(0x29, "BMI $%02X", 2, true),
  BMI_ABS(0x2A, "BMI $(%02X)", 2, true),
  BPL_IMM(0x2B, "BPL $%02X", 2, true),
  BPL_ABS(0x2C, "BPL $(%02X)", 2, true),
  BOS_IMM(0x2D, "BOS $%02X", 2, true),
  BOS_ABS(0x2E, "BOS $(%02X)", 2, true),
  BOC_IMM(0x2F, "BOC $%02X", 2, true),
  BOC_ABS(0x30, "BOC $(%02X)", 2, true),
  HLT(0x3F, "HLT", 1, false);

  private final int opcode;
  private final String format;
  private final int size;
  private final boolean acceptLabels;

  /**
   * @param opcode
   * @param size
   * @param acceptLabels
   */
  Instruction(int opcode, String format, int size, boolean acceptLabels) {
    this.opcode = opcode;
    this.format = format;
    this.size = size;
    this.acceptLabels = acceptLabels;
  }

  public int getOpcode() {
    return opcode;
  }

  public int getSize() {
    return size;
  }

  public boolean acceptLabels() {
    return acceptLabels;
  }

  public String getFormat() {
    return format;
  }
}
