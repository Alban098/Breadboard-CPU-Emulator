/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

public class Register16 extends BusConnectedModule {

  protected int value;
  private final int sigInLow;
  private final int sigInHigh;
  private final int sigOutLow;
  private final int sigOutHigh;

  public Register16(
      Bus bus,
      ControlUnitModule controlUnit,
      int sigInLow,
      int sigInHigh,
      int sigOutLow,
      int sigOutHigh) {
    super(bus, controlUnit);
    this.sigInLow = sigInLow;
    this.sigInHigh = sigInHigh;
    this.sigOutLow = sigOutLow;
    this.sigOutHigh = sigOutHigh;
  }

  public Register16(Bus bus, ControlUnitModule controlUnit) {
    super(bus, controlUnit);
    this.sigInLow = 0;
    this.sigInHigh = 0;
    this.sigOutLow = 0;
    this.sigOutHigh = 0;
  }

  @Override
  public boolean clock() {
    if (sigInLow > 0 && controlUnit.hasControlSignal(sigInLow)) {
      value = value & 0xFF00 | (readBus() & 0xFF);
    }
    if (sigInHigh > 0 && controlUnit.hasControlSignal(sigInHigh)) {
      value = value & 0x00FF | ((readBus() & 0xFF) << 8);
    }
    return false;
  }

  @Override
  public void update() {
    if (sigOutHigh > 0 && controlUnit.hasControlSignal(sigOutHigh)) {
      writeBus((value >> 8) & 0x00FF);
    }
    if (sigOutLow > 0 && controlUnit.hasControlSignal(sigOutLow)) {
      writeBus(value & 0x00FF);
    }
  }

  @Override
  public String hexString() {
    return String.format("0x%04X", value);
  }

  @Override
  public String binaryString() {
    return String.format("%16s", Integer.toBinaryString(value)).replaceAll(" ", "0");
  }

  @Override
  public void reset() {
    value = 0;
  }

  public int getValue() {
    return value & 0xFFFF;
  }
}
