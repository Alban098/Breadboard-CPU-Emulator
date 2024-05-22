package emulation.execution;

import emulation.constant.Flag;
import emulation.constant.Signal;

public enum Instruction {
    NOP(
            0x00,
            "No operation",
            1,
            new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    LDA(
            0x01,
            "LOAD A immediate, loads the next byte into the A register",
            2,
            new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.A_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    ADD(
            0x02,
            "ADD immediate, Add the next byte to the A register",
            2,
            new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.B_IN},
                            new Signal[]{Signal.ALU_OUT, Signal.A_IN},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    ),
    JMP(
            0x03,
            "JMP immediate, jump to the address in the next byte",
            2,
            new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.C_E},
                            new Signal[]{Signal.C_OUT, Signal.MAR_IN},
                            new Signal[]{Signal.RAM_OUT, Signal.C_IN},
                            new Signal[]{Signal.CU_RST}
                    }
            }
    ),
    HLT(
            0x3F,
            "HALT, Hold the CPU from further execution",
            1,
            new Signal[][][]{
                    new Signal[][]{
                            new Signal[]{Signal.HLT},
                            new Signal[]{Signal.C_E, Signal.CU_RST}
                    }
            }
    );

    private final int opcode;
    private final String desc;
    private final int size;
    private final int[][] microcode;


    /**
     * @param opcode
     * @param desc
     * @param size
     * @param microcode [x][y][z]
     *                  x : status flags (not present defaults to y = 0)
     *                  y : Control Unit Micro Step (starting after the first 2 mandatory fetch steps)
     *                  z : signals (order is irrelevant)
     */
    Instruction(int opcode, String desc, int size, Signal[][][] microcode) {
        this.opcode = opcode;
        this.desc = desc;
        this.size = size;
        int flagStateCount = (1 << Flag.values().length);
        // microsteps are coded over 4 bits, so 16 possible values
        this.microcode = new int[flagStateCount][16];
        for (int flagState = 0; flagState < flagStateCount; flagState++) {
            this.microcode[flagState][0] = Signal.C_OUT.getMask() | Signal.MAR_IN.getMask();
            this.microcode[flagState][1] = Signal.RAM_OUT.getMask() | Signal.IR_IN.getMask();
        }

        if (microcode.length > 0) {
            for (int flagState = 0; flagState < flagStateCount; flagState++) {
                Signal[][] steps = microcode[microcode.length > flagState ? flagState : 0];
                if (steps.length > 0) {
                    for (int additionalStep = 0; additionalStep < steps.length && additionalStep < 14; additionalStep++) {
                        Signal[] signals = steps[additionalStep];
                        int mask = 0;
                        for (Signal signal : signals) {
                            mask |= signal.getMask();
                        }
                        this.microcode[flagState][2 + additionalStep] = mask;
                    }
                }
            }
        }
    }

    public int getOpcode() {
        return opcode;
    }

    public int getSize() {
        return size;
    }

    public int getSignals(int step, int flags) {
        if (step >= 0 && step <= this.microcode.length) {
            return this.microcode[flags][step];
        }
        return 0;
    }

    public String getDesc() {
        return desc;
    }
}
