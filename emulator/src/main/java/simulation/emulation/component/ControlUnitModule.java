/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.constant.Signal;
import simulation.emulation.execution.Instruction;
import simulation.emulation.execution.Instructions;

public final class ControlUnitModule extends Module {

  private InstructionRegister instructionRegister;
  private StatusRegister statusRegister;
  private int microStep = 0;
  private int state;

  public void linkRegisters(
      InstructionRegister instructionRegister, StatusRegister statusRegister) {
    this.instructionRegister = instructionRegister;
    this.statusRegister = statusRegister;
  }

  public boolean hasControlSignal(Signal signal) {
    return signal.isActiveInState(state);
  }

  @Override
  public boolean clock() {
    microStep++;
    if (hasControlSignal(Signal.CU_RST)) {
      microStep = 0;
      return true;
    }
    return false;
  }

  @Override
  public void update() {
    Instruction instruction = Instructions.TABLE[instructionRegister.getValue() & 0b111111];
    state = instruction.getSignals(microStep & 0b1111, statusRegister.getValue() & 0b111);
  }

  @Override
  public void reset() {
    microStep = 0;
    state = Instruction.NOP.getSignals(0, 0);
  }

  @Override
  public String hexString() {
    return String.format("0x%04X", state);
  }

  @Override
  public String binaryString() {
    return String.format("%16s", Integer.toBinaryString(state)).replaceAll(" ", "0");
  }

  public int getMicroStep() {
    return microStep;
  }
}
