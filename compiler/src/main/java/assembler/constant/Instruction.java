/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.constant;

public enum Instruction {
  NOP(0x00, "NOP", AddressingMode.IMP, 1, false),

  LDA_IMM(0x01, "LDA", AddressingMode.IMM, 2, false),
  LDA_ZP(0x02, "LDA", AddressingMode.Z_P, 2, false),
  LDA_ABS(0x03, "LDA", AddressingMode.ABS, 3, false),

  LDB_IMM(0x04, "LDB", AddressingMode.IMM, 2, false),
  LDB_ZP(0x05, "LDB", AddressingMode.Z_P, 2, false),
  LDB_ABS(0x06, "LDB", AddressingMode.ABS, 3, false),

  STA_ZP(0x07, "STA", AddressingMode.Z_P, 2, false),
  STA_ABS(0x08, "STA", AddressingMode.ABS, 3, false),

  ADD_IMM(0x0B, "ADD", AddressingMode.IMM, 2, false),
  ADD_ZP(0x0C, "ADD", AddressingMode.Z_P, 2, false),
  ADD_ABS(0x0D, "ADD", AddressingMode.ABS, 3, false),
  ADD_IDX(0x0E, "ADD", AddressingMode.IDX, 2, false),

  SUB_IMM(0x0F, "SUB", AddressingMode.IMM, 2, false),
  SUB_ZP(0x10, "SUB", AddressingMode.Z_P, 2, false),
  SUB_ABS(0x11, "SUB", AddressingMode.ABS, 3, false),
  SUB_IDX(0x12, "SUB", AddressingMode.IDX, 2, false),

  CLS(0x14, "CLS", AddressingMode.IMP, 1, false),

  CMP(0x15, "CMP", AddressingMode.IMP, 1, false),
  CMP_IMM(0x16, "CMP", AddressingMode.IMM, 2, false),
  CMP_ZP(0x17, "CMP", AddressingMode.Z_P, 2, false),
  CMP_ABS(0x18, "CMP", AddressingMode.ABS, 3, false),

  PHA(0x19, "PHA", AddressingMode.IMP, 1, false),
  PHS(0x1B, "PHS", AddressingMode.IMP, 1, false),

  PLA(0x1C, "PLA", AddressingMode.IMP, 1, false),
  PLB(0x1D, "PLB", AddressingMode.IMP, 1, false),
  PLS(0x1E, "PLS", AddressingMode.IMP, 1, false),

  JSR_ABS(0x20, "JSR", AddressingMode.ABS, 3, true),
  RTS(0x21, "RTS", AddressingMode.IMP, 1, false),
  JMP_ZP(0x22, "JMP", AddressingMode.Z_P, 2, false),
  JMP_ABS(0x23, "JMP", AddressingMode.ABS, 3, true),
  JMP_IDX(0x24, "JMP", AddressingMode.IDX, 2, false),

  BCS_ZP(0x25, "BCS", AddressingMode.Z_P, 2, false),
  BCS_ABS(0x26, "BCS", AddressingMode.ABS, 3, true),
  BCS_IDX(0x27, "BCS", AddressingMode.IDX, 2, false),

  BCC_ZP(0x28, "BCC", AddressingMode.Z_P, 2, false),
  BCC_ABS(0x29, "BCC", AddressingMode.ABS, 3, true),
  BCC_IDX(0x2A, "BCC", AddressingMode.IDX, 2, false),

  BNE_ZP(0x2B, "BNE", AddressingMode.Z_P, 2, false),
  BNE_ABS(0x2C, "BNE", AddressingMode.ABS, 3, true),
  BNE_IDX(0x2D, "BNE", AddressingMode.IDX, 2, false),

  BEQ_ZP(0x2E, "BEQ", AddressingMode.Z_P, 2, false),
  BEQ_ABS(0x2F, "BEQ", AddressingMode.ABS, 3, true),
  BEQ_IDX(0x30, "BEQ", AddressingMode.IDX, 2, false),

  BPL_ZP(0x31, "BPL", AddressingMode.Z_P, 2, false),
  BPL_ABS(0x32, "BPL", AddressingMode.ABS, 3, true),
  BPL_IDX(0x33, "BPL", AddressingMode.IDX, 2, false),

  BMI_ZP(0x34, "BMI", AddressingMode.Z_P, 2, false),
  BMI_ABS(0x35, "BMI", AddressingMode.ABS, 3, true),
  BMI_IDX(0x36, "BMI", AddressingMode.IDX, 2, false),

  BOC_ZP(0x37, "BOC", AddressingMode.Z_P, 2, false),
  BOC_ABS(0x38, "BOC", AddressingMode.ABS, 3, true),
  BOC_IDX(0x39, "BOC", AddressingMode.IDX, 2, false),

  BOS_ZP(0x3A, "BOS", AddressingMode.Z_P, 2, false),
  BOS_ABS(0x3B, "BOS", AddressingMode.ABS, 3, true),
  BOS_IDX(0x3C, "BOS", AddressingMode.IDX, 2, false),
  HLT(0x3F, "HLT", AddressingMode.IMP, 1, false);

  private final int opcode;
  private final String name;
  private final AddressingMode addressingMode;
  private final int size;
  private final boolean acceptLabels;

  /**
   * @param opcode
   * @param size
   * @param acceptLabels
   */
  Instruction(
      int opcode, String name, AddressingMode addressingMode, int size, boolean acceptLabels) {
    this.opcode = opcode;
    this.name = name;
    this.addressingMode = addressingMode;
    this.size = size;
    this.acceptLabels = acceptLabels;
  }

  public static Instruction find(String operand, AddressingMode addressingMode) {
    for (Instruction instruction : Instruction.values()) {
      if (instruction.name.equals(operand) && instruction.addressingMode == addressingMode) {
        return instruction;
      }
    }
    throw new IllegalArgumentException(
        "Unable to find instruction '"
            + operand
            + "' with addressing mode '"
            + addressingMode.name()
            + "'");
  }

  public int getOpcode() {
    return opcode;
  }

  public int getSize() {
    return size;
  }

  public String format(int arg) {
    return name + " " + addressingMode.format(arg);
  }

  public AddressingMode getAddressingMode() {
    return addressingMode;
  }

  public Object getName() {
    return name;
  }

  public boolean acceptLabels() {
    return acceptLabels;
  }
}
