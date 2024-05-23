package simulation.emulation.component;

import simulation.emulation.constant.Signal;

public final class ARegister extends AbstractRegister {

    public ARegister(Bus bus, ControlUnitModule controlUnit, String name) {
        super(bus, controlUnit, name);
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(Signal.A_IN)) {
            value = readBus();
        }
        return false;
    }

    @Override
    public void update() {
        if (controlUnit.hasControlSignal(Signal.A_OUT)) {
            writeBus(value);
        }
    }
}
