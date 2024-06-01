/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.constant.Signals;

public final class ProgramCounter extends BusConnectedModule {

  private int value;

  public ProgramCounter(Bus bus, ControlUnitModule controlUnit) {
    super(bus, controlUnit);
  }

  @Override
  public boolean clock() {
    if (controlUnit.hasControlSignal(Signals.PC_E)) {
      value = (value + 1) & 0xFFFF;
    }
    if (controlUnit.hasControlSignal(Signals.PC_IN_16)) {
      value = bus.read16();
    }
    return false;
  }

  @Override
  public void update() {
    if (controlUnit.hasControlSignal(Signals.PC_OUT_16)) {
      writeBus16(value & 0xFFFF);
    }
    if (controlUnit.hasControlSignal(Signals.PC_OUT_HIGH)) {
      writeBus16((value & 0xFF00) >> 8);
    }
  }

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
    return value & 0xFFFF;
  }
}
