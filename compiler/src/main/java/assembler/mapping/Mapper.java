/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.mapping;

import assembler.Assembler;
import assembler.constant.Instruction;
import assembler.mapping.exception.MappingException;
import assembler.mapping.token.*;
import assembler.tokenizing.TokenizationResult;
import assembler.tokenizing.Tokenizer;
import assembler.tokenizing.token.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This component will execute the Mapping stage of the compilation. It will take all tokens and
 * place them in memory space
 *
 * <p>First placing memory blocks at the specified address
 *
 * <p>Secondly finding a memory range to place the code
 *
 * <p>If the code is not placed at address 0, a jmp instruction will automatically be added
 *
 * <p>Finally placing variables and constants at the first spot they can fit, starting from the end
 * of the address space
 */
public class Mapper {

  private static final Logger LOG = LoggerFactory.getLogger(Mapper.class);

  /**
   * a global array representing the current state of all memory address, used to prevent multiple
   * tokens to be mapped to the same address
   */
  private final MappedToken[] addressesStatus = new MappedToken[Assembler.MAX_FILE_SIZE];
  /** An Object containing all mapped tokens */
  private final MappingResult result = new MappingResult();
  /** a Map referencing Mapped Operation, indexed by Operation, used to resolve Label targets */
  private final Map<Operation, MappedOperation> operationMap = new HashMap<>();
  /** Has an error been encountered during this stage */
  private boolean hasErrors = false;

  /**
   * Execute the mapping stage of the compilation
   *
   * @param tokens the output of the tokenizing stage, as return by {@link
   *     Tokenizer#getTokenizationResult()} ()}
   */
  public void mapTokens(TokenizationResult tokens) {
    mapMemoryBlocks(tokens.getMemoryBlocks());
    mapOperations(tokens.getOperations());
    mapVariables(tokens.getVariables());
    mapConstant(tokens.getConstants());
    mapLabels(tokens.getLabels());
  }

  /**
   * Returns whether errors have been encountered or not
   *
   * @return whether errors have been encountered or not
   */
  public boolean hasErrors() {
    return hasErrors;
  }

  /**
   * Returns the result of the mapping stage, containing all the mapped tokens
   *
   * @return the result of the mapping stage
   */
  public MappingResult getMappingResult() {
    return result;
  }

  /**
   * Maps a List of Memory Blocks into memory space, Mapping in itself is not needed as memory
   * address is provided, this will only check that the block can be placed at this address
   *
   * @param memoryBlocks a List of Memory Block to Map
   */
  private void mapMemoryBlocks(List<MemoryBlock> memoryBlocks) {
    LOG.info("Mapping memory blocks");
    for (MemoryBlock memoryBlock : memoryBlocks) {
      int address = memoryBlock.getAddress() & Assembler.MAX_ADDRESS;
      int index = 0;

      LOG.info(
          "-----> Mapping block of size {} bytes at locations $({}) - $({})",
          memoryBlock.getData().size(),
          String.format("%04X", address),
          String.format("%04X", address + memoryBlock.getData().size()));

      if (address != memoryBlock.getAddress()) {
        LOG.warn(
            "-----> Line {} - block on memory address '${}' is out of bound, mirroring will be applied, effective address will be '${}'",
            memoryBlock.getSourceFileLine(),
            String.format("%04X", memoryBlock.getAddress()),
            String.format("%04X", address));
      }
      MappedMemoryBlock mapped = new MappedMemoryBlock(address, memoryBlock);
      for (int ignored : memoryBlock.getData()) {
        if (address <= 1 || address > Assembler.MAX_ADDRESS) {
          LOG.error(
              "-----> Line {} - block on memory address '{}' is out of bound for value index {}, resolved address is '{}', address should be between $02 and $3FF",
              memoryBlock.getSourceFileLine(),
              String.format("$%04X", memoryBlock.getAddress()),
              index,
              String.format("$%04X", address));
          this.hasErrors = true;
        } else if (address > Assembler.STACK_POINTER_MIN_ADDRESS) {
          LOG.error(
              "-----> Line {} - block on memory address '{}' overlaps with the stack address space spanning from '{}' to '{}",
              memoryBlock.getSourceFileLine(),
              String.format("$%04X", memoryBlock.getAddress()),
              String.format("$%04X", Assembler.STACK_POINTER_MIN_ADDRESS),
              String.format("$%04X", Assembler.MAX_ADDRESS));
        } else if (addressesStatus[address] != null) {
          MappedToken mappedToken = addressesStatus[address];
          LOG.error(
              "-----> Line {} - block on memory address '${}' overlaps with already mapped token at value index {}, resolved address is '${}', that is already mapped to '{}'",
              memoryBlock.getSourceFileLine(),
              String.format("$%04X", memoryBlock.getAddress()),
              index,
              String.format("$%04X", address),
              mappedToken);
          this.hasErrors = true;
        } else {
          addressesStatus[address++] = mapped;
        }
      }
      result.add(mapped);
    }
  }

