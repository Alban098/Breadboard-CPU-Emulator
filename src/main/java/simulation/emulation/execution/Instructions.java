package simulation.emulation.execution;

import simulation.emulation.constant.Flag;
import simulation.emulation.constant.Signal;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public final class Instructions {

    public static final Instruction[] TABLE = new Instruction[0x40];

    static {
        Arrays.fill(TABLE, Instruction.NOP);
        Arrays.stream(Instruction.values()).forEach(opcode -> TABLE[opcode.getOpcode()] = opcode);
    }

    private Instructions() {}

    public static void dumpInstructionSet(String fileName) throws IOException {
        int sampleAddr = 0xFD;
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
        writer.append("Binary |  Hex | Length |  Clk | Assembly\n");
        writer.append("-------|------|--------|------|---------\n");

        for (int i = 0; i < TABLE.length; i++) {
            if (i == 0x40) {
                break;
            }
            Instruction instruction = TABLE[i];
            int[] minMaxClockCycles = {32, 0};
            Arrays.stream(instruction.getMicrocode()).forEach(flagState -> {
                int clockCycles = 0;
                for (int signals : flagState) {
                    clockCycles++;
                    if ((signals & Signal.CU_RST.getMask()) == Signal.CU_RST.getMask()) {
                        break;
                    }
                }
                minMaxClockCycles[0] = Math.min(minMaxClockCycles[0], clockCycles);
                minMaxClockCycles[1] = Math.max(minMaxClockCycles[1], clockCycles);
            });
            String binary = String.format("%6s", Integer.toBinaryString(i)).replaceAll(" ", "0");
            String hex = String.format("0x%02X", i);
            String length = minMaxClockCycles[0] == minMaxClockCycles[1] ? "  " + minMaxClockCycles[0] + "c" : minMaxClockCycles[0] + "-" + minMaxClockCycles[1] + "c";
            writer
                    .append(binary)
                    .append(" | ")
                    .append(hex)
                    .append(" |      ")
                    .append(String.valueOf(instruction.getSize()))
                    .append(" | ")
                    .append(length)
                    .append(" | ")
                    .append(String.format(instruction.getFormat(), sampleAddr))
                    .append("\n");
        }
        writer.close();
    }

    public static void dumpProgramROM(boolean raw, String fileName) throws IOException {
        if (!raw) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
            writer.append("OpCode|OZCN|Step|          Bin          |\n");
            writer.append("------|----|----|-----------------------|\n");
            for (int addr = 0; addr < TABLE.length * (1 << Flag.values().length) * 8; addr++) {
                int opcode = (addr & 0b111111_0000_0000) >> 8;
                int flags = (addr & 0b000000_1111_0000) >> 4;
                int microstep = addr & 0b000000_0000_1111;
                Instruction instruction = TABLE[opcode];
                int signals = instruction.getMicrocode()[flags][microstep];
                StringBuilder formattedSignals = new StringBuilder();
                for (Signal signal : Signal.values()) {
                    if ((signals & signal.getMask()) == signal.getMask()) {
                        formattedSignals.append(String.format("%9s", signal.name()));
                    }
                }
                if (signals != 0) {
                    writer
                            .append(String.format("%6s_%4s_%4s", Integer.toBinaryString(opcode), Integer.toBinaryString(flags), Integer.toBinaryString(microstep)).replaceAll(" ", "0"))
                            .append(" ")
                            .append(String.format("%23s", Integer.toBinaryString(signals)).replaceAll(" ", "0"))
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
                int opcode = (addr & 0b111111_0000_0000) >> 8;
                int flags = (addr & 0b000000_1111_0000) >> 4;
                int microstep = addr & 0b000000_0000_1111;
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
