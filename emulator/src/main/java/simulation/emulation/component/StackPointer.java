/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.Emulator;
import simulation.emulation.constant.Signals;

public class StackPointer extends Register8 {

  private final Emulator emulator;

  public StackPointer(Bus bus, ControlUnitModule controlUnit, Emulator emulator) {
    super(bus, controlUnit);
    this.emulator = emulator;
    reset();
  }

  @Override
  public boolean clock() {
    if (controlUnit.hasControlSignal(Signals.STACK_POP)) {
      value = (value + 1) & 0xFF;
      if (value == 0) {
        emulator.halt();
      }
    }
    if (controlUnit.hasControlSignal(Signals.STACK_PUSH)) {
      value = (value - 1) & 0xFF;
      if (value == 0xFF) {
        emulator.halt();
      }
    }
    return false;
  }

  @Override
  public void update() {
    if (controlUnit.hasControlSignal(Signals.STACK_OUT_16)) {
      writeBus16(0xFF00 | value);
    }
  }

  @Override
  public String hexString() {
    return String.format("0x%04X", getValue());
  }

  @Override
  public String binaryString() {
    return String.format("%16s", Integer.toBinaryString(getValue())).replaceAll(" ", "0");
  }

  @Override
  public int getValue() {
    return 0xFF00 | value;
  }

  @Override
  public void reset() {
    value = 0xFF;
  }
}
