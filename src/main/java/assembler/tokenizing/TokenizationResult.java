package assembler.tokenizing;

import assembler.tokenizing.token.*;

import java.util.ArrayList;
import java.util.List;

public class TokenizationResult {

    private final List<Label> labels = new ArrayList<>();
    private final List<Variable> variables = new ArrayList<>();
    private final List<Constant> constants = new ArrayList<>();
    private final List<MemoryBlock> memoryBlocks = new ArrayList<>();
    private final List<Operation> operations = new ArrayList<>();

    public void add(Operation operation) {
        operations.add(operation);
    }

    public void add(MemoryBlock memoryBlock) {
        memoryBlocks.add(memoryBlock);
    }

    public void add(Label label) {
        labels.add(label);
    }

    public void add(Variable variable) {
        variables.add(variable);
    }

    public void add(Constant constant) {
        constants.add(constant);
    }

    public List<Label> getLabels() {
        return labels;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public List<MemoryBlock> getMemoryBlocks() {
        return memoryBlocks;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public List<Constant> getConstants() {
        return constants;
    }
}
