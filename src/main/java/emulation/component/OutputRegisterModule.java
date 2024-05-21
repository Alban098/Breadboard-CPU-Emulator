package emulation.component;

public class OutputRegisterModule extends RegisterModule {

    public OutputRegisterModule(Bus bus, ControlUnitModule controlUnit, String name) {
        super(bus, controlUnit, name);
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.OUT_IN)) {
            value = readBus();
        }
        return false;
    }

    @Override
    public void update() {
        //NO OP
    }
}
