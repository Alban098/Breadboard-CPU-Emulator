package emulation.component;

import emulation.constant.Flag;
import emulation.constant.Signal;

public final class StatusRegister extends AbstractRegister {

    public StatusRegister(Bus bus, ControlUnitModule controlUnit) {
        super(bus, controlUnit, "Status");
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(Signal.SR_IN)) {
            value = readBus();
        }
        return false;
    }

    @Override
    public void update() {
        if (controlUnit.hasControlSignal(Signal.SR_OUT)) {
            writeBus(value);
        }
    }

    public void setFlag(Flag flag, boolean value) {
        if (value) {
            this.value |= flag.getMask();
        } else {
            this.value &= ~flag.getMask();
        }
    }

    public boolean hasFlag(Flag flags) {
        return (this.value & flags.getMask()) == flags.getMask();
    }
}
