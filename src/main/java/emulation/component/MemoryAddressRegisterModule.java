package emulation.component;

public class MemoryAddressRegisterModule extends RegisterModule {

    public MemoryAddressRegisterModule(Bus bus, ControlUnitModule controlUnit) {
        super(bus, controlUnit, "Memory Address");
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.MAR_IN)) {
            value = readBus();
        }
        return false;
    }

    @Override
    public void update() {
        // NO OP
    }
}
