package emulation.component;

public class StatusRegisterModule extends RegisterModule {

    public enum Flags {
        ZERO(0b100),
        CARRY(0b010),
        NEGATIVE(0b001);

        final int mask;

        Flags(int mask) {
            this.mask = mask;
        }
    }

    public StatusRegisterModule(Bus bus, ControlUnitModule controlUnit) {
        super(bus, controlUnit, "Status");
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.SR_IN)) {
            value = readBus();
        }
        return false;
    }

    @Override
    public void update() {
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.SR_OUT)) {
            writeBus(value);
        }
    }

    public void setFlag(Flags flag, boolean value) {
        if (value) {
            this.value |= flag.mask;
        } else {
            this.value &= ~flag.mask;
        }
    }

    public boolean hasFlag(Flags flags) {
        return (this.value & flags.mask) == flags.mask;
    }
}
