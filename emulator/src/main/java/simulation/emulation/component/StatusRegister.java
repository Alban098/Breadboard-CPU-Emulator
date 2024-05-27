/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.constant.Flag;
import simulation.emulation.constant.Signal;

public final class StatusRegister extends AbstractRegister {

  private final ArithmeticLogicUnit alu;

  public StatusRegister(Bus bus, ControlUnitModule controlUnit, ArithmeticLogicUnit alu) {
    super(bus, controlUnit, "Status");
    this.alu = alu;
  }

  @Override
  public boolean clock() {
    if (controlUnit.hasControlSignal(Signal.SR_LATCH)) {
      value =
          (alu.probeFlag(Flag.OVERFLOW) ? Flag.OVERFLOW.getMask() : 0)
              | (alu.probeFlag(Flag.ZERO) ? Flag.ZERO.getMask() : 0)
              | (alu.probeFlag(Flag.CARRY) ? Flag.CARRY.getMask() : 0)
              | (alu.probeFlag(Flag.NEGATIVE) ? Flag.NEGATIVE.getMask() : 0);
    }
    return false;
  }

  @Override
  public void update() {
    // Nothing to do
  }

  public void setFlag(Flag flag, boolean value) {
    if (value) {
      this.value |= flag.getMask();
    } else {
      this.value &= ~flag.getMask();
    }
  }

  public boolean hasFlag(Flag flags) {
    return (this.value & flags.getMask()) == flags.getMask();
  }
}
