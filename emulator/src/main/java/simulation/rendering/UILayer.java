/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.rendering;

import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import java.util.*;
import simulation.emulation.Emulator;
import simulation.emulation.component.*;
import simulation.emulation.constant.EmulatorState;
import simulation.emulation.constant.Flag;
import simulation.emulation.constant.ModuleId;
import simulation.emulation.constant.Signals;
import simulation.emulation.execution.Instruction;
import simulation.emulation.execution.Instructions;

/**
 * This class represent the DebugLayer in charge of displaying the current state of the CPU
 * registers, currently executing code and is in charge of handling breakpoints management
 */
public class UILayer {

  private static final double HIGHLIGHT_DURATION = 1.5;
  private static final int DECOMPILATION_STACK_SIZE = 14;

  private final Emulator emulator;
  private final List<DecompiledInstruction> decompiled = new ArrayList<>(DECOMPILATION_STACK_SIZE);
  private final ImString goTo = new ImString();
  private final ImBoolean gradient = new ImBoolean();
  private final int[] currentMemoryPage = new int[1];

  private int highlight = -1;
  private double highlightCooldown = 0;
  private int lastInstructionIndex = 0;

  private static class DecompiledInstruction {
    public Instruction instruction;
    private int addr;
    private int[] args;
    private int fullArg;

    public DecompiledInstruction() {
      clear();
    }

    public void clear() {
      instruction = Instruction.NOP;
      addr = 0;
      args = null;
      fullArg = 0;
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

    public String format() {
      return instruction.format(fullArg);
    }
  }

  /** Create a new instance */
  public UILayer(Emulator emulator) {
    this.emulator = emulator;
    decompile(emulator.getCurrentInstructionAddress(), false);
  }

  /** Render the layer to the screen and propagate button presses if needed */
  public void render(double frametime) {
    ImGui.begin("CPU Status");
    ImGui.setWindowSize(560, 1100);
    printUIElements();
    printMemoryStatus(frametime);
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
      ControlUnitModule controlUnit =
          emulator.getModule(ModuleId.CONTROL_UNIT, ControlUnitModule.class);

      ImGui.textColored(0, 255, 255, 255, "Result");
      ImGui.sameLine();
      ImGui.text("= " + alu.binaryString());
      ImGui.sameLine();
      ImGui.text("(" + alu.hexString() + ")");
      ImGui.sameLine();
      ImGui.textColored(255, 0, 255, 255, "    O");
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
      if (!controlUnit.hasControlSignal(Signals.SUB)) {
        ImGui.textColored(0, 255, 0, 255, "    ADD");
      } else {
        ImGui.textColored(255, 0, 0, 255, "    ADD");
      }
      ImGui.sameLine();
      if (controlUnit.hasControlSignal(Signals.SUB)) {
        ImGui.textColored(0, 255, 0, 255, "SUB");
      } else {
        ImGui.textColored(255, 0, 0, 255, "SUB");
      }
      ImGui.treePop();
    }
    ImGui.separator();
  }

