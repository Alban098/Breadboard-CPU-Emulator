package simulation.emulation.component;

public abstract class BusConnectedModule extends ControlledModule {

    private final Bus bus;

    public BusConnectedModule(Bus bus, ControlUnitModule controlUnit) {
        super(controlUnit);
        this.bus = bus;
    }

    public int readBus() {
        return bus.read();
    }

    public void writeBus(int value) {
        bus.write(value);
    }
}
