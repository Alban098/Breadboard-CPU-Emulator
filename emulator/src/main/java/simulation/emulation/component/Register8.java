/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

public class Register8 extends BusConnectedModule {

  protected int value;
  private final int sigIn;
  private final int sigOut;

  public Register8(Bus bus, ControlUnitModule controlUnit, int sigIn, int sigOut) {
    super(bus, controlUnit);
    this.sigIn = sigIn;
    this.sigOut = sigOut;
    reset();
  }

  public Register8(Bus bus, ControlUnitModule controlUnit) {
    super(bus, controlUnit);
    this.sigIn = 0;
    this.sigOut = 0;
    reset();
  }

  @Override
  public boolean clock() {
    if (sigIn > 0 && controlUnit.hasControlSignal(sigIn)) {
      value = readBus8();
    }
    return false;
  }

  @Override
  public void update() {
    if (sigOut > 0 && controlUnit.hasControlSignal(sigOut)) {
      writeBus8(value);
    }
  }

  @Override
  public String hexString() {
    return String.format("0x%02X", value);
  }

  @Override
  public String binaryString() {
    return String.format("%8s", Integer.toBinaryString(value)).replaceAll(" ", "0");
  }

  @Override
  public void reset() {
    value = 0;
  }

  public int getValue() {
    return value & 0xFF;
  }
}
