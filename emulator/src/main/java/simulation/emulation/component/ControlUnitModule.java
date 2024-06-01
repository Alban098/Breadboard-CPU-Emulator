/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.constant.Signals;
import simulation.emulation.execution.Instruction;
import simulation.emulation.execution.Instructions;

public final class ControlUnitModule extends Module {

  private Register8 instructionRegister;
  private StatusRegister statusRegister;
  private int microStep = 0;
  private int state;

  public void linkRegisters(Register8 instructionRegister, StatusRegister statusRegister) {
    this.instructionRegister = instructionRegister;
    this.statusRegister = statusRegister;
  }

  public boolean hasControlSignal(int signal) {
    return Signals.isActiveInState(signal, state);
  }

  @Override
  public boolean clock() {
    microStep++;
    microStep &= 0b1111;
    if (hasControlSignal(Signals.CU_RST)) {
      microStep = 0;
      return true;
    }
    return false;
  }

  @Override
  public void update() {
    Instruction instruction = Instructions.TABLE[instructionRegister.getValue() & 0b111111];
    state = instruction.getSignals(microStep & 0b1111, statusRegister.getValue() & 0b1111);
  }

  @Override
  public void reset() {
    microStep = 0;
    state = Instruction.NOP.getSignals(0, 0);
  }

  @Override
  public String hexString() {
    return String.format("0x%06X", state);
  }

  @Override
  public String binaryString() {
    return String.format("%24s", Integer.toBinaryString(state)).replaceAll(" ", "0");
  }

  public int getMicroStep() {
    return microStep;
  }
}
