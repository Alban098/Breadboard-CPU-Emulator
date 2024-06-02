# 8 bits Breadboard Computer - Electronic diagrams

## Registers
This computer has **3 working registers**, used for general purpose computations, and an **output register**

### [A Register](modules/a_register.md)
This register is the main working register, it has **read** and **write** capabilities and is directly connected to the ALU.

Its main purpose is to store the **primary operand** of any computation.
<img src="modules/schematics/a_register.png" />

### [B Register](modules/b_register.md)
This register is the secondary working register, it only has **write** capabilities and is directly connected to the ALU.

Its purpose is to store the **secondary operand** of any computation.
<img src="modules/schematics/b_register.png" />

### [HL Register](modules/hl_register.md)
This register is used to 'buffer' 16 bits memory addresses, 16 bits values can be written to it using only the 8 LSB of the BUS

It is widely used for memory addressing and branching instruction
<img src="modules/schematics/hl_register.png" />

### [Output Register](modules/output_register.md)
This register has one purpose, to display the result of a computation in a user-friendly manner, it only has **write** capabilities and display can be switch from signed to unsigned. 
<img src="modules/schematics/output_register.png" />

## Memory Management
In this computer, **RAM reads and writes** are totally **synchronous**, and no slower to access than any registers.
Memory address is selected using the **Memory Address Register** after that, **Memory** operate juste like any other register.

### [Memory Address Register](modules/memory_address_register.md)
This register's only purpose is to hold the currently selected memory address, it's output is directly connected to the address lines of the **Memory module**

It also holds control over "Manual programming mode" using a static switch, in this state, the memory address pointer is controller by 2 8DIP switch.

This register can hold a 16 bits address
<img src="modules/schematics/memory_address_register.png" />

### [Memory](modules/memory.md)
The memory module is responsible to hold all the data of the computer and should be capable of being addressed by an 8 bit value.

This module is enslaved to the **Memory Address Register**'s **Manual programming mode** switch, in this state, the written value is controlled by an 8DIP switch.
<img src="modules/schematics/memory.png" />

## Execution Logic
The execution logic is composed of 3 primary components, responsible for addressing, executing and keeping track of the current state and instructions.

### [Program Counter](modules/program_counter.md)
The program counter is responsible for keeping track of where in the execution the computer currently is, it has the ability to be programmatically set by branching instructions

The program counter can count up to #FFFF, and is one of the few modules that can exploit the entire 16 bit bus
<img src="modules/schematics/program_counter.png" />

### [Arithmetic Unit](modules/alu.md)
The alu is the main brain of the computer, it's the component that actually perform computation and is the only way to interact with the status register and execute conditional branching instructions
It has 2 modes, the first being simple addition, the second being 2's complement subtraction (A - B)
<img src="modules/schematics/alu.png" />

### [Status Register](modules/status_register.md)
The last component of the execution logic is the status register, it holds **Flags** regarding the last computation.

The 4 flags are :
- **Overflow** if the result has changed sign, this flag is only relevant if you consider your values as signed integers.
- **Zero** if the result of the computation is 0.
- **Carry** if the previous operation has produced a carry to ripple out of the 8 bit range.
- **Negative** if the most significant bit of the result is set to 1, meaning a negative number if in signed mode .
<img src="modules/schematics/status_register.png" />

### [Stack Pointer](modules/stack_pointer.md)
This register is used to hold the current stack pointer, it is hardware wise an 8 bits register, but is read as a 16bit register, with the 8 MSB set to HIGH, effectively this restricts the stack to the last memory page

<img src="modules/schematics/stack_pointer.png" />

## Control Logic
The last key part of the computer, is the control logic that will enable every component to work together as one unit.

### [Clock Module](modules/clock.md)
The clock module is at the bottom of the pyramid, it dictat the rythme to all other component and synchronize them.

This module can operate on itself or switch to "Manual" mode, in which the clock signal is controller by a psh button for debug purposes.
<img src="modules/schematics/clock.png" />

### [Instruction Register](modules/instruction_register.md)
The instruction register is similar to the **Memory Address Register** but hold the opcode of the currently executing instruction.

Its output is directly connected to the **Control Unit**
<img src="modules/schematics/instruction_register.png" />

### [Control Unit](modules/control_unit.md)
Finally, the last component is one of the most important, as it will tell each other component, what to do and when to do it depending on the current execution context 
<img src="modules/schematics/control_unit.png" />
