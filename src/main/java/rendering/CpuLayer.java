package rendering;

import emulation.Emulator;
import emulation.EmulatorState;
import emulation.Instructions;
import emulation.component.*;
import imgui.ImGui;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represent the DebugLayer in charge of displaying the current state of the CPU
 * registers, currently executing code and is in charge of handling breakpoints management
 */
public class CpuLayer extends Layer {

    private final Emulator emulator;
    private final List<Instruction> decompiled = new ArrayList<>(0x100);
    private static class Instruction {
        public int size;
        private int addr;
        private int opcode;
        private Integer arg;
        private String name;

        public Instruction() {
            clear();
        }

        public void clear() {
            addr = 0;
            size = 1;
            opcode = 0;
            arg = null;
            name = "NOP";
        }
    }

    /**
     * Create a new instance of CPULayer
     */
    public CpuLayer(Emulator emulator) {
        this.emulator = emulator;
        for (int i = 0; i < 0x100; i++) {
            decompiled.add(new Instruction());
        }
        decompile();
    }

    /**
     * Render the layer to the screen
     * and propagate button presses if needed
     */
    public void render() {
        ImGui.begin("CPU Status");
        ImGui.setWindowSize(380, 760);
        ImGui.text("Current State :");
        ImGui.sameLine();
        switch (emulator.getState()) {
            case RUN -> {
                ImGui.textColored(0, 255, 255, 255, "Running");
                ImGui.newLine();
            }
            case DEBUG -> {
                ImGui.textColored(255, 255, 0, 255, "Debug Mode");
                ImGui.newLine();
            }
        }
        ImGui.sameLine(80);
        if (emulator.getState() == EmulatorState.DEBUG) {
            if (ImGui.button("Run")) {
                emulator.setState(EmulatorState.RUN);
            }
        } else {
            if (ImGui.button("Debug")) {
                emulator.setState(EmulatorState.DEBUG);
            }
        }
        ImGui.sameLine();
        if (ImGui.button("Reset")) {
            emulator.reset();
        }
        ImGui.sameLine();
        if (ImGui.button("Clock") && emulator.getState() == EmulatorState.DEBUG) {
            emulator.clock();
            if (emulator.hasMemoryChanged()) {
                decompile();
            }
        }
        ImGui.sameLine();
        ImGui.separator();
        ImGui.separator();
        ImGui.setNextItemOpen(true);
        if (ImGui.treeNode("Flags")) {
            ImGui.newLine();
            ImGui.sameLine(145);
            ImGui.textColored(255, 0, 255, 255, "Z");
            ImGui.sameLine();
            ImGui.text("=");
            ImGui.sameLine();
            if (emulator.getModule("STAT", StatusRegisterModule.class).hasFlag(StatusRegisterModule.Flags.ZERO)) ImGui.textColored(0, 255, 0, 255, "1");
            else ImGui.textColored(255, 0, 0, 255, "0");
            ImGui.sameLine(200);
            ImGui.textColored(255, 0, 255, 255, "C");
            ImGui.sameLine();
            ImGui.text("=");
            ImGui.sameLine();
            if (emulator.getModule("STAT", StatusRegisterModule.class).hasFlag(StatusRegisterModule.Flags.CARRY)) ImGui.textColored(0, 255, 0, 255, "1");
            else ImGui.textColored(255, 0, 0, 255, "0");
            ImGui.newLine();
            ImGui.sameLine(150);
            ImGui.textColored(255, 0, 255, 255, "   N");
            ImGui.sameLine();
            ImGui.text("=");
            ImGui.sameLine();
            if (emulator.getModule("STAT", StatusRegisterModule.class).hasFlag(StatusRegisterModule.Flags.NEGATIVE)) ImGui.textColored(0, 255, 0, 255, "1");
            else ImGui.textColored(255, 0, 0, 255, "0");
            ImGui.treePop();
        }
        ImGui.separator();
        ImGui.setNextItemOpen(true);
        if (ImGui.treeNode("Registers")) {
            ImGui.textColored(0, 255, 255, 255, " A");
            ImGui.sameLine();
            ImGui.text("= " + emulator.getModule("A", RegisterModule.class).hexString());
            ImGui.sameLine(70);
            ImGui.textColored(0, 255, 255, 255, "      B");
            ImGui.sameLine();
            ImGui.text("= " + emulator.getModule("B", RegisterModule.class).hexString());
            ImGui.sameLine(150);
            ImGui.textColored(0, 255, 255, 255, "     Out");
            ImGui.sameLine();
            ImGui.text("= " + emulator.getModule("OUT", RegisterModule.class).hexString());
            ImGui.sameLine(230);
            ImGui.textColored(255, 255, 0, 255, "      PC");
            ImGui.sameLine();
            ImGui.text("= " + emulator.getModule("PC", ProgramCounterModule.class).hexString());
            ImGui.text(" " + emulator.getModule("A", RegisterModule.class).binaryString());
            ImGui.sameLine(112);
            ImGui.text(emulator.getModule("B", RegisterModule.class).binaryString());
            ImGui.sameLine(192);
            ImGui.text(emulator.getModule("OUT", RegisterModule.class).binaryString());
            ImGui.sameLine(277);
            ImGui.text(emulator.getModule("PC", ProgramCounterModule.class).binaryString());
            ImGui.newLine();
            ImGui.textColored(0, 255, 255, 255, " IR");
            ImGui.sameLine();
            ImGui.text("= " + emulator.getModule("IR", InstructionRegisterModule.class).hexString());
            ImGui.sameLine(102);
            ImGui.textColored(0, 255, 255, 255, " Bus");
            ImGui.sameLine();
            ImGui.text("= " + emulator.getBus().hexString());
            ImGui.sameLine(185);
            ImGui.textColored(0, 255, 255, 255, " MR");
            ImGui.sameLine();
            ImGui.text("= " + emulator.getModule("MAR", MemoryAddressRegisterModule.class).hexString());
            ImGui.sameLine(270);
            ImGui.textColored(0, 255, 255, 255, " RAM");
            ImGui.sameLine();
            ImGui.text("= " + emulator.getModule("RAM", MemoryModule.class).hexString());
            ImGui.text(" " + emulator.getModule("IR", InstructionRegisterModule.class).binaryString());
            ImGui.sameLine(104);
            ImGui.text(" " + emulator.getBus().binaryString());
            ImGui.sameLine(185);
            ImGui.text(" " + emulator.getModule("MAR", MemoryAddressRegisterModule.class).binaryString());
            ImGui.sameLine(270);
            ImGui.text(" " + emulator.getModule("RAM", MemoryModule.class).binaryString());
            ImGui.treePop();
        }
        ImGui.separator();
        int row = 0;
        for (ControlUnitModule.Signals signal : ControlUnitModule.Signals.values()) {
            row++;
            boolean hasSignal = emulator.getModule("CONTROL", ControlUnitModule.class).hasControlSignal(signal);
            ImGui.textColored(hasSignal ? 0 : 255, hasSignal ? 255 : 0, 0, 255, signal.name() + "    ");
            if (row < 5) {
                ImGui.sameLine();
            } else {
                row = 0;
            }
        }
        ImGui.newLine();
        ImGui.separator();
        ImGui.setNextItemOpen(true);
        if (ImGui.treeNode("Code Execution")) {
            int current = emulator.getModule("PC", ProgramCounterModule.class).getValue();
            int instructionIndex = 0;
            for (Instruction instruction : decompiled) {
                if (instruction.size == 2 && current == instruction.addr + 1) {
                    current = instruction.addr;
                    break;
                }
                if (((instruction.addr == current - 2) && instruction.size == 2) ||
                        ((instruction.addr == current - 1) && instruction.size == 1) || instruction.addr == current) {
                    break;
                }
                instructionIndex++;
            }
            int start = Math.max(instructionIndex - 2, 0);
            for (int i = start; i < start + 24; i++) {
                printInstruction(decompiled.get(i).addr == current, decompiled.get(i));
            }
            ImGui.treePop();
        }
        ImGui.end();
    }

