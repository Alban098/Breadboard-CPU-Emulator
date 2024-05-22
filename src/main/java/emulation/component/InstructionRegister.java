package emulation.component;

import emulation.constant.Signal;

public final class InstructionRegister extends AbstractRegister {

    public InstructionRegister(Bus bus, ControlUnitModule controlUnit, String name) {
        super(bus, controlUnit, name);
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(Signal.IR_IN)) {
            value = readBus();
        }
        return false;
    }

    @Override
    public void update() {
        //NO OP
    }
}
