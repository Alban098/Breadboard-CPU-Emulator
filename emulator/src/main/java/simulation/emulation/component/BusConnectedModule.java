/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

public abstract class BusConnectedModule extends ControlledModule {

  protected final Bus bus;

  public BusConnectedModule(Bus bus, ControlUnitModule controlUnit) {
    super(controlUnit);
    this.bus = bus;
  }

  public final int readBus8() {
    return bus.read();
  }

  public final void writeBus8(int value) {
    bus.write(value);
  }

  public final int readBus16() {
    return bus.read16();
  }

  public final void writeBus16(int value) {
    bus.write16(value);
  }
}
