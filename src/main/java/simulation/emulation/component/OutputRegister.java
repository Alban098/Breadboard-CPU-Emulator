package simulation.emulation.component;

import simulation.emulation.constant.Signal;

public final class OutputRegister extends AbstractRegister {

    public OutputRegister(Bus bus, ControlUnitModule controlUnit, String name) {
        super(bus, controlUnit, name);
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(Signal.OUT_IN)) {
            value = readBus();
        }
        return false;
    }

    @Override
    public void update() {
        //NO OP
    }
}
