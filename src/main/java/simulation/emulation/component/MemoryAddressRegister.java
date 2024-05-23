package simulation.emulation.component;

import simulation.emulation.constant.Signal;

public final class MemoryAddressRegister extends AbstractRegister {

    public MemoryAddressRegister(Bus bus, ControlUnitModule controlUnit) {
        super(bus, controlUnit, "Memory Address");
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(Signal.MAR_IN)) {
            value = readBus();
        }
        return false;
    }

    @Override
    public void update() {
        // NO OP
    }
}
