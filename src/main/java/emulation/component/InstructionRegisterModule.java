package emulation.component;

public class InstructionRegisterModule extends RegisterModule {

    public InstructionRegisterModule(Bus bus, ControlUnitModule controlUnit, String name) {
        super(bus, controlUnit, name);
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.IR_IN)) {
            value = readBus();
        }
        return false;
    }

    @Override
    public void update() {
        //NO OP
    }
}
