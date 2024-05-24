package assembler.tokenizing;

import assembler.Instruction;
import assembler.tokenizing.exception.MalformedAddressException;
import assembler.tokenizing.exception.MalformedValueException;
import assembler.tokenizing.exception.TokenizingException;
import assembler.tokenizing.token.*;

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

    private final TokenizationResult result = new TokenizationResult();
    private Mode currentMode = Mode.CODE;
    private MemoryBlock currentMemoryBlock;
    private Label labelToReference;
    private int lineIndex = 1; //Initialize at 1 because of mandatory header

    public void tokenize(String line) throws TokenizingException {
        lineIndex++;
        tokenizeLine(line, lineIndex);
    }

    public TokenizationResult getTokenizationResult() {
        return result;
    }

    private void tokenizeLine(String line, int index) throws TokenizingException {
        String[] tokens = line.split(" ");
        tokens = sanitizeTokens(tokens);

        //skip blank lines
        if (tokens.length == 0) {
            return;
        }
        switch (currentMode) {
            case MEMORY_BLOCK -> fillMemoryBlock(index, tokens);
            case CODE -> {
                if (tokens[0].startsWith(".block")) {
                    tokenizeMemoryBlock(index, tokens);
                } else if (tokens[0].startsWith(".var")) {
                    tokenizeVariable(index, tokens);
                } else if (tokens[0].startsWith(".const")) {
                    tokenizeConstant(index, tokens);
                } else if (tokens[0].startsWith(":")) {
                    tokenizeLabel(index, tokens);
                } else if (!tokens[0].isBlank()){
                    tokenizeInstruction(index, tokens);
                }
            }
        }
    }

    private String[] sanitizeTokens(String[] tokens) {
        List<String> sanitized = new ArrayList<>(tokens.length);
        for (String token : tokens) {
            if (token.startsWith("//")) {
                break;
            } if (!token.isBlank()) {
                sanitized.add(token);
            }
        }
        return sanitized.toArray(new String[0]);
    }

    private void tokenizeInstruction(int index, String[] tokens) throws TokenizingException {
        String operand = tokens[0];
        String arg = null;
        Instruction decoded;
        if (tokens.length == 2) {
            arg = tokens[1];
            try {
                checkArg(arg);
            } catch (MalformedValueException e) {
                throw new TokenizingException("ERROR:" + index + " - " + arg + " is not a valid arguments");
            }
            // '$' means we need to dereference the address
            // - for variables this will replace '$var' by '$(addr)'
            // - for constants this will replace '$_const' by '$(val)'
            if (arg.startsWith("$")) {
                operand += " $(X)";
            } else {
                operand += " X";
            }
        }
        decoded = instructionMap.get(operand);
        if (decoded == null) {
            throw new TokenizingException("ERROR:" + index + " - " + tokens[0] + " is not recognized as an instruction, or isn't compatible with the passed argument");
        }
        Operation token = new Operation(decoded, arg);
        if (labelToReference != null) {
            labelToReference.setReferenceOperation(token);
            labelToReference = null;
        }
        result.add(token);
    }

    private boolean checkArg(String arg) throws MalformedValueException {
        if (arg.startsWith("$(") && arg.matches("\\$\\([0-9A-Fa-f]{1,2}\\)")) {
            return true;
        } else if (arg.startsWith("@") && arg.matches("@[a-zA-Z]+[a-zA-Z0-9_-]*")) {
            return true;
        } else if (arg.startsWith("*") && arg.matches("\\*[a-zA-Z]+[a-zA-Z0-9_-]*")) {
            return true;
        } else if (arg.startsWith("$") && arg.matches("\\$_?[a-zA-Z]+[a-zA-Z0-9_-]*")) {
            return true;
        } else if (arg.startsWith("_") && arg.matches("_[a-zA-Z]+[a-zA-Z0-9_-]*")) {
            return true;
        } else if (arg.matches("[0-9A-Fa-f]{1,2}")) {
            return true;
        }
        throw new MalformedValueException();
    }

    private void fillMemoryBlock(int index, String[] tokens) throws TokenizingException {
        for (String token : tokens) {
            if (token.equals(".endblock")) {
                currentMode = Mode.CODE;
                currentMemoryBlock = null;
                return;
            }
            try {
                int value = Integer.parseInt(token, 16);
                currentMemoryBlock.addData(value);
            } catch (NumberFormatException e) {
                throw new TokenizingException("ERROR:" + index + " - " + token + " is not an hexadecimal value (have you missed a '.endblock' tag ?)");
            }
        }
    }

    private void tokenizeMemoryBlock(int index, String[] tokens) throws TokenizingException {
        if (tokens.length != 2) {
            throw new TokenizingException("ERROR:" + index + " - .block must be followed by its memory address");
        }
        currentMode = Mode.MEMORY_BLOCK;
        try {
            if (tokens[1].startsWith("$(") && tokens[1].matches("\\$\\([0-9A-Fa-f]{1,2}\\)")) {
                currentMemoryBlock = new MemoryBlock(tokens[1]);
            } else {
                throw new TokenizingException("ERROR:" + index + " - addresses must be formatted like '$(FF)'");
            }
        } catch (MalformedAddressException e) {
            throw new TokenizingException("ERROR:" + index + " - addresses must be formatted like '$(FF)'");
        }
        result.add(currentMemoryBlock);
    }

    private void tokenizeLabel(int index, String[] tokens) throws TokenizingException {
        if (tokens.length != 1) {
            throw new TokenizingException("ERROR:" + index + " - Labels must not contains blank space");
        }
        labelToReference = new Label(tokens[0].replace(":", ""));
        result.add(labelToReference);
    }

    private void tokenizeVariable(int index, String[] tokens) throws TokenizingException {
        if (tokens.length != 2 && tokens.length != 3) {
            throw new TokenizingException("ERROR:" + index + " - .var must be followed by its alias and an optional value");
        }
        try {
            result.add(new Variable(tokens[1], tokens.length == 3 ? tokens[2] : null));
        } catch (NumberFormatException e) {
            throw new TokenizingException("ERROR:" + index + " - " + tokens[2] + " is not a valid hexadecimal value");
        }
    }

    private void tokenizeConstant(int index, String[] tokens) throws TokenizingException {
        if (tokens.length != 3) {
            throw new TokenizingException("ERROR:" + index + " - .const must be followed by its alias and a value");
        }
        try {
            result.add(new Constant(tokens[1], tokens[2]));
        } catch (NumberFormatException e) {
            throw new TokenizingException("ERROR:" + index + " - " + tokens[2] + " is not a valid hexadecimal value");
        }
    }
}
