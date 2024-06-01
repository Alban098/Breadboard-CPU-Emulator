/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.constant.Flag;
import simulation.emulation.constant.Signals;

public final class StatusRegister extends Register8 {

  private final ArithmeticLogicUnit alu;

  public StatusRegister(Bus bus, ControlUnitModule controlUnit, ArithmeticLogicUnit alu) {
    super(bus, controlUnit, Signals.STATUS_IN, Signals.STATUS_OUT);
    this.alu = alu;
  }

  @Override
  public boolean clock() {
    super.clock();
    if (controlUnit.hasControlSignal(Signals.ALU_OUT)) {
      value =
          (alu.probeFlag(Flag.OVERFLOW) ? Flag.OVERFLOW.getMask() : 0)
              | (alu.probeFlag(Flag.ZERO) ? Flag.ZERO.getMask() : 0)
              | (alu.probeFlag(Flag.CARRY) ? Flag.CARRY.getMask() : 0)
              | (alu.probeFlag(Flag.NEGATIVE) ? Flag.NEGATIVE.getMask() : 0);
    }
    return false;
  }

  public boolean hasFlag(Flag flags) {
    return (this.value & flags.getMask()) == flags.getMask();
  }
}
