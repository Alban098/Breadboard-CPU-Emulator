package emulation.component;

import emulation.constant.Flag;
import emulation.constant.Signal;

public final class ArithmeticLogicUnit extends BusConnectedModule {

    private final AbstractRegister aRegister;
    private final AbstractRegister bRegister;
    private final StatusRegister statusRegister;

    public ArithmeticLogicUnit(Bus bus, ControlUnitModule controlUnit, AbstractRegister aRegister, AbstractRegister bRegister, StatusRegister statusRegister) {
        super(bus, controlUnit);
        this.aRegister = aRegister;
        this.bRegister = bRegister;
        this.statusRegister = statusRegister;
    }

    @Override
    public boolean clock() {
        int state = computeState();
        if (controlUnit.hasControlSignal(Signal.ALU_OUT)) {
            statusRegister.setFlag(Flag.ZERO, (state & 0xFF) == 0);
            statusRegister.setFlag(Flag.CARRY, state > 255);
            statusRegister.setFlag(Flag.NEGATIVE, (state & 0x80) == 0x80);
        }
        return false;
    }

    @Override
    public void update() {
        int state = computeState();
        if (controlUnit.hasControlSignal(Signal.ALU_OUT)) {
            writeBus(state & 0xFF);
        }
    }

    public boolean probeFlag(Flag flag) {
        int state = computeState();
        return switch (flag) {
            case ZERO -> (state & 0xFF) == 0;
            case CARRY -> state > 255;
            case NEGATIVE -> (state & 0x80) == 0x80;
        };
    }

    @Override
    public void reset() {}

    private int computeState() {
        if (controlUnit.hasControlSignal(Signal.SUB)) {
            // 2's complement
            return aRegister.getValue() + (bRegister.getValue() ^ 0xFF) + 1;
        } else if (controlUnit.hasControlSignal(Signal.OR)) {
            return aRegister.getValue() | bRegister.getValue();
        } else if (controlUnit.hasControlSignal(Signal.XOR)) {
            return aRegister.getValue() & bRegister.getValue();
        } else if (controlUnit.hasControlSignal(Signal.AND)) {
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
