package assembler.tokenizing.tokens;

import simulation.emulation.execution.Instruction;

public class Operator extends Token {

    private final Instruction instruction;
    private final String arg;

    public Operator(Instruction instruction, String arg) {
        this.instruction = instruction;
        this.arg = arg;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public String getArg() {
        return arg;
    }

    @Override
    public String toString() {
        return "[" + String.format("%02X", instruction.getOpcode()) + "] " + instruction.name() + " " + (arg == null ? "" : arg);
    }
}
