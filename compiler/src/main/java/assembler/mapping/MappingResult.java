/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler.mapping;

import assembler.mapping.token.*;
import java.util.ArrayList;
import java.util.List;

public class MappingResult {

  private final List<MappedMemoryBlock> memoryBlocks = new ArrayList<>();
  private final List<MappedOperation> operations = new ArrayList<>();
  private final List<MappedLabel> labels = new ArrayList<>();
  private final List<MappedVariable> variables = new ArrayList<>();
  private final List<MappedConstant> constants = new ArrayList<>();

  public void add(MappedOperation operation) {
    operations.add(operation);
  }

  public void add(MappedMemoryBlock memoryBlock) {
    memoryBlocks.add(memoryBlock);
  }

  public void add(MappedLabel label) {
    labels.add(label);
  }

  public void add(MappedVariable variable) {
    variables.add(variable);
  }

  public void add(MappedConstant constant) {
    constants.add(constant);
  }

  public List<MappedMemoryBlock> getMemoryBlocks() {
    return memoryBlocks;
  }

  public List<MappedOperation> getOperations() {
    return operations;
  }

  public List<MappedLabel> getLabels() {
    return labels;
  }

  public List<MappedVariable> getVariables() {
    return variables;
  }

  public List<MappedConstant> getConstants() {
    return constants;
  }
}
