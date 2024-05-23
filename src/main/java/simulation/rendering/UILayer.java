package simulation.rendering;

import simulation.emulation.Emulator;
import simulation.emulation.component.*;
import simulation.emulation.constant.EmulatorState;
import simulation.emulation.constant.ModuleId;
import simulation.emulation.constant.Signal;
import simulation.emulation.execution.Instruction;
import simulation.emulation.execution.Instructions;
import simulation.emulation.constant.Flag;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;

import java.util.*;


/**
 * This class represent the DebugLayer in charge of displaying the current state of the CPU
 * registers, currently executing code and is in charge of handling breakpoints management
 */
public class UILayer {

    private static final int HIGHLIGHT_DURATION = 64;
    private static final int DECOMPILATION_STACK_SIZE = 14;

    private final Emulator emulator;
    private final List<DecompiledInstruction> decompiled = new ArrayList<>(DECOMPILATION_STACK_SIZE);
    private final ImString goTo = new ImString();
    private final ImBoolean gradient = new ImBoolean();
    private int highlight = -1;
    private int highlightCooldown = 0;
    private int lastInstructionIndex = 0;

    private static class DecompiledInstruction {
        public Instruction instruction;
        private int addr;
        private Integer arg;

        public DecompiledInstruction() {
            clear();
        }

        public void clear() {
            instruction = Instruction.NOP;
            addr = 0;
            arg = null;
        }

        public int getSize() {
            return instruction.getSize();
        }

        public String getName() {
            return instruction.name();
        }

        public int getOpcode() {
            return instruction.getOpcode();
        }

        public String getFormat() {
            return instruction.getFormat();
        }
    }

    /**
     * Create a new instance
     */
    public UILayer(Emulator emulator) {
        this.emulator = emulator;
        decompile(emulator.getCurrentInstructionAddress(), false);
    }

    /**
     * Render the layer to the screen
     * and propagate button presses if needed
     */
    public void render() {
        ImGui.begin("CPU Status");
        ImGui.setWindowSize(560, 955);
        printUIElements();
        printMemoryStatus();
        printStatusRegister();
        printALUStatus();
        printRegisters();
        printControlUnitStatus();
        printCodeExecutionContext();
        ImGui.end();
    }

    private void printALUStatus() {
        ImGui.setNextItemOpen(true);
        if (ImGui.treeNode("ALU")) {
            ArithmeticLogicUnit alu = emulator.getModule(ModuleId.ALU, ArithmeticLogicUnit.class);
            ControlUnitModule controlUnit = emulator.getModule(ModuleId.CONTROL_UNIT, ControlUnitModule.class);

            ImGui.textColored(0, 255, 255, 255, "Result");
            ImGui.sameLine();
            ImGui.text("= " + alu.binaryString());
            ImGui.sameLine();
            ImGui.text("(" + alu.hexString() + ")");
            ImGui.sameLine();
            ImGui.textColored(255, 0, 255, 255, " O");
            ImGui.sameLine();
            ImGui.text("=");
            ImGui.sameLine();
            if (alu.probeFlag(Flag.OVERFLOW)) {
                ImGui.textColored(0, 255, 0, 255, "1");
            } else {
                ImGui.textColored(255, 0, 0, 255, "0");
            }
            ImGui.sameLine();
            ImGui.textColored(255, 0, 255, 255, " Z");
            ImGui.sameLine();
            ImGui.text("=");
            ImGui.sameLine();
            if (alu.probeFlag(Flag.ZERO)) {
                ImGui.textColored(0, 255, 0, 255, "1");
            } else {
                ImGui.textColored(255, 0, 0, 255, "0");
            }
            ImGui.sameLine();
            ImGui.textColored(255, 0, 255, 255, " C");
            ImGui.sameLine();
            ImGui.text("=");
            ImGui.sameLine();
            if (alu.probeFlag(Flag.CARRY)) {
                ImGui.textColored(0, 255, 0, 255, "1");
            } else {
                ImGui.textColored(255, 0, 0, 255, "0");
            }
            ImGui.sameLine();
            ImGui.textColored(255, 0, 255, 255, " N");
            ImGui.sameLine();
            ImGui.text("=");
            ImGui.sameLine();
            if (alu.probeFlag(Flag.NEGATIVE)) {
                ImGui.textColored(0, 255, 0, 255, "1");
            } else {
                ImGui.textColored(255, 0, 0, 255, "0");
            }

            ImGui.sameLine();
            if (!controlUnit.hasControlSignal(Signal.SUB) &&
                    !controlUnit.hasControlSignal(Signal.AND) &&
                    !controlUnit.hasControlSignal(Signal.XOR) &&
                    !controlUnit.hasControlSignal(Signal.OR)) {
                ImGui.textColored(0, 255, 0, 255, " ADD");
            } else {
                ImGui.textColored(255, 0, 0, 255, " ADD");
            }
            ImGui.sameLine();
            if (controlUnit.hasControlSignal(Signal.SUB)) {
                ImGui.textColored(0, 255, 0, 255, "SUB");
            } else {
                ImGui.textColored(255, 0, 0, 255, "SUB");
            }
            ImGui.sameLine();
            if (controlUnit.hasControlSignal(Signal.AND)) {
                ImGui.textColored(0, 255, 0, 255, "AND");
            } else {
                ImGui.textColored(255, 0, 0, 255, "AND");
            }
            ImGui.sameLine();
            if (controlUnit.hasControlSignal(Signal.OR)) {
                ImGui.textColored(0, 255, 0, 255, "OR");
            } else {
                ImGui.textColored(255, 0, 0, 255, "OR");
            }
            ImGui.sameLine();
            if (controlUnit.hasControlSignal(Signal.XOR)) {
                ImGui.textColored(0, 255, 0, 255, "XOR");
            } else {
                ImGui.textColored(255, 0, 0, 255, "XOR");
            }
            ImGui.treePop();
        }
        ImGui.separator();
    }

