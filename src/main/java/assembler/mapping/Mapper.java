package assembler.mapping;

import assembler.Instruction;
import assembler.mapping.exception.MappingException;
import assembler.mapping.token.*;
import assembler.tokenizing.TokenizationResult;
import assembler.tokenizing.token.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Mapper {

    private final MappedToken[] addressesStatus = new MappedToken[0x100];
    private final MappingResult result = new MappingResult();
    private final Map<Operation, MappedOperation> operationMap = new HashMap<>();

    public Mapper() {
        Arrays.fill(addressesStatus, null);
    }

    public void mapTokens(TokenizationResult tokens) throws MappingException {
        mapMemoryBlocks(tokens.getMemoryBlocks());
        mapOperations(tokens.getOperations());
        mapVariables(tokens.getVariables());
        mapConstant(tokens.getConstants());
        mapLabels(tokens.getLabels());
    }

    public MappingResult getMappingResult() {
        return result;
    }

    private void mapMemoryBlocks(List<MemoryBlock> memoryBlocks) throws MappingException {
        for (MemoryBlock memoryBlock : memoryBlocks) {
            int address = memoryBlock.getAddress();
            int index = 0;
            MappedMemoryBlock mapped = new MappedMemoryBlock(address, memoryBlock);
            for (int ignored : memoryBlock.getData()) {
                if (address <= 1 || address > 0xFF) {
                    throw new MappingException(
                            "ERROR:? - block on memory address '" +
                            String.format("$%02X", memoryBlock.getAddress()) +
                            "' is out of bound for value index " +
                            index + ", resolved address is " +
                            String.format("$%02X", address) +
                            ", address sould be between $02 and $FF");
                } else if (addressesStatus[address] != null) {
                    MappedToken mappedToken = addressesStatus[address];
                    throw new MappingException(
                            "ERROR:? - block on memory address '" +
                            String.format("$%02X", memoryBlock.getAddress()) +
                            "' overlaps with already mapped token at value index " +
                            index + ", resolved address is " +
                            String.format("$%02X", address) +
                            " that is already mapped to '" +
                            mappedToken.toString() +
                            "'");
                } else {
                    addressesStatus[address++] = mapped;
                }
            }
            result.add(mapped);
        }
    }

    private void mapOperations(List<Operation> operations) throws MappingException {
        AtomicInteger size = new AtomicInteger();
        operations.forEach(op -> size.addAndGet(op.getInstruction().getSize()));
        int entryPoint = findAvailableBlock(size.get(), false);
        int currentAddress = entryPoint;
        for (Operation operation : operations) {
            MappedOperation mapped = new MappedOperation(currentAddress, operation);
            addressesStatus[currentAddress++] = mapped;
            if (operation.getInstruction().getSize() == 2) {
                addressesStatus[currentAddress++] = mapped;
            }
            result.add(mapped);
            operationMap.put(operation, mapped);
        }
        // If entrypoint is not address 0x00, add a dummy JMP instruction to jump to it
        if (entryPoint != 0) {
            Operation entryPointJump = new Operation(Instruction.JMP_IMM, String.format("%02X", entryPoint));
            MappedOperation mappedEntryPoint = new MappedOperation(0, entryPointJump);
            addressesStatus[0] = mappedEntryPoint;
            addressesStatus[1] = mappedEntryPoint;
            result.add(mappedEntryPoint);
        }
    }

    private void mapVariables(List<Variable> variables) throws MappingException {
        for (Variable variable : variables) {
            int address;
            do {
                address = findAvailableBlock(1, true);
            } while (addressesStatus[address] != null);
            MappedVariable mapped = new MappedVariable(address, variable);
            result.add(mapped);
        }
    }

    private void mapConstant(List<Constant> constants) throws MappingException {
        for (Constant constant : constants) {
            MappedConstant mapped = new MappedConstant(-1, constant);
            result.add(mapped);
        }
    }

    private void mapLabels(List<Label> labels) throws MappingException {
        for (Label label : labels) {
            MappedOperation operation = operationMap.get(label.getReferenceOperation());
            if (operation == null) {
                throw new MappingException("ERROR:? - Label '" + label.getAlias() + "' reference a token that is not an instruction");
            }
            MappedLabel mapped = new MappedLabel(label, operation);
            result.add(mapped);
        }
    }

    private int findAvailableBlock(int size, boolean fromEnd) throws MappingException {
        final int[] runLength = new int[0x100];
        int currentLength = 1;
        for (int i = 0; i < runLength.length; i++) {
            if (addressesStatus[i] == null) {
                runLength[i] = currentLength;
                currentLength++;
            } else {
                runLength[i] = 0;
                currentLength = 1;
            }
        }
        int max = 0;
        if (fromEnd) {
            for (int i = runLength.length - 1; i > 2; i--) {
                max = Math.max(max, runLength[i]);
                if (runLength[i] == size) {
                    return 1 + i - size;
                }
            }
        } else {
            for (int i = 0; i < runLength.length; i++) {
                max = Math.max(max, runLength[i]);
                // block can not be placed at address 0x01, because the dummy jump is 2 bytes long and must be placed at addresses 0x00 and 0x01
                if (runLength[i] == size && (1 + i - size != 1)) {
                    return 1 + i - size;
                }
            }
        }
        throw new MappingException("ERROR:? - Unable to find a block of " + size + " bytes for mapping code, max found is " + max + " bytes");
    }
}
