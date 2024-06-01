/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.constant.Signals;

public final class MemoryAddressRegister extends Register16 {

  public MemoryAddressRegister(Bus bus, ControlUnitModule controlUnit) {
    super(bus, controlUnit);
  }

  @Override
  public boolean clock() {
    if (controlUnit.hasControlSignal(Signals.MAR_IN_16)) {
      value = bus.read16();
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
