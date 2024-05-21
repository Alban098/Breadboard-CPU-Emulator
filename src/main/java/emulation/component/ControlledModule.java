package emulation.component;

public abstract class ControlledModule extends Module {

    protected final ControlUnitModule controlUnit;

    protected ControlledModule(ControlUnitModule controlUnit) {
        this.controlUnit = controlUnit;
    }
}
