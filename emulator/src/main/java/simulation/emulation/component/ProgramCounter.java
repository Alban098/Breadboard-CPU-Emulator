/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.constant.Signal;

public final class ProgramCounter extends BusConnectedModule {

  private int value;

  public ProgramCounter(Bus bus, ControlUnitModule controlUnit) {
    super(bus, controlUnit);
  }

  @Override
  public boolean clock() {
    if (controlUnit.hasControlSignal(Signal.C_E)) {
      value = (value + 1) & 0xFF;
    }
    if (controlUnit.hasControlSignal(Signal.C_IN)) {
      value = readBus();
    }
    return false;
  }

  @Override
  public void update() {
    if (controlUnit.hasControlSignal(Signal.C_OUT)) {
      writeBus(value);
    }
  }

  @Override
  public void reset() {
    value = 0;
  }

  @Override
  public String hexString() {
    return String.format("0x%02X", value);
  }

  @Override
  public String binaryString() {
    return String.format("%8s", Integer.toBinaryString(value)).replaceAll(" ", "0");
  }

  public int getValue() {
    return value & 0xFF;
  }
}
