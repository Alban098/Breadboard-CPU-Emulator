package emulation.component;

public abstract class AbstractRegister extends BusConnectedModule {

    protected final String name;
    protected int value;

    public AbstractRegister(Bus bus, ControlUnitModule controlUnit, String name) {
        super(bus, controlUnit);
        this.name = name;
    }

    @Override
    public String hexString() {
        return String.format("0x%02X", value);
    }

    @Override
    public String binaryString() {
        return String.format("%8s", Integer.toBinaryString(value)).replaceAll(" ", "0");
    }

    @Override
    public void reset() {
        value = 0;
    }

    public int getValue() {
        return value & 0xFF;
    }
}
