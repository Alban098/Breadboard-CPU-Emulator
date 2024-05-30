# 8 bits Breadboard Computer - Assembly Language

## Guidelines

This assembly language has some global restrictions :

- It only supports 8 bits values
- The largest address is 0xFF = 255
- The size after compilation must not exceed 0xFF = 256 bytes

File should start with this header

```
#BB8BC_ASM_v1
```

## Comments

Comments can be added anywhere in the code with the following syntax

```
LDA $FA //load the value at the location $FA into the A Register
```

## Addressing Modes

This assembly language support 2 addressing modes :

- **Direct Addressing** : `LDA FD` This addressing mode will interpret the value as is, being an address or a value depending on the instruction
- **Indirect Addressing** : `LDA $(FD)` This addressing mode will interpret the value as a pointer, and will use the value stored at the address as the argument, for a direct addressing execution

Some instruction can use a 3rd addressing mode, in case they do not take arguments, in that case they will use the value in the A Register

## Instructions

| Instruction                                   |                                          Type | Addressing mode | Opcode | Size (bytes) | Clock cycles |                                                                                                                                                                   Desc |
|:----------------------------------------------|----------------------------------------------:|----------------:|:------:|:------------:|:------------:|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| <span style="color:#808080;">NOP</span>       |     <span style="color:#D0F000;">Misc.</span> |            None |  0x00  |      1       |      3c      |                                                                                                                                                             Do nothing |
| LDA FD                                        |    <span style="color:#D000D0;">Memory</span> |          Direct |  0x01  |      2       |      6c      |                                                                                                             **Load** the value of the argument into the **A Register** |
| LDA $(FD)                                     |    <span style="color:#D000D0;">Memory</span> |        Indirect |  0x02  |      2       |      7c      |                                                                                      **Load** the value at the address pointed by the argument into the **A Register** |
| <span style="color:#808080;">LDB $FD</span>   |    <span style="color:#D000D0;">Memory</span> |          Direct |  0x03  |      2       |      6c      |                                                                                                             **Load** the value of the argument into the **B Register** |
| <span style="color:#808080;">LDB $(FD)</span> |    <span style="color:#D000D0;">Memory</span> |        Indirect |  0x04  |      2       |      7c      |                                                                                      **Load** the value at the address pointed by the argument into the **B Register** |
| OUT FD                                        |    <span style="color:#D000D0;">Memory</span> |          Direct |  0x05  |      2       |      6c      |                                                                                                         **Put** the value of the argument into the **Output Register** |
| OUT $(FD)                                     |    <span style="color:#D000D0;">Memory</span> |        Indirect |  0x06  |      2       |      7c      |                                                                                  **Put** the value at the address pointed by the argument into the **Output Register** |
| OUT                                           |    <span style="color:#D000D0;">Memory</span> |         Implied |  0x07  |      1       |      4c      |                                                                                                   **Put** the value of the **A Register** into the **Output Register** |
| <span style="color:#808080;">STA FD</span>    |    <span style="color:#D000D0;">Memory</span> |          Direct |  0x08  |      2       |      7c      |                                                                                       **Store** the value of the **A Register** at the address pointed by the argument |
| <span style="color:#808080;">STA $(FD)</span> |    <span style="color:#D000D0;">Memory</span> |        Indirect |  0x09  |      2       |      8c      |                                                                 **Store** the value of the **A Register** at the address stored at the address pointed by the argument |
| ADD FD                                        |     <span style="color:#00D0D0;">Logic</span> |          Direct |  0x0A  |      2       |      7c      |                                                                         **Add** the value of the argument to the **A Register** and **update** the **Status Register** |
| ADD $(FD)                                     |     <span style="color:#00D0D0;">Logic</span> |        Indirect |  0x0B  |      2       |      8c      |                                                  **Add** the value at the address pointer by the argument to the **A Register** and **update** the **Status Register** |
| <span style="color:#808080;">SUB FD</span>    |     <span style="color:#00D0D0;">Logic</span> |          Direct |  0x0C  |      2       |      7c      |                                                                  **Subtract** the value of the argument from the **A Register** and **update** the **Status Register** |
| <span style="color:#808080;">SUB $(FD)</span> |     <span style="color:#00D0D0;">Logic</span> |        Indirect |  0x0D  |      2       |      8c      |                                           **Subtract** the value at the address pointer by the argument from the **A Register** and **update** the **Status Register** |
| CMP FD                                        |     <span style="color:#00D0D0;">Logic</span> |          Direct |  0x0E  |      2       |      7c      |                        **Subtract** the value of the argument from the value of the **A Register** and **update the **Status Register**, **A Register\*\* in unchanged |
| CMP $(FD)                                     |     <span style="color:#00D0D0;">Logic</span> |        Indirect |  0x0F  |      2       |      8c      | **Subtract** the value at the address pointer by the argument from the value of the **A Register** and **update the **Status Register**, **A Register\*\* in unchanged |
| CMP                                           |     <span style="color:#00D0D0;">Logic</span> |         Implied |  0x10  |      1       |      4c      |                  **Subtract** the value of the **B Register** from the value of the **A Register** and **update the **Status Register**, **A Register\*\* in unchanged |
| <span style="color:#808080;">JMP FD</span>    | <span style="color:#E06030;">Branching</span> |          Direct |  0x11  |      2       |      6c      |                                                                                                                        **Jump** to the address pointed by the argument |
| <span style="color:#808080;">JMP $(FD)</span> | <span style="color:#E06030;">Branching</span> |        Indirect |  0x12  |      2       |      7c      |                                                                                                  **Jump** to the address stored at the address pointed by the argument |
| JMA                                           | <span style="color:#E06030;">Branching</span> |         Implied |  0x13  |      1       |      4c      |                                                                                                     **Jump** to the address pointed by the value of the **A Register** |
| <span style="color:#808080;">BCS FD</span>    | <span style="color:#E06030;">Branching</span> |          Direct |  0x14  |      2       |     4-6c     |                                                                                       **Jump** to the address pointed by the argument if the **CARRY** Flag is **set** |
| <span style="color:#808080;">BCS $(FD)</span> | <span style="color:#E06030;">Branching</span> |        Indirect |  0x15  |      2       |     4-7c     |                                                                 **Jump** to the address stored at the address pointed by the argument if the **CARRY** Flag is **set** |
| BCC FD                                        | <span style="color:#E06030;">Branching</span> |          Direct |  0x16  |      2       |     4-6c     |                                                                                   **Jump** to the address pointed by the argument if the **CARRY** Flag is **not set** |
| BCC $(FD)                                     | <span style="color:#E06030;">Branching</span> |        Indirect |  0x17  |      2       |     4-7c     |                                                             **Jump** to the address stored at the address pointed by the argument if the **CARRY** Flag is **not set** |
| <span style="color:#808080;">BEQ FD</span>    | <span style="color:#E06030;">Branching</span> |          Direct |  0x18  |      2       |     4-6c     |                                                                                        **Jump** to the address pointed by the argument if the **ZERO** Flag is **set** |
| <span style="color:#808080;">BEQ $(FD)</span> | <span style="color:#E06030;">Branching</span> |        Indirect |  0x19  |      2       |     4-7c     |                                                                  **Jump** to the address stored at the address pointed by the argument if the **ZERO** Flag is **set** |
| BNE FD                                        | <span style="color:#E06030;">Branching</span> |          Direct |  0x1A  |      2       |     4-6c     |                                                                                    **Jump** to the address pointed by the argument if the **ZERO** Flag is **not set** |
| BNE $(FD)                                     | <span style="color:#E06030;">Branching</span> |        Indirect |  0x1B  |      2       |     4-7c     |                                                              **Jump** to the address stored at the address pointed by the argument if the **ZERO** Flag is **not set** |
| <span style="color:#808080;">BMI FD</span>    | <span style="color:#E06030;">Branching</span> |          Direct |  0x1C  |      2       |     4-6c     |                                                                                    **Jump** to the address pointed by the argument if the **NEGATIVE** Flag is **set** |
| <span style="color:#808080;">BMI $(FD)</span> | <span style="color:#E06030;">Branching</span> |        Indirect |  0x1D  |      2       |     4-7c     |                                                              **Jump** to the address stored at the address pointed by the argument if the **NEGATIVE** Flag is **set** |
| BPL FD                                        | <span style="color:#E06030;">Branching</span> |          Direct |  0x1E  |      2       |     4-6c     |                                                                                **Jump** to the address pointed by the argument if the **NEGATIVE** Flag is **not set** |
| BPL $(FD)                                     | <span style="color:#E06030;">Branching</span> |        Indirect |  0x1F  |      2       |     4-7c     |                                                          **Jump** to the address stored at the address pointed by the argument if the **NEGATIVE** Flag is **not set** |
| <span style="color:#808080;">BOS FD</span>    | <span style="color:#E06030;">Branching</span> |          Direct |  0x20  |      2       |     4-6c     |                                                                                    **Jump** to the address pointed by the argument if the **OVERFLOW** Flag is **set** |
| <span style="color:#808080;">BOS $(FD)</span> | <span style="color:#E06030;">Branching</span> |        Indirect |  0x21  |      2       |     4-7c     |                                                              **Jump** to the address stored at the address pointed by the argument if the **OVERFLOW** Flag is **set** |
| BOC FD                                        | <span style="color:#E06030;">Branching</span> |          Direct |  0x22  |      2       |     4-6c     |                                                                                **Jump** to the address pointed by the argument if the **OVERFLOW** Flag is **not set** |
| BOC $(FD)                                     | <span style="color:#E06030;">Branching</span> |        Indirect |  0x23  |      2       |     4-7c     |                                                          **Jump** to the address stored at the address pointed by the argument if the **OVERFLOW** Flag is **not set** |
| <span style="color:#808080;">HLT</span>       |     <span style="color:#D0F000;">Misc.</span> |            None |  0x3F  |      1       |      3c      |                                                                                                                                    Halt execution by cutting the clock |

