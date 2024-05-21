package emulation;

import java.util.Arrays;

public class Instructions {

    public static final OpCodes[] INSTRUCTIONS = new OpCodes[0b1000000];

    static {
        Arrays.fill(INSTRUCTIONS, OpCodes.NOP);
        Arrays.stream(OpCodes.values()).forEach(opcode -> INSTRUCTIONS[opcode.opcode] = opcode);
    }

    public enum OpCodes {
        NOP(0x00, 1),
        LDA(0x01, 2),
        ADD(0x02, 2),
        HLT(0x3F, 1);

        private final int opcode;
        private final int size;

        OpCodes(int opcode, int size) {
            this.opcode = opcode;
            this.size = size;
        }

        public int getOpcode() {
            return opcode;
        }

        public int getSize() {
            return size;
        }
    }
}
