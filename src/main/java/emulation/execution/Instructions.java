package emulation.execution;

import java.util.Arrays;

public final class Instructions {

    public static final Instruction[] TABLE = new Instruction[0b1000000];

    static {
        Arrays.fill(TABLE, Instruction.NOP);
        Arrays.stream(Instruction.values()).forEach(opcode -> TABLE[opcode.getOpcode()] = opcode);
    }

    private Instructions() {}
}
