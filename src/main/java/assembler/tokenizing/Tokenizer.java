package assembler.tokenizing;

import assembler.tokenizing.tokens.*;
import simulation.emulation.execution.Instruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tokenizer {

    private static final Map<String, Instruction> instructionMap = new HashMap<>();

    static {
        instructionMap.put("NOP", Instruction.NOP);
        instructionMap.put("LDA X", Instruction.LDA_IMM);
        instructionMap.put("LDA $(X)", Instruction.LDA_ABS);
        instructionMap.put("LDB X", Instruction.LDB_IMM);
        instructionMap.put("LDB $(X)", Instruction.LDB_ABS);
        instructionMap.put("LDS X", Instruction.LDS_IMM);
        instructionMap.put("LDS $(X)", Instruction.LDS_ABS);
        instructionMap.put("OUT X", Instruction.OUT_IMM);
        instructionMap.put("OUT $(X)", Instruction.OUT_ABS);
        instructionMap.put("OUT", Instruction.OUT);
        instructionMap.put("STA X", Instruction.STA_IMM);
        instructionMap.put("STA $(X)", Instruction.STA_ABS);
        instructionMap.put("STB X", Instruction.STB_IMM);
        instructionMap.put("STB $(X)", Instruction.STB_ABS);
        instructionMap.put("STS X", Instruction.STS_IMM);
        instructionMap.put("STS $(X)", Instruction.STS_ABS);
        instructionMap.put("CLS", Instruction.CLS);
        instructionMap.put("ADD X", Instruction.ADD_IMM);
        instructionMap.put("ADD $(X)", Instruction.ADD_ABS);
        instructionMap.put("SUB X", Instruction.SUB_IMM);
        instructionMap.put("SUB $(X)", Instruction.SUB_ABS);
        instructionMap.put("XOR X", Instruction.XOR_IMM);
        instructionMap.put("XOR $(X)", Instruction.XOR_ABS);
        instructionMap.put("AND X", Instruction.AND_IMM);
        instructionMap.put("AND $(X)", Instruction.AND_ABS);
        instructionMap.put("ORA X", Instruction.ORA_IMM);
        instructionMap.put("ORA $(X)", Instruction.ORA_ABS);
        instructionMap.put("CMP X", Instruction.CMP_IMM);
        instructionMap.put("CMP $(X)", Instruction.CMP_ABS);
        instructionMap.put("CMP", Instruction.CMP);
        instructionMap.put("JMP X", Instruction.JMP_IMM);
        instructionMap.put("JMP $(X)", Instruction.JMP_ABS);
        instructionMap.put("JMA", Instruction.JMA);
        instructionMap.put("BCS X", Instruction.BCS_IMM);
        instructionMap.put("BCS $(X)", Instruction.BCS_ABS);
        instructionMap.put("BCC X", Instruction.BCC_IMM);
        instructionMap.put("BCC $(X)", Instruction.BCC_ABS);
        instructionMap.put("BEQ X", Instruction.BEQ_IMM);
        instructionMap.put("BEQ $(X)", Instruction.BEQ_ABS);
        instructionMap.put("BNE X", Instruction.BNE_IMM);
        instructionMap.put("BNE $(X)", Instruction.BNE_ABS);
        instructionMap.put("BMI X", Instruction.BMI_IMM);
        instructionMap.put("BMI $(X)", Instruction.BMI_ABS);
        instructionMap.put("BPL X", Instruction.BPL_IMM);
        instructionMap.put("BPL $(X)", Instruction.BPL_ABS);
        instructionMap.put("BOS X", Instruction.BOS_IMM);
        instructionMap.put("BOS $(X)", Instruction.BOS_ABS);
        instructionMap.put("BOC X", Instruction.BOC_IMM);
        instructionMap.put("BOC $(X)", Instruction.BOC_ABS);
        instructionMap.put("HLT", Instruction.HLT);
    }

    private Mode currentMode = Mode.CODE;
    private MemoryBlock currentMemoryBlock;
    private int currentAddress = 0;
    private int lineIndex = 0;

    private final Map<String, Label> labels = new HashMap<>();
    private final Map<String, Variable> vars = new HashMap<>();
    private final Map<String, MemoryBlock> memoryBlocks = new HashMap<>();
    private final List<InstructionToken> instructions = new ArrayList<>();

    public void tokenize(String line) throws TokenizingException {
        lineIndex++;
        handleLine(line, lineIndex);
    }

    private void handleLine(String line, int index) throws TokenizingException {
        String[] tokens = line.split(" ");
        switch (currentMode) {
            case MEMORY_BLOCK -> fillMemoryBlock(index, tokens);
            case CODE -> {
                if (tokens[0].startsWith(".block")) {
                    handleMemoryBlock(index, tokens);
                } else if (tokens[0].startsWith(".var")) {
                    handleVariable(index, tokens);
                } else if (tokens[0].startsWith(":")) {
                    handleLabel(index, tokens);
                } else {
                    handleInstruction(index, tokens);
                }
            }
        }
    }

    private void handleInstruction(int index, String[] tokens) throws TokenizingException {
        String operand = tokens[0];
        String arg = null;
        if (tokens.length == 2) {
            arg = tokens[2];
            if (tokens[1].startsWith("$(") || tokens[1].startsWith("@")) {
                operand += " $(X)";
            } else {
                operand += "X";
            }
        }
        Instruction decoded = instructionMap.get(operand);
        if (decoded == null) {
            throw new TokenizingException("ERROR:" + index + " - " + tokens[0] + " is not recognized as an instruction, or isn't compatible with the passed argument");
        }
        InstructionToken token = new InstructionToken(currentAddress, decoded, arg);
        instructions.add(token);
        currentAddress += decoded.getSize();
    }

    private void fillMemoryBlock(int index, String[] tokens) throws TokenizingException {
        for (String token : tokens) {
            try {
                int value = Integer.parseInt(token, 16);
                currentMemoryBlock.addData(value);
            } catch (NumberFormatException e) {
                throw new TokenizingException("ERROR:" + index + " - " + token + " is not an hexadecimal value");
            }
        }
    }

    private void handleMemoryBlock(int index, String[] tokens) throws TokenizingException {
        if (tokens.length != 2) {
            throw new TokenizingException("ERROR:" + index + " - .block must be followed by its memory address");
        }
        currentMode = Mode.MEMORY_BLOCK;
        try {
            currentMemoryBlock = new MemoryBlock(tokens[0], tokens[1]);
        } catch (MalformedAddress e) {
            throw new TokenizingException("ERROR:" + index + " - addresses must be formatted like '$FF'");
        }
        memoryBlocks.put(tokens[0], currentMemoryBlock);
    }

    private void handleLabel(int index, String[] tokens) throws TokenizingException {
        if (tokens.length != 2) {
            throw new TokenizingException("ERROR:" + index + " - Labels must not contains blank space");
        }
        try {
            labels.put(tokens[1], new Label(tokens[1], Integer.toHexString(currentAddress)));
        } catch (MalformedAddress e) {
            throw new TokenizingException("ERROR:" + index + " - addresses must be formatted like '$FF'");
        }
    }

    private void handleVariable(int index, String[] tokens) throws TokenizingException {
        if (tokens.length != 2 && tokens.length != 3) {
            throw new TokenizingException("ERROR:" + index + " - .var must be followed by its alias and an optional value");
        }
        vars.put(tokens[1], new Variable(tokens[1], tokens.length == 3 ? tokens[2] : null));
    }
}
