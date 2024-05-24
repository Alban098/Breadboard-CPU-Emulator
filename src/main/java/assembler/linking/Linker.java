package assembler.linking;

import assembler.linking.exception.LinkingException;
import assembler.linking.token.LinkedMemoryBlock;
import assembler.linking.token.LinkedOperation;
import assembler.linking.token.LinkedToken;
import assembler.linking.token.LinkedVariable;
import assembler.mapping.MappingResult;
import assembler.mapping.token.*;

import java.util.ArrayList;
import java.util.List;

public class Linker {

    private final List<LinkedToken> linkedTokens = new ArrayList<>();

    public void linkTokens(MappingResult mappingResult) throws LinkingException {
        linkMemoryBlocks(mappingResult.getMemoryBlocks());
        linkVariables(mappingResult.getVariables());
        linkOperations(mappingResult.getOperations(), mappingResult.getVariables(), mappingResult.getConstants(), mappingResult.getLabels());
    }

    public List<LinkedToken> getLinkingResult() {
        return linkedTokens;
    }

    private void linkMemoryBlocks(List<MappedMemoryBlock> memoryBlocks) {
        for (MappedMemoryBlock memoryBlock : memoryBlocks) {
            int[] data = new int[memoryBlock.getToken().getData().size()];
            for (int i = 0; i < data.length; i++) {
                data[i] = memoryBlock.getToken().getData().get(i) & 0xFF;
            }
            linkedTokens.add(new LinkedMemoryBlock(memoryBlock.getAddress(), data));
        }
    }

    private void linkVariables(List<MappedVariable> variables) {
        for (MappedVariable variable : variables) {
            linkedTokens.add(new LinkedVariable(variable.getAddress(), variable.getToken().getValue()));
        }
    }

    private void linkOperations(List<MappedOperation> operations, List<MappedVariable> variables, List<MappedConstant> constants, List<MappedLabel> labels) throws LinkingException {
        for (MappedOperation operation : operations) {
            String arg = operation.getToken().getArg();
            Integer parsed = null;
            if (arg != null) {
                if (arg.startsWith("$(")) {
                    parsed = handlePlainAddress(arg);
                } else if (arg.startsWith("@")) {
                    if (operation.getToken().getInstruction().acceptLabels()) {
                        parsed = handleLabel(labels, arg);
                    } else {
                        throw new LinkingException("ERROR:? - '" + arg + "' Labels can only be used with branching instruction, not on '" + operation.getToken().getInstruction().getFormat() + "'");
                    }
                } else if (arg.startsWith("*")) {
                    parsed = handlePlainVariable(variables, arg);
                } else if (arg.startsWith("_")) {
                    parsed = handlePlainConstant(constants, arg);
                } else if (arg.startsWith("$")) {
                    parsed = handleDereferenceAlias(variables, constants, arg);
                } else if (arg.matches("[0-9A-Fa-f]{1,2}")) {
                    parsed = handlePlainValue(arg);
                } else {
                    throw new LinkingException("ERROR:? - Malformed argument : " + arg);
                }
            }
            linkedTokens.add(new LinkedOperation(operation.getAddress(), operation.getToken().getInstruction(), parsed));
        }
    }

    private Integer handleDereferenceAlias(List<MappedVariable> variables, List<MappedConstant> constants, String arg) throws LinkingException {
        String name = arg.substring(1);
        if (name.startsWith("_")) {
            return handlePlainConstant(constants, name);
        }
        return handlePlainVariable(variables, arg);
    }

    private static Integer handlePlainVariable(List<MappedVariable> variables, String arg) throws LinkingException {
        String variableName = arg.substring(1);
        MappedVariable variable = variables.stream().filter(var -> var.getToken().getAlias().equals(variableName)).findFirst().orElse(null);
        if (variable != null) {
            return variable.getAddress();
        } else {
            throw new LinkingException("ERROR:? - Variable '" + variableName + "' is not defined");
        }
    }

    private static Integer handlePlainValue(String arg) {
        return Integer.parseInt(arg, 16) & 0xFF;
    }

    private static Integer handlePlainConstant(List<MappedConstant> constants, String arg) throws LinkingException {
        String constantName = arg.substring(1);
        MappedConstant constant = constants.stream().filter(cst -> cst.getToken().getAlias().equals(constantName)).findFirst().orElse(null);
        if (constant != null) {
            return constant.getToken().getValue();
        } else {
            throw new LinkingException("ERROR:? - Constant '" + constantName + "' is not defined");
        }
    }

    private static Integer handleLabel(List<MappedLabel> labels, String arg) throws LinkingException {
        String labelName = arg.substring(1);
        MappedLabel label = labels.stream().filter(lbl -> lbl.getToken().getAlias().equals(labelName)).findFirst().orElse(null);
        if (label != null) {
            return label.getAddress();
        } else {
            throw new LinkingException("ERROR:? - Label '" + labelName + "' is not defined");
        }
    }

    private static Integer handlePlainAddress(String arg) {
        return Integer.parseInt(arg.replaceAll("[$()]", ""), 16) & 0xFF;
    }
}
