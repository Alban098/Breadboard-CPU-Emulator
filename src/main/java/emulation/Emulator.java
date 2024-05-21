package emulation;

import emulation.component.*;
import emulation.component.Module;

import java.util.HashMap;
import java.util.Map;

public class Emulator {

    private EmulatorState state = EmulatorState.DEBUG;
    private final Bus bus;
    private final ARegisterModule aRegister;
    private final BRegisterModule bRegister;
    private final InstructionRegisterModule instructionRegister;
    private final OutputRegisterModule outputRegister;
    private final StatusRegisterModule statusRegister;
    private final MemoryAddressRegisterModule memoryAddressRegister;
    private final MemoryModule ram;
    private final ArithmeticLogicUnitModule alu;
    private final ProgramCounterModule programCounter;
    private final ControlUnitModule controlUnit;

    private final Map<String, Module> modules = new HashMap<>();
    private boolean hasMemoryChanged = false;

    public Emulator() {
        this.bus = new Bus();
        this.controlUnit = new ControlUnitModule();
        this.aRegister = new ARegisterModule(bus, controlUnit, "A");
        this.bRegister = new BRegisterModule(bus, controlUnit, "B");
        this.instructionRegister = new InstructionRegisterModule(bus, controlUnit, "Instruction");
        this.outputRegister = new OutputRegisterModule(bus, controlUnit, "Output");
        this.statusRegister = new StatusRegisterModule(bus, controlUnit);
        this.memoryAddressRegister = new MemoryAddressRegisterModule(bus, controlUnit);
        this.ram = new MemoryModule(bus, controlUnit, memoryAddressRegister);
        this.alu = new ArithmeticLogicUnitModule(bus, controlUnit, aRegister, bRegister, statusRegister);
        this.programCounter = new ProgramCounterModule(bus, controlUnit);
        this.controlUnit.linkRegisters(instructionRegister, statusRegister);

        modules.put("PC", programCounter);
        modules.put("IR", instructionRegister);
        modules.put("ALU", alu);
        modules.put("STAT", statusRegister);
        modules.put("A", aRegister);
        modules.put("B", bRegister);
        modules.put("OUT", outputRegister);
        modules.put("MAR", memoryAddressRegister);
        modules.put("RAM", ram);
        modules.put("CONTROL", controlUnit);
    }

    public void clock() {
        bus.write(0);
        update();
        hasMemoryChanged |= modules.get("PC").clock();
        hasMemoryChanged |= modules.get("IR").clock();
        hasMemoryChanged |= modules.get("ALU").clock();
        hasMemoryChanged |= modules.get("STAT").clock();
        hasMemoryChanged |= modules.get("A").clock();
        hasMemoryChanged |= modules.get("B").clock();
        hasMemoryChanged |= modules.get("OUT").clock();
        hasMemoryChanged |= modules.get("MAR").clock();
        hasMemoryChanged |= modules.get("RAM").clock();
        hasMemoryChanged |= modules.get("CONTROL").clock();
    }
    public void update() {
        modules.get("PC").update();
        modules.get("IR").update();
        modules.get("ALU").update();
        modules.get("STAT").update();
        modules.get("A").update();
        modules.get("B").update();
        modules.get("OUT").update();
        modules.get("MAR").update();
        modules.get("RAM").update();
        modules.get("CONTROL").update();
    }

    public Map<String, Module> getModules() {
        return modules;
    }
    public <T> T getModule(String identifier, Class<T> clazz) {
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
        modules.get("PC").reset();
        modules.get("IR").reset();
        modules.get("ALU").reset();
        modules.get("STAT").reset();
        modules.get("A").reset();
        modules.get("B").reset();
        modules.get("OUT").reset();
        modules.get("MAR").reset();
        modules.get("RAM").reset();
        modules.get("CONTROL").reset();
    }

    public boolean hasMemoryChanged() {
        boolean tmp = hasMemoryChanged;
        hasMemoryChanged = false;
        return tmp;
    }
}
