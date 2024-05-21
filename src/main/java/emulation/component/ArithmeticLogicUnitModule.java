package emulation.component;

public class ArithmeticLogicUnitModule extends BusConnectedModule {

    private final RegisterModule aRegister;
    private final RegisterModule bRegister;
    private final StatusRegisterModule statusRegister;

    public ArithmeticLogicUnitModule(Bus bus, ControlUnitModule controlUnit, RegisterModule aRegister, RegisterModule bRegister, StatusRegisterModule statusRegister) {
        super(bus, controlUnit);
        this.aRegister = aRegister;
        this.bRegister = bRegister;
        this.statusRegister = statusRegister;
    }

    @Override
    public boolean clock() {
        int state = computeState();
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.ALU_OUT)) {
            statusRegister.setFlag(StatusRegisterModule.Flags.ZERO, (state & 0xFF) == 0);
            statusRegister.setFlag(StatusRegisterModule.Flags.CARRY, state > 255);
            statusRegister.setFlag(StatusRegisterModule.Flags.NEGATIVE, (state & 0x80) == 0x80);
        }
        return false;
    }

    @Override
    public void update() {
        int state = computeState();
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.ALU_OUT)) {
            writeBus(state & 0xFF);
        }
    }

    @Override
    public void reset() {}

    private int computeState() {
        if (controlUnit.hasControlSignal(ControlUnitModule.Signals.SUB)) {
            // 2's complement
            return aRegister.getValue() + (bRegister.getValue() ^ 0xFF) + 1;
        } else if (controlUnit.hasControlSignal(ControlUnitModule.Signals.OR)) {
            return aRegister.getValue() | bRegister.getValue();
        } else if (controlUnit.hasControlSignal(ControlUnitModule.Signals.XOR)) {
            return aRegister.getValue() & bRegister.getValue();
        } else if (controlUnit.hasControlSignal(ControlUnitModule.Signals.AND)) {
            return aRegister.getValue() ^ bRegister.getValue();
        } else {
            return aRegister.getValue() + bRegister.getValue();
        }
    }

    @Override
    public String hexString() {
        int state = computeState();
        return String.format("0x%02X", state);
    }

    @Override
    public String binaryString() {
        int state = computeState();
        return String.format("%8s", Integer.toBinaryString(state)).replaceAll(" ", "0");
    }
}
