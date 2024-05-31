/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.constant.Signals;

public final class ProgramCounter extends BusConnectedModule {

  private final Register16 hlRegister;
  private int value;

  public ProgramCounter(Bus bus, ControlUnitModule controlUnit, Register16 hlRegister) {
    super(bus, controlUnit);
    this.hlRegister = hlRegister;
  }

  @Override
  public boolean clock() {
    if (controlUnit.hasControlSignal(Signals.PC_E)) {
      value = (value + 1) & 0x3FF;
    }
    if (controlUnit.hasControlSignal(Signals.PC_IN)) {
      value = readBus();
    }
    if (controlUnit.hasControlSignal(Signals.PC_IN_HL)) {
      value = hlRegister.getValue() & 0x3FF;
    }
    return false;
  }

  @Override
  public void update() {}

  @Override
  public void reset() {
    value = 0;
  }

  @Override
  public String hexString() {
    return String.format("0x%04X", value);
  }

  @Override
  public String binaryString() {
    return String.format("%16s", Integer.toBinaryString(value)).replaceAll(" ", "0");
  }

  public int getValue() {
    return value & 0x3FF;
  }
}
