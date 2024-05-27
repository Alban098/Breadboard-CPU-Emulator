/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.constant.Signal;

public final class InstructionRegister extends AbstractRegister {

  public InstructionRegister(Bus bus, ControlUnitModule controlUnit, String name) {
    super(bus, controlUnit, name);
  }

  @Override
  public boolean clock() {
    if (controlUnit.hasControlSignal(Signal.IR_IN)) {
      value = readBus();
    }
    return false;
  }

  @Override
  public void update() {
    // NO OP
  }
}
