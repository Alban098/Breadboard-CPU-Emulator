/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation;

import java.util.HashMap;
import java.util.Map;
import simulation.emulation.component.*;
import simulation.emulation.component.Module;
import simulation.emulation.constant.EmulatorState;
import simulation.emulation.constant.ModuleId;
import simulation.emulation.constant.Signals;

public final class Emulator {

  private EmulatorState state = EmulatorState.DEBUG;
  private final Bus bus;
  private final Map<ModuleId, Module> modules = new HashMap<>();
  private int currentInstructionAddress = 0;

  public Emulator() {
    this.bus = new Bus();

    ControlUnitModule controlUnit = new ControlUnitModule();
    Register8 aRegister = new Register8(bus, controlUnit, Signals.A_IN, Signals.A_OUT);
    Register8 bRegister = new Register8(bus, controlUnit, Signals.B_IN, 0);
    Register8 instructionRegister = new Register8(bus, controlUnit, Signals.IR_IN, 0);
    ArithmeticLogicUnit alu = new ArithmeticLogicUnit(bus, controlUnit, aRegister, bRegister);
    StatusRegister statusRegister = new StatusRegister(bus, controlUnit, alu);
    MemoryAddressRegister memoryAddressRegister = new MemoryAddressRegister(bus, controlUnit);
    controlUnit.linkRegisters(instructionRegister, statusRegister);

    modules.put(ModuleId.PROGRAM_COUNTER, new ProgramCounter(bus, controlUnit));
    modules.put(
        ModuleId.HL_REGISTER,
        new Register16(
            bus, controlUnit, Signals.HL_IN_LOW, Signals.HL_IN_HIGH, 0, Signals.HL_OUT_16));
    modules.put(ModuleId.STACK_POINTER, new StackPointer(bus, controlUnit));
    modules.put(ModuleId.OUTPUT_REGISTER, new Register8(bus, controlUnit, Signals.OUT_IN, 0));
    modules.put(ModuleId.RAM, new Memory(bus, controlUnit, memoryAddressRegister));

    modules.put(ModuleId.MEMORY_ADDRESS_REGISTER, memoryAddressRegister);
    modules.put(ModuleId.INSTRUCTION_REGISTER, instructionRegister);
    modules.put(ModuleId.ALU, alu);
    modules.put(ModuleId.STATUS_REGISTER, statusRegister);
    modules.put(ModuleId.A_REGISTER, aRegister);
    modules.put(ModuleId.B_REGISTER, bRegister);
    modules.put(ModuleId.CONTROL_UNIT, controlUnit);
  }

  public boolean clock() {
    if (getModule(ModuleId.CONTROL_UNIT, ControlUnitModule.class).hasControlSignal(Signals.HALT)) {
      return false;
    }
    bus.update();
    update();
    boolean newInstruction = false;
    for (ModuleId id : ModuleId.values()) {
      newInstruction |= modules.get(id).clock();
      if (newInstruction) {
        currentInstructionAddress =
            getModule(ModuleId.PROGRAM_COUNTER, ProgramCounter.class).getValue();
      }
    }
    return newInstruction;
  }

  public void update() {
    bus.update();
    for (ModuleId id : ModuleId.values()) {
      modules.get(id).update();
    }
  }

  public <T> T getModule(ModuleId identifier, Class<T> clazz) {
    return clazz.cast(modules.get(identifier));
  }

  public Bus getBus() {
    return this.bus;
  }

  public EmulatorState getState() {
    return state;
  }

  public void setState(EmulatorState state) {
    this.state = state;
  }

  public void reset() {
    for (ModuleId id : ModuleId.values()) {
      modules.get(id).reset();
    }
    currentInstructionAddress = 0;
  }

  public void writeMemory(byte[] bytes) {
    getModule(ModuleId.RAM, Memory.class).writeMemory(bytes);
  }

  public int getCurrentInstructionAddress() {
    return currentInstructionAddress;
  }
}
