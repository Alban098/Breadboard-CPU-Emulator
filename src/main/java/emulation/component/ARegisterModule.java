package emulation.component;

public class ARegisterModule extends RegisterModule {

    public ARegisterModule(Bus bus, ControlUnitModule controlUnit, String name) {
        super(bus, controlUnit, name);
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.A_IN)) {
            value = readBus();
        }
        return false;
    }

    @Override
    public void update() {
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.A_OUT)) {
            writeBus(value);
        }
    }
}
