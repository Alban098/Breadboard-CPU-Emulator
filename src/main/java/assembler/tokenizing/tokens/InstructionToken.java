package assembler.tokenizing.tokens;

import simulation.emulation.execution.Instruction;

public class InstructionToken {

    private final int address;
    private final Instruction instruction;
    private final String arg;

    public InstructionToken(int address, Instruction instruction, String arg) {
        this.address = address;
        this.instruction = instruction;
        this.arg = arg;
    }

    public int getAddress() {
        return address;
    }

    public simulation.emulation.execution.Instruction getInstruction() {
        return instruction;
    }

    public String getArg() {
        return arg;
    }
}
