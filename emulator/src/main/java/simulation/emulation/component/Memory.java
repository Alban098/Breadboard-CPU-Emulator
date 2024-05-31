/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

import java.util.Arrays;
import simulation.emulation.constant.Signals;

public final class Memory extends BusConnectedModule {
  private final MemoryAddressRegister memoryAddressRegister;

  private final int[] memory = new int[0x400];

  public Memory(
      Bus bus, ControlUnitModule controlUnit, MemoryAddressRegister memoryAddressRegister) {
    super(bus, controlUnit);
    Arrays.fill(memory, 0x00);
    this.memoryAddressRegister = memoryAddressRegister;
  }

  @Override
  public boolean clock() {
    if (controlUnit.hasControlSignal(Signals.RAM_IN)) {
      memory[memoryAddressRegister.getValue()] = readBus();
    }
    return false;
  }

  @Override
  public void update() {
    if (controlUnit.hasControlSignal(Signals.RAM_OUT)) {
      writeBus(memory[memoryAddressRegister.getValue()]);
    }
  }

  @Override
  public void reset() {}

  public int readMemory(int addr) {
    return memory[addr & 0x3FF];
  }

  public void writeMemory(byte[] values) {
    for (int i = 0; i < values.length && i < memory.length; i++) {
      memory[i] = values[i] & 0xFF;
    }
  }

  @Override
  public String hexString() {
    return String.format("0x%02X", memory[memoryAddressRegister.getValue()]);
  }

  @Override
  public String binaryString() {
    return String.format("%8s", Integer.toBinaryString(memory[memoryAddressRegister.getValue()]))
        .replaceAll(" ", "0");
  }
}