  /**
   * Maps a List of Operation into memory space, This will compute the size of the block to
   * allocate, then find a block of that size and map operation their, If the start of the block is
   * not at address 0, a JMP instruction will be placed on addresses $00 and $01 to jump to the
   * correct memory address
   *
   * @param operations a List of Operation to map
   */
  private void mapOperations(List<Operation> operations) {
    // Compute the size of the block to allocate
    AtomicInteger size = new AtomicInteger();
    operations.forEach(op -> size.addAndGet(op.getInstruction().getSize()));

    LOG.info("Mapping instructions");

    // Find memory block
    int entryPoint;
    try {
      entryPoint = findAvailableBlock(size.get(), false);
      LOG.info(
          "-----> Found block of size {} bytes at address ${}",
          size,
          String.format("%04X", entryPoint));
    } catch (Exception e) {
      LOG.error("-----> Can not find a block of size {} bytes to place compiled code", size);
      this.hasErrors = true;
      return;
    }

    // Map operations
    int currentAddress = entryPoint;
    for (Operation operation : operations) {
      MappedOperation mapped = new MappedOperation(currentAddress, operation);
      addressesStatus[currentAddress++] = mapped;
      for (int i = 1; i < operation.getInstruction().getSize(); i++) {
        addressesStatus[currentAddress++] = mapped;
      }
      result.add(mapped);
      operationMap.put(operation, mapped);
    }

    // If entrypoint is not address 0x00, add a dummy JMP instruction to jump to it
    if (entryPoint != 0) {
      Operation entryPointJump =
          new Operation(Instruction.JMP_ABS, String.format("$%04X", entryPoint), -1);
      MappedOperation mappedEntryPoint = new MappedOperation(0, entryPointJump);
      addressesStatus[0] = mappedEntryPoint;
      addressesStatus[1] = mappedEntryPoint;
      result.add(mappedEntryPoint);
      LOG.info(
          "-----> Block is not at address $0000, adding {} at address $0000",
          String.format(entryPointJump.getInstruction().format(entryPoint)));
    }
  }

  /**
   * Maps a List of Variable into memory space, This will find addresses to map variables to,
   * searching from the end of address space
   *
   * @param variables a List of variable to map
   */
  private void mapVariables(List<Variable> variables) {
    LOG.info("Mapping Variables");
    for (Variable variable : variables) {
      int address = -1;
      do {
        try {
          address = findAvailableBlock(1, true);
        } catch (Exception e) {
          LOG.error(
              "-----> Line {} - Can not find an address for variable '{}'",
              variable.getSourceFileLine(),
              variable);
          this.hasErrors = true;
        }
      } while (addressesStatus[address] != null);
      MappedVariable mapped = new MappedVariable(address, variable);
      LOG.info(
          "-----> Mapping variable '{}' at location $({})",
          variable.getAlias(),
          String.format("%04X", address));
      result.add(mapped);
    }
  }

  /**
   * Maps a List of Constant nothing to do, as Constant will be replaced by there values before
   * compilation Just pass them to next stage
   *
   * @param constants a List of Constants to map
   */
  private void mapConstant(List<Constant> constants) {
    for (Constant constant : constants) {
      MappedConstant mapped = new MappedConstant(-1, constant);
      result.add(mapped);
    }
  }

  /**
   * Maps a List of labels into memory space, labels will take the address of the operation they
   * reference. This step should be executed after all other steps of the mapping stage
   *
   * @param labels a List of Labels to map
   */
  private void mapLabels(List<Label> labels) {
    for (Label label : labels) {
      MappedOperation operation = operationMap.get(label.getReferenceOperation());
      if (operation == null) {
        LOG.error(
            "-----> Line {} - Label '{}' does not reference any instruction, Labels should be followed by an instruction",
            label.getSourceFileLine(),
            label.getAlias());
        this.hasErrors = true;
        return;
      }
      MappedLabel mapped = new MappedLabel(label, operation);
      result.add(mapped);
    }
  }

  /**
   * Finds an available block of a specified size in memory space
   *
   * @param size the size of the requested block
   * @param fromEnd should we start searching from the end
   * @return the address of the allocated block
   * @throws MappingException when the block can not be allocated
   */
  private int findAvailableBlock(int size, boolean fromEnd) throws Exception {
    final int[] runLength = new int[Assembler.STACK_POINTER_MIN_ADDRESS];
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
        if (runLength[i] >= size && (1 + i - size != 1)) {
          return 1 + i - size;
        }
      }
    } else {
      for (int i = 0; i < runLength.length; i++) {
        max = Math.max(max, runLength[i]);
        // block can not be placed at address 0x01, because the dummy jump is 2 bytes long and must
        // be placed at addresses 0x00 and 0x01
        if (runLength[i] >= size && (1 + i - size != 1)) {
          return 1 + i - size;
        }
      }
    }
    throw new MappingException(
        "Unable to find a block of "
            + size
            + " bytes for mapping code, max found is "
            + max
            + " bytes");
  }
}
