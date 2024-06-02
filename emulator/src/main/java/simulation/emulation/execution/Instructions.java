/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.execution;

import static simulation.emulation.constant.Signals.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import simulation.emulation.constant.Flag;
import simulation.emulation.constant.Signals;

public final class Instructions {

  public static final Instruction[] TABLE = new Instruction[0x40];

  public static final int[] JMP_ZP_MICROCODE =
      new int[] {
        MAR_IN_16 | PC_OUT_16,
        RAM_OUT | IR_IN,
        PC_E,
        MAR_IN_16 | PC_OUT_16,
        RAM_OUT | HL_IN_LOW,
        HL_IN_HIGH,
        PC_IN_16 | HL_OUT_16 | CU_RST
      };

  public static final int[] JMP_ABS_MICROCODE =
      new int[] {
        MAR_IN_16 | PC_OUT_16,
        RAM_OUT | IR_IN,
        PC_E,
        MAR_IN_16 | PC_OUT_16,
        RAM_OUT | HL_IN_HIGH | PC_E,
        MAR_IN_16 | PC_OUT_16,
        RAM_OUT | HL_IN_LOW,
        PC_IN_16 | HL_OUT_16 | CU_RST
      };

  public static final int[] JMP_IDX_MICROCODE =
      new int[] {
        MAR_IN_16 | PC_OUT_16,
        RAM_OUT | IR_IN,
        PC_E,
        MAR_IN_16 | PC_OUT_16,
        RAM_OUT | HL_IN_HIGH,
        A_OUT | HL_IN_LOW,
        PC_IN_16 | HL_OUT_16 | CU_RST
      };

  static {
    Arrays.fill(TABLE, Instruction.NOP);
    Arrays.stream(Instruction.values()).forEach(opcode -> TABLE[opcode.getOpcode()] = opcode);
  }

  private Instructions() {}

  public static void dumpInstructionSet(String fileName) throws IOException {
    int sampleAddr = 0xFD;
    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
    writer.append("|    Hex|  Assembly| Addressing Mode|   Size| Clock cycles|\n");
    writer.append("|:-----:|:---------|---------------:|------:|------------:|\n");

    for (int i = 0; i < TABLE.length; i++) {
      Instruction instruction = TABLE[i];
      int[] minMaxClockCycles = {32, 0};
      Arrays.stream(instruction.getMicrocode())
          .forEach(
              flagState -> {
                int clockCycles = 0;
                for (int signals : flagState) {
                  clockCycles++;

                  if (Signals.isActiveInState(Signals.CU_RST, signals)) {
                    break;
                  }
                }
                minMaxClockCycles[0] = Math.min(minMaxClockCycles[0], clockCycles);
                minMaxClockCycles[1] = Math.max(minMaxClockCycles[1], clockCycles);
              });
      String hex = String.format("0x%02X", i);
      String length =
          minMaxClockCycles[0] == minMaxClockCycles[1]
              ? "  " + minMaxClockCycles[0] + "c"
              : minMaxClockCycles[0] + "-" + minMaxClockCycles[1] + "c";
      writer
          .append("| ")
          .append(hex)
          .append(" | ")
          .append(instruction.format(sampleAddr))
          .append(" | ")
          .append(String.valueOf(instruction.getAddressingMode()))
          .append(" | ")
          .append(String.valueOf(instruction.getSize()))
          .append(" | ")
          .append(length)
          .append(" |")
          .append("\n");
    }
    writer.close();
  }

  public static void dumpProgramROM(boolean raw, String fileName) throws IOException {
    if (!raw) {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
      writer.append("Step|OZCN|OpCode|          Bin           |\n");
      writer.append("----|----|------|------------------------|\n");
      for (int addr = 0; addr < TABLE.length * (1 << Flag.values().length) * 16; addr++) {
        int opcode = addr & 0b0000_0000_111111;
        int flags = (addr & 0b0000_1111_000000) >> 6;
        int microstep = (addr & 0b1111_0000_000000) >> 10;
        Instruction instruction = TABLE[opcode];
        int signals = instruction.getMicrocode()[flags][microstep];
        StringBuilder formattedSignals = new StringBuilder();
        for (Map.Entry<Integer, String> signal : Signals.values().entrySet()) {
          int mask = signal.getKey();
          if ((signals & mask) == mask) {
            formattedSignals.append(String.format("%11s", signal.getValue()));
          }
        }
        if (signals != 0) {
          writer
              .append(
                  String.format(
                          "%4s_%4s_%6s",
                          Integer.toBinaryString(microstep),
                          Integer.toBinaryString(flags),
                          Integer.toBinaryString(opcode))
                      .replaceAll(" ", "0"))
              .append(" ")
              .append(String.format("%24s", Integer.toBinaryString(signals)).replaceAll(" ", "0"))
              .append(" ")
              .append(String.valueOf(formattedSignals))
              .append("\n");
        }
      }
      writer.close();
    } else {
      FileOutputStream dump_LB = new FileOutputStream(fileName + "0");
      FileOutputStream dump_MB = new FileOutputStream(fileName + "1");
      FileOutputStream dump_HB = new FileOutputStream(fileName + "2");
      byte[] lbs = new byte[0b1000000_0000_0000];
      byte[] mbs = new byte[0b1000000_0000_0000];
      byte[] hbs = new byte[0b1000000_0000_0000];
      for (int addr = 0; addr < TABLE.length * (1 << (Flag.values().length - 1)) * 8; addr++) {
        int opcode = addr & 0b0000_0000_111111;
        int flags = (addr & 0b0000_1111_000000) >> 6;
        int microstep = (addr & 0b1111_0000_000000) >> 10;
        Instruction instruction = TABLE[opcode];
        int signals = instruction.getMicrocode()[flags][microstep];
        lbs[addr] = (byte) (signals & 0x0000FF);
        mbs[addr] = (byte) ((signals & 0x00FF00) >> 8);
        hbs[addr] = (byte) ((signals & 0xFF0000) >> 16);
      }
      dump_LB.write(lbs);
      dump_MB.write(mbs);
      dump_HB.write(hbs);
      dump_LB.close();
      dump_MB.close();
      dump_HB.close();
    }
  }
}
