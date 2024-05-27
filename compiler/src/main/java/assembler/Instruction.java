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
  OUT_IMM(0x05, "OUT $%02X", 2, false),
  OUT_ABS(0x06, "OUT $(%02X)", 2, false),
  OUT(0x07, "OUT", 1, false),
  STA_IMM(0x08, "STA $%02X", 2, false),
  STA_ABS(0x09, "STA $(%02X)", 2, false),
  ADD_IMM(0x0A, "ADD $%02X", 2, false),
  ADD_ABS(0x0B, "ADD $(%02X)", 2, false),
  SUB_IMM(0x0C, "SUB $%02X", 2, false),
  SUB_ABS(0x0D, "SUB $(%02X)", 2, false),
  CMP_IMM(0x0E, "CMP $%02X", 2, false),
  CMP_ABS(0x0F, "CMP $(%02X)", 2, false),
  CMP(0x10, "CMP", 1, false),
  JMP_IMM(0x11, "JMP $%02X", 2, true),
  JMP_ABS(0x12, "JMP $(%02X)", 2, true),
  JMA(0x13, "JMA", 1, false),
  BCS_IMM(0x14, "BCS $%02X", 2, true),
  BCS_ABS(0x15, "BCS $(%02X)", 2, true),
  BCC_IMM(0x16, "BCC $%02X", 2, true),
  BCC_ABS(0x17, "BCC $(%02X)", 2, true),
  BEQ_IMM(0x18, "BEQ $%02X", 2, true),
  BEQ_ABS(0x19, "BEQ $(%02X)", 2, true),
  BNE_IMM(0x1A, "BNE $%02X", 2, true),
  BNE_ABS(0x1B, "BNE $(%02X)", 2, true),
  BMI_IMM(0x1C, "BMI $%02X", 2, true),
  BMI_ABS(0x1D, "BMI $(%02X)", 2, true),
  BPL_IMM(0x1E, "BPL $%02X", 2, true),
  BPL_ABS(0x1F, "BPL $(%02X)", 2, true),
  BOS_IMM(0x20, "BOS $%02X", 2, true),
  BOS_ABS(0x21, "BOS $(%02X)", 2, true),
  BOC_IMM(0x22, "BOC $%02X", 2, true),
  BOC_ABS(0x23, "BOC $(%02X)", 2, true),
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

  public String getName() {
    return this.format.split(" ")[0];
  }
}
