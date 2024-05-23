package simulation.emulation;

import simulation.emulation.component.Module;
import simulation.emulation.constant.EmulatorState;
import simulation.emulation.constant.ModuleId;
import simulation.emulation.constant.Signal;
import simulation.emulation.component.*;

import java.util.HashMap;
import java.util.Map;

public final class Emulator {

    private EmulatorState state = EmulatorState.DEBUG;
    private final Bus bus;
    private final ARegister aRegister;
    private final BRegister bRegister;
    private final InstructionRegister instructionRegister;
    private final OutputRegister outputRegister;
    private final StatusRegister statusRegister;
    private final MemoryAddressRegister memoryAddressRegister;
    private final Memory ram;
    private final ArithmeticLogicUnit alu;
    private final ProgramCounter programCounter;
    private final ControlUnitModule controlUnit;

    private final Map<ModuleId, Module> modules = new HashMap<>();
    private boolean hasMemoryChanged = false;
    private int currentInstructionAddress = 0;

    public Emulator() {
        this.bus = new Bus();
        this.controlUnit = new ControlUnitModule();
        this.aRegister = new ARegister(bus, controlUnit, "A");
        this.bRegister = new BRegister(bus, controlUnit, "B");
        this.instructionRegister = new InstructionRegister(bus, controlUnit, "Instruction");
        this.outputRegister = new OutputRegister(bus, controlUnit, "Output");
        this.memoryAddressRegister = new MemoryAddressRegister(bus, controlUnit);
        this.ram = new Memory(bus, controlUnit, memoryAddressRegister);
        this.alu = new ArithmeticLogicUnit(bus, controlUnit, aRegister, bRegister);
        this.statusRegister = new StatusRegister(bus, controlUnit, alu);
        this.programCounter = new ProgramCounter(bus, controlUnit);
        this.controlUnit.linkRegisters(instructionRegister, statusRegister);

        modules.put(ModuleId.PROGRAM_COUNTER, programCounter);
        modules.put(ModuleId.INSTRUCTION_REGISTER, instructionRegister);
        modules.put(ModuleId.ALU, alu);
        modules.put(ModuleId.STATUS_REGISTER, statusRegister);
        modules.put(ModuleId.A_REGISTER, aRegister);
        modules.put(ModuleId.B_REGISTER, bRegister);
        modules.put(ModuleId.OUTPUT_REGISTER, outputRegister);
        modules.put(ModuleId.MEMORY_ADDRESS_REGISTER, memoryAddressRegister);
        modules.put(ModuleId.RAM, ram);
        modules.put(ModuleId.CONTROL_UNIT, controlUnit);
    }

    public boolean clock() {
        if (controlUnit.hasControlSignal(Signal.HLT)) {
            return false;
        }
        bus.write(0);
        update();
        boolean newInstruction = false;
        for (ModuleId id : ModuleId.values()) {
            newInstruction |= modules.get(id).clock();
            if (newInstruction) {
                currentInstructionAddress = programCounter.getValue();
            }
        }
        return newInstruction;
    }
    public void update() {
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
        ram.writeMemory(bytes);
    }

    public int getCurrentInstructionAddress() {
        return currentInstructionAddress;
    }
}