    private void printMemoryStatus() {
        ImGui.setNextItemOpen(true);
        if (ImGui.treeNode("Memory")) {
            Memory memory = emulator.getModule(ModuleId.RAM, Memory.class);
            MemoryAddressRegister memoryAddressRegister = emulator.getModule(ModuleId.MEMORY_ADDRESS_REGISTER, MemoryAddressRegister.class);
            ImGui.pushItemWidth(100);
            if (ImGui.inputText(": Search Address", goTo)) {
                goTo.set(goTo.get().replaceAll("[^A-Fa-f0-9]*[ ]*", ""));
                if (goTo.get().isEmpty())
                    goTo.set("0");
                highlight = Integer.decode("0x" + goTo.get());
                highlightCooldown = HIGHLIGHT_DURATION;
            }
            ImGui.sameLine(440);
            ImGui.checkbox("Gradient", gradient);
            ImGui.separator();
            ImGui.text("   ");
            for (int i = 0x0; i <= 0xF; i++) {
                ImGui.sameLine();
                ImGui.textColored(255, 255, 0, 255, String.format("%02X", i));
            }
            for (int i = 0x0; i <= 0xF; i++) {
                int addr = i << 4;
                ImGui.textColored(255, 255, 0, 255, String.format("%02X ", addr));
                for (int data = 0x0; data <= 0xF; data++) {
                    ImGui.sameLine();
                    addr = (i << 4) | data;
                    int read = memory.readMemory(addr);
                    if (addr == highlight) {
                        if (read == 0x00) {
                            ImGui.textColored(128 + 128 * highlightCooldown / HIGHLIGHT_DURATION, 128 - 128 * highlightCooldown / HIGHLIGHT_DURATION, 128 - 128 * highlightCooldown / HIGHLIGHT_DURATION, 255, String.format("%02X", read));
                        } else {
                            ImGui.textColored(255, 255 - 255 * highlightCooldown / HIGHLIGHT_DURATION, 255 - 255 * highlightCooldown / HIGHLIGHT_DURATION, 255, String.format("%02X", read));
                        }
                        if (highlightCooldown-- == 0) {
                            highlight = -1;
                        }
                    } else if (addr == memoryAddressRegister.getValue()) {
                        ImGui.textColored(0, 192, 255, 255, String.format("%02X", read));
                    } else if (!gradient.get()) {
                        if (read == 0x00) {
                            ImGui.textColored(128, 128, 128, 255, String.format("%02X", read));
                        } else {
                            ImGui.text(String.format("%02X", read));
                        }
                    } else {
                        ImGui.textColored(read, read, read, 255, String.format("%02X", read));
                    }
                }
                ImGui.sameLine();
                ImGui.text(" | ");
                StringBuilder dataString = new StringBuilder();
                for (int data = 0x0; data <= 0xF; data++) {
                    char read = (char) memory.readMemory((i << 4) | data);
                    dataString.append(read < 0x20 ? "." : read);
                }
                ImGui.sameLine();
                ImGui.text(dataString.toString());
            }
            ImGui.treePop();
        }
        ImGui.separator();
    }

