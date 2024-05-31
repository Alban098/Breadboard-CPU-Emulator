/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.constant.Signals;

public final class MemoryAddressRegister extends Register16 {

  private final Register16 hlRegister;
  private final ProgramCounter programCounter;
  private final StackPointer stackPointer;

  public MemoryAddressRegister(
      Bus bus,
      ControlUnitModule controlUnit,
      Register16 hlRegister,
      ProgramCounter programCounter,
      StackPointer stackPointer) {
    super(bus, controlUnit);
    this.hlRegister = hlRegister;
    this.programCounter = programCounter;
    this.stackPointer = stackPointer;
  }

  @Override
  public boolean clock() {
    if (controlUnit.hasControlSignal(Signals.MAR_IN)) {
      value = readBus();
    }
    if (controlUnit.hasControlSignal(Signals.MAR_IN_HL)) {
      value = hlRegister.getValue();
    }
    if (controlUnit.hasControlSignal(Signals.MAR_IN_PC)) {
      value = programCounter.getValue();
    }
    if (controlUnit.hasControlSignal(Signals.MAR_IN_STACK)) {
      value = stackPointer.getValue();
    }
    return false;
  }

  @Override
  public void update() {
    // NO OP
  }

  @Override
  public String hexString() {
    return String.format("0x%04X", value);
  }

  @Override
  public String binaryString() {
    return String.format("%16s", Integer.toBinaryString(value)).replaceAll(" ", "0");
  }
}
