package emulation.component;

import emulation.Instructions;

import java.util.*;

public class ControlUnitModule extends Module {

    private static final Map<Integer, EnumSet<Signals>> PROGRAM_ROM = new HashMap<>();

    static {
        for (int i = 0; i < 0b1000_000000; i++) {
            PROGRAM_ROM.put(0b0000_000_000000 | i, EnumSet.of(Signals.C_OUT, Signals.MAR_IN));
            PROGRAM_ROM.put(0b0001_000_000000 | i, EnumSet.of(Signals.RAM_OUT, Signals.IR_IN));
            PROGRAM_ROM.put(0b0010_000_000000 | i, EnumSet.of(Signals.C_E, Signals.CU_RST));
        }
        PROGRAM_ROM.put(0b0010_000_000000 | Instructions.OpCodes.LDA.getOpcode(), EnumSet.of(Signals.C_E));
        PROGRAM_ROM.put(0b0011_000_000000 | Instructions.OpCodes.LDA.getOpcode(), EnumSet.of(Signals.C_OUT, Signals.MAR_IN));
        PROGRAM_ROM.put(0b0100_000_000000 | Instructions.OpCodes.LDA.getOpcode(), EnumSet.of(Signals.RAM_OUT, Signals.A_IN));
        PROGRAM_ROM.put(0b0101_000_000000 | Instructions.OpCodes.LDA.getOpcode(), EnumSet.of(Signals.C_E, Signals.CU_RST));
    }

    private InstructionRegisterModule instructionRegister;
    private StatusRegisterModule statusRegister;
    private int microStep = 0;
    private Set<Signals> signals = PROGRAM_ROM.get(0);
    private int state;
    public void linkRegisters(InstructionRegisterModule instructionRegister, StatusRegisterModule statusRegister) {
        this.instructionRegister = instructionRegister;
        this.statusRegister = statusRegister;
    }

    public boolean hasControlSignal(Signals signal) {
        return signals.contains(signal);
    }

    public enum Signals {
        C_E,
        C_IN,
        C_OUT,
        MAR_IN,
        RAM_IN,
        RAM_OUT,
        IR_IN,
        SR_IN,
        SR_OUT,
        A_IN,
        A_OUT,
        ALU_OUT,
        AND,
        SUB,
        OR,
        XOR,
        B_IN,
        B_OUT,
        OUT_IN,
        CU_RST
    }

    @Override
    public boolean clock() {
        microStep++;
        if (hasControlSignal(Signals.CU_RST)) {
            microStep = 0;
        }
        return false;
    }

    @Override
    public void update() {
        signals.clear();
        signals.addAll(PROGRAM_ROM.get(((microStep & 0b1111) << 9) | ((statusRegister.getValue() & 0b111) << 6) | (instructionRegister.getValue() & 0x0b111111)));
        for (Signals signal : signals) {
            state |= 1 << signal.ordinal();
        }
    }

    @Override
    public void reset() {
        signals = PROGRAM_ROM.get(0);
    }

    @Override
    public String hexString() {
        return String.format("0x%05X", state);
    }

    @Override
    public String binaryString() {
        return String.format("%20s", Integer.toBinaryString(state)).replaceAll(" ", "0");
    }
}