## Memory Blocks

Memory blocks allows you to declare a static block of memory and fill it with arbitrary data.

A Memory block is defined as follows

```
.block $(addr)
    00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F
.endblock
```

- Declaration must start with `.block` and specify the base address
- Size is arbitrary but `addr + size` must not exceed 255
- Declaration must end with `.endblock`

## Constants

### Declaration

Constants are the same as a memory block, but only allows for a size of 1 byte

A constant if defined as follows

```
.const name FF
```

- Declaration must start with `.const` followed by an alias
- Aliases must start with a letter, and can contain alphanumeric characters, '-' or '\_'
- Value must be specified as hexadecimal and mandatory

### Usages

Constants have 2 use cases depending on the addressing mode

Assuming the following declaration `.const C 10` we have 2 possible use cases :

- **Direct Addressing** : `ADD _C` this will be compiled as `ADD 10` and add 10 to the A Register
- **Indirect Addressing** : `ADD $_C` this will be compiled as `ADD $(10)` and add the value at address $10 to the A Register

## Variable

### Declaration

Variable allows you to abstract a memory address, it will allocate an available address in the address space and use it as a container

A variable is defined as follows

```
.var name FF
```

- Declaration must start with `.var` followed by an alias
- Aliases must start with a letter, and can contain alphanumeric characters, '-' or '\_'
- Value is specified as Hexadecimal and is optional, if no value is passed it is assumed to be 0

### Usages

Variable have 2 use cases depending on the addressing mode

Assuming the following declaration `.var V FF` with an assigned address of `$F0` we have 2 possible use cases :

- **Direct Addressing** : `STA *V` this will be compiled as `STA F0` and store the content of the A Register at address `F0`
- **Indirect Addressing** : `LDA $V` this will be compiled as `LDA $(F0)` and load value FF into the A Register

## Labels

### Declaration

Labels are just memory address aliases, they are placed one line before an instruction and will reference the address of that instruction
they can then be referenced by branching instructions

A Label is declared as follows

```
:LABEL
```

### Usage

A Label can be referenced by branching instructions as follows

```
:LOOP
    ADD 10
    BCC @LOOP
```

This code will loop until the value in the A Register rolls over 0xFF and sets the Carry Flag,
the `BCC` instruction will jump to the address referenced by the `LOOP` label, which in this case is the `ADD 10` instruction
