package emulation.component;

public class ProgramCounterModule extends BusConnectedModule {

    private int value;

    public ProgramCounterModule(Bus bus, ControlUnitModule controlUnit) {
        super(bus, controlUnit);
    }

    @Override
    public boolean clock() {
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.C_E)) {
            value = (value + 1) & 0xFF;
        }
        return false;
    }

    @Override
    public void update() {
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.C_OUT)) {
            writeBus(value);
        }
    }

    @Override
    public void reset() {
        value = 0;
    }

    @Override
    public String hexString() {
        return String.format("0x%02X", value);
    }

    @Override
    public String binaryString() {
        return String.format("%8s", Integer.toBinaryString(value)).replaceAll(" ", "0");
    }

    public int getValue() {
        return value & 0xFF;
    }
}