    private void printUIElements() {
        ImGui.text("Current State :");
        ImGui.sameLine();
        switch (emulator.getState()) {
            case RUN -> ImGui.textColored(0, 255, 255, 255, "Running");
            case DEBUG -> ImGui.textColored(255, 255, 0, 255, "Debug Mode");
        }

        ImGui.sameLine(360);
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
            lastInstructionIndex = 0;
            decompile(emulator.getCurrentInstructionAddress(), false);
        }
        ImGui.sameLine();
        if (ImGui.button("Clock") && emulator.getState() == EmulatorState.DEBUG) {
            if (emulator.clock()) {
                decompile(emulator.getCurrentInstructionAddress(), true);
            }
        }
        ImGui.separator();
    }

    private void printStatusRegister() {
        ImGui.setNextItemOpen(true);
        if (ImGui.treeNode("Status Register")) {
            StatusRegister statusRegister = emulator.getModule(ModuleId.STATUS_REGISTER, StatusRegister.class);
            ImGui.textColored(255, 0, 255, 255, "O");
            ImGui.sameLine();
            ImGui.text("=");
            ImGui.sameLine();
            if (statusRegister.hasFlag(Flag.OVERFLOW)) ImGui.textColored(0, 255, 0, 255, "1");
            else ImGui.textColored(255, 0, 0, 255, "0");
            ImGui.sameLine();
            ImGui.textColored(255, 0, 255, 255, "  Z");
            ImGui.sameLine();
            ImGui.text("=");
            ImGui.sameLine();
            if (statusRegister.hasFlag(Flag.ZERO)) ImGui.textColored(0, 255, 0, 255, "1");
            else ImGui.textColored(255, 0, 0, 255, "0");
            ImGui.sameLine();
            ImGui.textColored(255, 0, 255, 255, "  C");
            ImGui.sameLine();
            ImGui.text("=");
            ImGui.sameLine();
            if (statusRegister.hasFlag(Flag.CARRY)) ImGui.textColored(0, 255, 0, 255, "1");
            else ImGui.textColored(255, 0, 0, 255, "0");
            ImGui.sameLine();
            ImGui.textColored(255, 0, 255, 255, "  N");
            ImGui.sameLine();
            ImGui.text("=");
            ImGui.sameLine();
            if (statusRegister.hasFlag(Flag.NEGATIVE)) ImGui.textColored(0, 255, 0, 255, "1");
            else ImGui.textColored(255, 0, 0, 255, "0");
            ImGui.treePop();
        }
        ImGui.separator();
    }

    private void printRegisters() {
        ImGui.setNextItemOpen(true);
        if (ImGui.treeNode("Registers")) {
            AbstractRegister aRegister = emulator.getModule(ModuleId.A_REGISTER, AbstractRegister.class);
            AbstractRegister bRegister = emulator.getModule(ModuleId.B_REGISTER, AbstractRegister.class);
            AbstractRegister outputRegister = emulator.getModule(ModuleId.OUTPUT_REGISTER, AbstractRegister.class);
            ProgramCounter programCounter = emulator.getModule(ModuleId.PROGRAM_COUNTER, ProgramCounter.class);
            InstructionRegister instructionRegister = emulator.getModule(ModuleId.INSTRUCTION_REGISTER, InstructionRegister.class);
            MemoryAddressRegister memoryAddressRegister = emulator.getModule(ModuleId.MEMORY_ADDRESS_REGISTER, MemoryAddressRegister.class);
            Memory ram = emulator.getModule(ModuleId.RAM, Memory.class);

            ImGui.textColored(0, 255, 255, 255, "A");
            ImGui.sameLine();
            ImGui.text("= " + aRegister.hexString());
            ImGui.sameLine();
            ImGui.textColored(0, 255, 255, 255, " B");
            ImGui.sameLine();
            ImGui.text("= " + bRegister.hexString());
            ImGui.sameLine();
            ImGui.textColored(0, 255, 255, 255, " Out Reg");
            ImGui.sameLine();
            ImGui.text("= " + outputRegister.hexString());
            ImGui.sameLine();
            ImGui.textColored(255, 255, 0, 255, " Prg. Cnt");
            ImGui.sameLine();
            ImGui.text("= " + programCounter.hexString());
            ImGui.sameLine();
            ImGui.textColored(0, 255, 255, 255, " Instr. Reg");
            ImGui.sameLine();
            ImGui.text("= " + instructionRegister.hexString());

            ImGui.text(aRegister.binaryString());
            ImGui.sameLine();
            ImGui.text(" " + bRegister.binaryString());
            ImGui.sameLine();
            ImGui.text("    " + outputRegister.binaryString());
            ImGui.sameLine();
            ImGui.text("        " + programCounter.binaryString());
            ImGui.sameLine();
            ImGui.text("         " + instructionRegister.binaryString());
            ImGui.newLine();

            ImGui.textColored(0, 255, 255, 255, "Bus");
            ImGui.sameLine();
            ImGui.text("= " + emulator.getBus().hexString());
            ImGui.sameLine();
            ImGui.textColored(0, 255, 255, 255, "     Mem. Addr. Register");
            ImGui.sameLine();
            ImGui.text("= " + memoryAddressRegister.hexString());
            ImGui.sameLine();
            ImGui.textColored(0, 255, 255, 255, "     RAM Slot");
            ImGui.sameLine();
            ImGui.text("= " + ram.hexString());

            ImGui.text(" " + emulator.getBus().binaryString());
            ImGui.sameLine();
            ImGui.text("              " + memoryAddressRegister.binaryString());
            ImGui.sameLine();
            ImGui.text("                     " + ram.binaryString());
            ImGui.treePop();
        }
        ImGui.separator();
    }

    private void printCodeExecutionContext() {
        ImGui.setNextItemOpen(true);
        if (ImGui.treeNode("Code Execution")) {
            for (DecompiledInstruction instruction : decompiled) {
                printInstruction(instruction.addr == emulator.getCurrentInstructionAddress(), instruction);
            }
            ImGui.treePop();
        }
    }

    private void printControlUnitStatus() {
        ImGui.setNextItemOpen(true);
        if (ImGui.treeNode("ControlUnit")) {
            ControlUnitModule controlUnit = emulator.getModule(ModuleId.CONTROL_UNIT, ControlUnitModule.class);
            ImGui.textColored(0, 255, 255, 255, "State");
            ImGui.sameLine();
            ImGui.text("= " + controlUnit.binaryString());
            ImGui.sameLine();
            ImGui.text("(" + controlUnit.hexString() + ")");
            ImGui.sameLine();
            ImGui.textColored(0, 255, 255, 255, "    Micro-step");
            ImGui.sameLine();
            ImGui.text("= " + controlUnit.getMicroStep());
            ImGui.newLine();
            int col = 0;
            for (Signal signal : Signal.values()) {
                col++;
                boolean hasSignal = controlUnit.hasControlSignal(signal);
                ImGui.textColored(hasSignal ? 0 : 255, hasSignal ? 255 : 0, 0, 255, signal.name() + "    ");
                if (col < 6) {
                    ImGui.sameLine(95 * col);
                } else {
                    col = 0;
                }
            }
            ImGui.newLine();
            ImGui.treePop();
        }
        ImGui.separator();
    }

    public void decompile(int addr, boolean last) {
        DecompiledInstruction lastInstruction = decompiled.size() > lastInstructionIndex && last ? decompiled.get(lastInstructionIndex) : null;
        decompiled.clear();
        if (lastInstruction != null) {
            decompiled.add(lastInstruction);
            lastInstructionIndex = 1;
        }
        int index = 0;
        while (index < DECOMPILATION_STACK_SIZE - (lastInstruction != null ? 1 : 0)) {
            DecompiledInstruction decompileLine = decompileLine(addr & 0xFF);
            decompiled.add(decompileLine);
            addr += decompileLine.getSize();
            index++;
        }
    }

    private DecompiledInstruction decompileLine(int addr) {
        DecompiledInstruction dest = new DecompiledInstruction();
        Memory ram = emulator.getModule(ModuleId.RAM, Memory.class);
        int opcode = ram.readMemory(addr);
        Instruction instruction = Instructions.TABLE[opcode & 0x3F];
        dest.addr = addr;
        dest.instruction = instruction;
        if (instruction.getSize() > 1) {
            dest.arg = ram.readMemory(addr + 1);
        }

        return dest;
    }

    /**
     * Print an Instruction to the screen
     * @param instruction the instruction to print
     * @param current use alternate color or not
     */
    private void printInstruction(boolean current, DecompiledInstruction instruction) {
        ImGui.newLine();
        ImGui.sameLine(180);
        if (!current) {
            ImGui.textColored(0, 255, 255, 255, "  " + String.format("%02X", instruction.addr) + ":");
            ImGui.sameLine();
            ImGui.textColored(128, 128, 128, 255, String.format("%02X", instruction.getOpcode()));
            if (instruction.arg != null) {
                ImGui.sameLine();
                ImGui.textColored(128, 128, 128, 255, String.format("%02X", instruction.arg));
            }
            ImGui.sameLine(280);
            ImGui.text(String.format(instruction.getFormat(), instruction.arg));
        } else {
            ImGui.textColored(255, 255, 0, 255, "  " + String.format("%02X", instruction.addr) + ":");
            ImGui.sameLine();
            ImGui.textColored(128, 128, 0, 255, String.format("%02X", instruction.getOpcode()));
            if (instruction.arg != null) {
                ImGui.sameLine();
                ImGui.textColored(128, 128, 0, 255, String.format("%02X", instruction.arg));
            }
            ImGui.sameLine(280);
            ImGui.textColored(255, 255, 0, 255, String.format(instruction.getFormat(), instruction.arg));

        }
    }
}