    public void decompile() {
        decompiled.forEach(Instruction::clear);
        int addr = 0;
        int index = 0;
        while (addr < 0x100 || index < decompiled.size()) {
            addr += decompileLine(addr, decompiled.get(index++));
        }
    }

    private int decompileLine(int addr, Instruction dest) {
        int opcode = emulator.getModule("RAM", MemoryModule.class).readMemory(addr);
        Instructions.OpCodes operand = Instructions.INSTRUCTIONS[opcode];
        dest.addr = addr;
        dest.opcode = opcode;
        dest.name = operand.name();
        dest.size = operand.getSize();
        if (operand.getSize() > 1) {
            dest.arg = emulator.getModule("RAM", MemoryModule.class).readMemory(addr + 1);
        }

        return operand.getSize();
    }

    /**
     * Print an Instruction to the screen
     * @param instruction the instruction to print
     * @param current use alternate color or not
     */
    private void printInstruction(boolean current, Instruction instruction) {
        if (!current) {
            ImGui.textColored(0, 255, 255, 255, "  " + String.format("%02X", instruction.addr) + ":");
            ImGui.sameLine();
            ImGui.textColored(128, 128, 128, 255, String.format("%02X", instruction.opcode));
            if (instruction.arg != null) {
                ImGui.sameLine();
                ImGui.textColored(128, 128, 128, 255, String.format("%02X", instruction.arg));
            }
            ImGui.sameLine(150);
            ImGui.text(instruction.name);

            if (instruction.arg != null) {
                ImGui.sameLine();
                ImGui.text(String.format("%02X", instruction.arg));
            }
        } else {
            ImGui.textColored(255, 255, 0, 255, "  " + String.format("%02X", instruction.addr) + ":");
            ImGui.sameLine();
            ImGui.textColored(128, 128, 0, 255, String.format("%02X", instruction.opcode));
            if (instruction.arg != null) {
                ImGui.sameLine();
                ImGui.textColored(128, 128, 0, 255, String.format("%02X", instruction.arg));
            }
            ImGui.sameLine(150);
            ImGui.textColored(255, 255, 0, 255, instruction.name);

            if (instruction.arg != null) {
                ImGui.sameLine();
                ImGui.textColored(255, 255, 0, 255, String.format("%02X", instruction.arg));
            }
        }
    }
}
