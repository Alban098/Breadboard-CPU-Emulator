package emulation.component;

public class BRegisterModule extends RegisterModule {

    public BRegisterModule(Bus bus, ControlUnitModule controlUnit, String name) {
        super(bus, controlUnit, name);
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.B_IN)) {
            value = readBus();
        }
        return false;
    }

    @Override
    public void update() {
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.B_OUT)) {
            writeBus(value);
        }
    }
}