  private void printMemoryStatus(double frametime) {
    ImGui.setNextItemOpen(true);
    if (ImGui.treeNode("Memory")) {
      Memory memory = emulator.getModule(ModuleId.RAM, Memory.class);
      StackPointer stackPointer = emulator.getModule(ModuleId.STACK_POINTER, StackPointer.class);
      ProgramCounter programCounter =
          emulator.getModule(ModuleId.PROGRAM_COUNTER, ProgramCounter.class);
      MemoryAddressRegister memoryAddressRegister =
          emulator.getModule(ModuleId.MEMORY_ADDRESS_REGISTER, MemoryAddressRegister.class);
      ImGui.pushItemWidth(100);
      if (ImGui.inputText(": Search Address", goTo)) {
        goTo.set(goTo.get().replaceAll("[^A-Fa-f0-9]*[ ]*", ""));
        if (goTo.get().isEmpty()) goTo.set("0");
        highlight = Integer.decode("0x" + goTo.get());
        currentMemoryPage[0] = (highlight & 0x300) >> 4;
        highlightCooldown = HIGHLIGHT_DURATION;
      }
      ImGui.sameLine(440);
      ImGui.checkbox("Gradient", gradient);
      ImGui.separator();
      ImGui.text("    ");
      for (int i = 0x0; i <= 0xF; i++) {
        ImGui.sameLine();
        ImGui.textColored(255, 255, 0, 255, String.format("%02X", i));
      }
      for (int i = 0x0; i <= 0xF; i++) {
        int addr = (currentMemoryPage[0] << 4) + (i << 4);
        ImGui.textColored(255, 255, 0, 255, String.format("%03X ", addr));
        for (int data = 0x0; data <= 0xF; data++) {
          ImGui.sameLine();
          addr = (currentMemoryPage[0] << 4) + (i << 4) | data;
          int read = memory.readMemory(addr);
          if (addr == highlight) {
            if (read == 0x00) {
              ImGui.textColored(
                  (int) (128 + 128 * highlightCooldown / HIGHLIGHT_DURATION),
                  (int) (128 - 128 * highlightCooldown / HIGHLIGHT_DURATION),
                  (int) (128 - 128 * highlightCooldown / HIGHLIGHT_DURATION),
                  255,
                  String.format("%02X", read));
            } else {
              ImGui.textColored(
                  255,
                  (int) (255 - 255 * highlightCooldown / HIGHLIGHT_DURATION),
                  (int) (255 - 255 * highlightCooldown / HIGHLIGHT_DURATION),
                  255,
                  String.format("%02X", read));
            }
            if (highlightCooldown > 0) {
              highlightCooldown -= frametime;
            }
            if (highlightCooldown <= 0) {
              highlight = -1;
            }
          } else if (addr == (memoryAddressRegister.getValue() & Memory.MAX_ADDRESS)) {
            ImGui.textColored(255, 0, 255, 255, String.format("%02X", read));
          } else if (addr == (programCounter.getValue() & Memory.MAX_ADDRESS)) {
            ImGui.textColored(207, 125, 10, 255, String.format("%02X", read));
          } else if (addr == (stackPointer.getValue() & Memory.MAX_ADDRESS)) {
            ImGui.textColored(0, 255, 0, 255, String.format("%02X", read));
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
        ImGui.text("|");
        StringBuilder dataString = new StringBuilder();
        for (int data = 0x0; data <= 0xF; data++) {
          char read = (char) memory.readMemory((currentMemoryPage[0] << 4) + (i << 4) | data);
          dataString.append(read < 0x20 ? "." : read);
        }
        ImGui.sameLine();
        ImGui.text(dataString.toString());
      }
      ImGui.pushItemWidth(450);
      ImGui.sliderInt(
          " ", currentMemoryPage, 0, 0x30, String.format("%03X0", currentMemoryPage[0]));
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
      StatusRegister statusRegister =
          emulator.getModule(ModuleId.STATUS_REGISTER, StatusRegister.class);
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
      Register8 aRegister = emulator.getModule(ModuleId.A_REGISTER, Register8.class);
      Register8 bRegister = emulator.getModule(ModuleId.B_REGISTER, Register8.class);
      Register8 outputRegister = emulator.getModule(ModuleId.OUTPUT_REGISTER, Register8.class);
      Register16 hlRegister = emulator.getModule(ModuleId.HL_REGISTER, Register16.class);
      StackPointer stackPointer = emulator.getModule(ModuleId.STACK_POINTER, StackPointer.class);
      ProgramCounter programCounter =
          emulator.getModule(ModuleId.PROGRAM_COUNTER, ProgramCounter.class);
      Register8 instructionRegister =
          emulator.getModule(ModuleId.INSTRUCTION_REGISTER, Register8.class);
      MemoryAddressRegister memoryAddressRegister =
          emulator.getModule(ModuleId.MEMORY_ADDRESS_REGISTER, MemoryAddressRegister.class);
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
      ImGui.textColored(0, 255, 255, 255, " Instr Reg");
      ImGui.sameLine();
      ImGui.text("= " + instructionRegister.hexString());
      ImGui.sameLine();
      ImGui.textColored(0, 255, 255, 255, " HL Register");
      ImGui.sameLine();
      ImGui.text("= " + hlRegister.hexString());

      ImGui.text(aRegister.binaryString());
      ImGui.sameLine();
      ImGui.text(" " + bRegister.binaryString());
      ImGui.sameLine();
      ImGui.text("    " + outputRegister.binaryString());
      ImGui.sameLine();
      ImGui.text("        " + instructionRegister.binaryString());
      ImGui.sameLine();
      ImGui.text("        " + hlRegister.binaryString());
      ImGui.newLine();

      ImGui.textColored(255, 0, 255, 255, "Memory Address");
      ImGui.sameLine();
      ImGui.text("= " + memoryAddressRegister.hexString());
      ImGui.sameLine();
      ImGui.textColored(0, 255, 0, 255, "    Stack Pointer");
      ImGui.sameLine();
      ImGui.text("= " + stackPointer.hexString());
      ImGui.sameLine();
      ImGui.textColored(207, 125, 10, 255, "   Prg. Cnt");
      ImGui.sameLine();
      ImGui.text("= " + programCounter.hexString());

      ImGui.text("   " + memoryAddressRegister.binaryString());
      ImGui.sameLine();
      ImGui.text("           " + stackPointer.binaryString());
      ImGui.sameLine();
      ImGui.text("       " + programCounter.binaryString());
      ImGui.newLine();

      ImGui.textColored(255, 255, 0, 255, "                Bus");
      ImGui.sameLine();
      ImGui.text("= " + emulator.getBus().hexString());
      ImGui.sameLine();
      ImGui.textColored(255, 0, 255, 255, "        RAM Output");
      ImGui.sameLine();
      ImGui.text("= " + ram.hexString());

      ImGui.text("              " + emulator.getBus().binaryString());
      ImGui.sameLine();
      ImGui.text("           " + ram.binaryString());

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
    if (ImGui.treeNode("Control Unit")) {
      ControlUnitModule controlUnit =
          emulator.getModule(ModuleId.CONTROL_UNIT, ControlUnitModule.class);
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
      for (Map.Entry<Integer, String> signal : Signals.values().entrySet()) {
        col++;
        boolean hasSignal = controlUnit.hasControlSignal(signal.getKey());
        ImGui.textColored(
            hasSignal ? 0 : 255, hasSignal ? 255 : 0, 0, 255, signal.getValue() + "    ");
        if (col < 4) {
          ImGui.sameLine(150 * col);
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
    DecompiledInstruction lastInstruction =
        decompiled.size() > lastInstructionIndex && last
            ? decompiled.get(lastInstructionIndex)
            : null;
    decompiled.clear();
    if (lastInstruction != null) {
      decompiled.add(lastInstruction);
      lastInstructionIndex = 1;
    }
    int index = 0;
    while (index < DECOMPILATION_STACK_SIZE - (lastInstruction != null ? 1 : 0)) {
      DecompiledInstruction decompileLine = decompileLine(addr & 0x3FF);
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
      dest.args = new int[instruction.getSize() - 1];
      for (int i = 1; i < instruction.getSize(); i++) {
        dest.args[i - 1] = ram.readMemory(addr + i);
        dest.fullArg <<= 8;
        dest.fullArg |= dest.args[i - 1];
      }
    }

    return dest;
  }

  /**
   * Print an Instruction to the screen
   *
   * @param instruction the instruction to print
   * @param current use alternate color or not
   */
  private void printInstruction(boolean current, DecompiledInstruction instruction) {
    ImGui.newLine();
    ImGui.sameLine(180);
    if (!current) {
      ImGui.textColored(0, 255, 255, 255, "  " + String.format("%03X", instruction.addr) + ":");
      ImGui.sameLine();
      ImGui.textColored(128, 128, 128, 255, String.format("%02X", instruction.getOpcode()));
      if (instruction.args != null) {
        for (int value : instruction.args) {
          ImGui.sameLine();
          ImGui.textColored(128, 128, 128, 255, String.format("%02X", value));
        }
      }
      ImGui.sameLine(295);
      ImGui.text(instruction.format());
    } else {
      ImGui.textColored(255, 255, 0, 255, "  " + String.format("%03X", instruction.addr) + ":");
      ImGui.sameLine();
      ImGui.textColored(128, 128, 0, 255, String.format("%02X", instruction.getOpcode()));
      if (instruction.args != null) {
        for (int value : instruction.args) {
          ImGui.sameLine();
          ImGui.textColored(128, 128, 0, 255, String.format("%02X", value));
        }
      }
      ImGui.sameLine(295);
      ImGui.textColored(255, 255, 0, 255, instruction.format());
    }
  }
}
