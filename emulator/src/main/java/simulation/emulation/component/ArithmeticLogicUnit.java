/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import simulation.emulation.constant.Flag;
import simulation.emulation.constant.Signal;

public final class ArithmeticLogicUnit extends BusConnectedModule {

  private final AbstractRegister aRegister;
  private final AbstractRegister bRegister;

  private int state = 0;

  public ArithmeticLogicUnit(
      Bus bus,
      ControlUnitModule controlUnit,
      AbstractRegister aRegister,
      AbstractRegister bRegister) {
    super(bus, controlUnit);
    this.aRegister = aRegister;
    this.bRegister = bRegister;
  }

  @Override
  public boolean clock() {
    state = computeState();
    return false;
  }

  @Override
  public void update() {
    state = computeState();
    if (controlUnit.hasControlSignal(Signal.ALU_OUT)) {
      writeBus(state & 0xFF);
    }
  }

  public boolean probeFlag(Flag flag) {
    return switch (flag) {
      case OVERFLOW -> (aRegister.getValue() & 0x80) != (state & 0x80);
      case ZERO -> (state & 0xFF) == 0;
      case CARRY -> state > 255;
      case NEGATIVE -> (state & 0x80) == 0x80;
    };
  }

  @Override
  public void reset() {}

  private int computeState() {
    if (controlUnit.hasControlSignal(Signal.SUB)) {
      // 2's complement
      return aRegister.getValue() + (bRegister.getValue() ^ 0xFF) + 1;
    } else if (controlUnit.hasControlSignal(Signal.OR)) {
      return aRegister.getValue() | bRegister.getValue();
    } else if (controlUnit.hasControlSignal(Signal.XOR)) {
      return aRegister.getValue() ^ bRegister.getValue();
    } else if (controlUnit.hasControlSignal(Signal.AND)) {
      return aRegister.getValue() & bRegister.getValue();
    } else {
      return aRegister.getValue() + bRegister.getValue();
    }
  }

  @Override
  public String hexString() {
    int state = computeState();
    return String.format("0x%02X", state);
  }

  @Override
  public String binaryString() {
    int state = computeState();
    return String.format("%8s", Integer.toBinaryString(state)).replaceAll(" ", "0");
  }
}
