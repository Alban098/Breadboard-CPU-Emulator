/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.constant.Signal;

public final class BRegister extends AbstractRegister {

  public BRegister(Bus bus, ControlUnitModule controlUnit, String name) {
    super(bus, controlUnit, name);
  }

  @Override
  public boolean clock() {
    if (controlUnit.hasControlSignal(Signal.B_IN)) {
      value = readBus();
    }
    return false;
  }

  @Override
  public void update() {
    // Nothing to do
  }
}
