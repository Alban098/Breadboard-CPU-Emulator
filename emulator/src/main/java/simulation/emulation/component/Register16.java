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
  private final int sigIn16;
  private final int sigOut16;

  public Register16(
      Bus bus,
      ControlUnitModule controlUnit,
      int sigInLow,
      int sigInHigh,
      int sigIn16,
      int sigOut16) {
    super(bus, controlUnit);
    this.sigInLow = sigInLow;
    this.sigInHigh = sigInHigh;
    this.sigIn16 = sigIn16;
    this.sigOut16 = sigOut16;
  }

  public Register16(Bus bus, ControlUnitModule controlUnit) {
    super(bus, controlUnit);
    this.sigInLow = 0;
    this.sigInHigh = 0;
    this.sigIn16 = 0;
    this.sigOut16 = 0;
  }

  @Override
  public boolean clock() {
    if (sigInLow > 0 && controlUnit.hasControlSignal(sigInLow)) {
      value = value & 0xFF00 | (readBus8() & 0xFF);
    }
    if (sigInHigh > 0 && controlUnit.hasControlSignal(sigInHigh)) {
      value = value & 0x00FF | ((readBus8() & 0xFF) << 8);
    }
    if (sigIn16 > 0 && controlUnit.hasControlSignal(sigIn16)) {
      value = readBus16() & 0xFFFF;
    }
    return false;
  }

  @Override
  public void update() {
    if (sigOut16 > 0 && controlUnit.hasControlSignal(sigOut16)) {
      writeBus16(value & 0xFFFF);
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
