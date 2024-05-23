package assembler.tokenizing;

import assembler.tokenizing.tokens.Label;
import assembler.tokenizing.tokens.MemoryBlock;
import assembler.tokenizing.tokens.Operator;
import assembler.tokenizing.tokens.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenizationResult {

    private final Map<String, Label> labels = new HashMap<>();
    private final Map<String, Variable> variables = new HashMap<>();
    private final List<MemoryBlock> memoryBlocks = new ArrayList<>();
    private final List<Operator> operators = new ArrayList<>();

    public void add(Operator operator) {
        operators.add(operator);
    }

    public void add(MemoryBlock memoryBlock) {
        memoryBlocks.add(memoryBlock);
    }

    public void add(Label label) {
        labels.put(label.getAlias(), label);
    }

    public void add(Variable variable) {
        variables.put(variable.getAlias(), variable);
    }
}
