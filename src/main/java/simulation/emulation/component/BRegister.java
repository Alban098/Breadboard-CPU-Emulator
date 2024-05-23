package simulation.emulation.component;

import simulation.emulation.constant.Signal;

public final class BRegister extends AbstractRegister {

    public BRegister(Bus bus, ControlUnitModule controlUnit, String name) {
        super(bus, controlUnit, name);
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(Signal.B_IN)) {
            value = readBus();
        }
        return false;
    }

    @Override
    public void update() {
        if (controlUnit.hasControlSignal(Signal.B_OUT)) {
            writeBus(value);
        }
    }
}
