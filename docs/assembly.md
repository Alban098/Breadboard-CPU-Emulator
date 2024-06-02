# 8 bits Breadboard Computer - Assembly Language

## Guidelines

This assembly language has some global restrictions :

- Values are 1 byte.
- Addresses are 2 bytes.
- Address space can go up to $FFFF, but currently the hardware is designed with mirroring after $3FF.
- The address space is 1KByte but the last page ($0300 to $03FF) is reserved to the stack.
- Stack overflow will trigger the computer to HALT.
- The base computer is designed to work with big endian values.

File should start with this header :

```
#BB8BC_ASM_v1
```

## Comments

Comments can be added anywhere in the code with the following syntax :

```
// Full comment
LDA #10    // Inline comment
```

## Addressing Modes

This assembly is specifically designed to work with the Breadboard computer, therefore it includes all the addressing modes that the hardware implements :
### Implied
**Instructions** using this addressing mode will be **1 byte long** and have **no operand**, the instruction is enough to deduce everything needed for the execution.

Examples :
```
NOP
PHA
PLB
CLS
```

### Immediate
**Instructions** using this addressing mode will be **2 byte long** and have **1 operand**, the operand is interpreted as a **plain hexadecimal value**.

This addressing mode is compatible with **1 byte Constants**.

Operands should be formatted as follows :
- Start with a ``#``.
- **Constants** should be surrounded by parenthesis.

Examples :
```
LDA #FF
STA #(CONST8)
```

### Absolute
This addressing mode works as an absolute pointer.

**Instructions** using this addressing mode will be **3 byte long** and have **2 operands**, the operand is interpreted as a big endian **2 bytes address**.

This addressing mode is compatible with **Variables** and **1 & 2 bytes Constants**, **1 byte Constants** should be completed by a memory page or an offset, if only the constant is passed, the compiler will replace it with the **Zero Page** version as it is faster to execute.

Operands should be formatted as follows :
- Start with a ``$`` for plain **address** and **Constants**.
- **Constants** should be surrounded by parenthesis.
- **Variable** should not be prefixed or surrounded.

Examples :
```
LDA $(CONST16)
ADD $2FF
STA VARIABLE
ADD $02(CONST8)
ADD $(CONST8)A9
```

### Zero Page
This addressing mode works in the same way as the **Absolute** mode but is restricted to the first 256 bytes page of memory, and is 1 cycle faster to execute.

**Instructions** using this addressing mode will be **2 byte long** and have **1 operand**, the operand is interpreted as an offset in the 0th memory page.

This addressing mode is compatible with **1 byte Constants**

Operands should be formatted as follows :
- Start with a ``$``
- **Constants** should be surrounded by parenthesis

Examples :
```
LDA $(CONST8)
ADD $80
```

### Indexed
This addressing mode is kind of an extension of **Zero Page** and **Absolute** as it allows to index a **specified memory page** using the **A Register**.

**Instructions** using this addressing mode will be **2 byte long** and have **1 operand**, **the operand** is interpreted the **page to index** (the 8 MSB of the address), **the offset** inside the page is retrieved from the **A Register** 

This addressing mode is compatible with **1 byte Constants**

Operands should be formatted as follows :
- Start with a ``$``
- End with ``, A``
- **Constants** should be surrounded by parenthesis

Examples :
```
ADD $02, A
ADD $(CONST8), A
```

## Instructions

|  Hex  | Assembly                                                   |                               Addressing Mode |      Type | Size | Clock cycles |
|:-----:|:-----------------------------------------------------------|----------------------------------------------:|----------:|-----:|-------------:|
| 0x00  | **[NOP](#nop)**                                            |                                       Implied |     Misc. |    1 |           4c | 
| 0x01  | **[LDA](#lda)** <span style="color:#E6DC31;">#FD</span>    | <span style="color:#E6DC31;">Immediate</span> |    Memory |    2 |           5c | 
| 0x02  | **[LDA](#lda)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> |    Memory |    2 |           8c | 
| 0x03  | **[LDA](#lda)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> |    Memory |    3 |           9c | 
| 0x04  | **[LDB](#ldb)** <span style="color:#E6DC31;">#FD</span>    | <span style="color:#E6DC31;">Immediate</span> |    Memory |    2 |           5c | 
| 0x05  | **[LDB](#ldb)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> |    Memory |    2 |           8c | 
| 0x06  | **[LDB](#ldb)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> |    Memory |    3 |           9c | 
| 0x07  | **[STA](#sta)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> |    Memory |    2 |           8c | 
| 0x08  | **[STA](#sta)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> |    Memory |    3 |           9c | 
| 0x09  | **[NOP](#nop)**                                            |                                       Implied |     Misc. |    1 |           4c | 
| 0x0A  | **[NOP](#nop)**                                            |                                       Implied |     Misc. |    1 |           4c | 
| 0x0B  | **[ADD](#add)** <span style="color:#E6DC31;">#FD</span>    | <span style="color:#E6DC31;">Immediate</span> |     Logic |    2 |           6c | 
| 0x0C  | **[ADD](#add)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> |     Logic |    2 |           9c | 
| 0x0D  | **[ADD](#add)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> |     Logic |    3 |          10c | 
| 0x0E  | **[ADD](#add)** <span style="color:#0797EB;">$FD, A</span> |   <span style="color:#0797EB;">Indexed</span> |     Logic |    2 |           9c | 
| 0x0F  | **[SUB](#sub)** <span style="color:#E6DC31;">#FD</span>    | <span style="color:#E6DC31;">Immediate</span> |     Logic |    2 |           6c | 
| 0x10  | **[SUB](#sub)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> |     Logic |    2 |           9c | 
| 0x11  | **[SUB](#sub)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> |     Logic |    3 |          10c | 
| 0x12  | **[SUB](#sub)** <span style="color:#0797EB;">$FD, A</span> |   <span style="color:#0797EB;">Indexed</span> |     Logic |    2 |           9c | 
| 0x13  | **[NOP](#nop)**                                            |                                       Implied |     Misc. |    1 |           4c | 
| 0x14  | **[CLS](#cls)**                                            |                                       Implied |     Logic |    1 |           3c | 
| 0x15  | **[CMP](#cmp)**                                            |                                       Implied |     Logic |    1 |           3c | 
| 0x16  | **[CMP](#cmp)** <span style="color:#E6DC31;">#FD</span>    | <span style="color:#E6DC31;">Immediate</span> |     Logic |    2 |           6c | 
| 0x17  | **[CMP](#cmp)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> |     Logic |    2 |           9c | 
| 0x18  | **[CMP](#cmp)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> |     Logic |    3 |          10c | 
| 0x19  | **[PHA](#pha)**                                            |                                       Implied |    Memory |    1 |           4c | 
| 0x1A  | **[NOP](#nop)**                                            |                                       Implied |     Misc. |    1 |           4c | 
| 0x1B  | **[PHS](#phs)**                                            |                                       Implied |    Memory |    1 |           4c | 
| 0x1C  | **[PLA](#pla)**                                            |                                       Implied |    Memory |    1 |           5c | 
| 0x1D  | **[PLB](#plb)**                                            |                                       Implied |    Memory |    1 |           5c | 
| 0x1E  | **[PLS](#pls)**                                            |                                       Implied |     Logic |    1 |           5c | 
| 0x1F  | **[NOP](#nop)**                                            |                                       Implied |     Misc. |    1 |           4c | 
| 0x20  | **[JSR](#jsr)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> | Branching |    3 |          11c | 
| 0x21  | **[RTS](#rts)**                                            |                                       Implied | Branching |    1 |          12c | 
| 0x22  | **[JMP](#jpm)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> | Branching |    2 |           7c | 
| 0x23  | **[JMP](#jmp)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> | Branching |    3 |           8c | 
| 0x24  | **[JMP](#jmp)** <span style="color:#0797EB;">$FD, A</span> |   <span style="color:#0797EB;">Indexed</span> | Branching |    2 |           7c | 
| 0x25  | **[BCS](#bcs)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> | Branching |    2 |         4-7c | 
| 0x26  | **[BCS](#bcs)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> | Branching |    3 |         5-8c | 
| 0x27  | **[BCS](#bcs)** <span style="color:#0797EB;">$FD, A</span> |   <span style="color:#0797EB;">Indexed</span> | Branching |    2 |         4-7c | 
| 0x28  | **[BCC](#bcc)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> | Branching |    2 |         4-7c | 
| 0x29  | **[BCC](#bcc)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> | Branching |    3 |         5-8c | 
| 0x2A  | **[BCC](#bcc)** <span style="color:#0797EB;">$FD, A</span> |   <span style="color:#0797EB;">Indexed</span> | Branching |    2 |         4-7c | 
| 0x2B  | **[BNE](#bne)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> | Branching |    2 |         4-7c | 
| 0x2C  | **[BNE](#bne)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> | Branching |    3 |         5-8c | 
| 0x2D  | **[BNE](#bne)** <span style="color:#0797EB;">$FD, A</span> |   <span style="color:#0797EB;">Indexed</span> | Branching |    2 |         4-7c | 
| 0x2E  | **[BEQ](#beq)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> | Branching |    2 |         4-7c | 
| 0x2F  | **[BEQ](#beq)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> | Branching |    3 |         5-8c | 
| 0x30  | **[BEQ](#beq)** <span style="color:#0797EB;">$FD, A</span> |   <span style="color:#0797EB;">Indexed</span> | Branching |    2 |         4-7c | 
| 0x31  | **[BPL](#bpl)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> | Branching |    2 |         4-7c | 
| 0x32  | **[BPL](#bpl)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> | Branching |    3 |         5-8c | 
| 0x33  | **[BPL](#bpl)** <span style="color:#0797EB;">$FD, A</span> |   <span style="color:#0797EB;">Indexed</span> | Branching |    2 |         4-7c | 
| 0x34  | **[BMI](#bmi)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> | Branching |    2 |         4-7c | 
| 0x35  | **[BMI](#bmi)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> | Branching |    3 |         5-8c | 
| 0x36  | **[BMI](#bmi)** <span style="color:#0797EB;">$FD, A</span> |   <span style="color:#0797EB;">Indexed</span> | Branching |    2 |         4-7c | 
| 0x37  | **[BOC](#boc)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> | Branching |    2 |         4-7c | 
| 0x38  | **[BOC](#boc)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> | Branching |    3 |         5-8c | 
| 0x39  | **[BOC](#boc)** <span style="color:#0797EB;">$FD, A</span> |   <span style="color:#0797EB;">Indexed</span> | Branching |    2 |         4-7c | 
| 0x3A  | **[BOS](#bos)** <span style="color:#18E637;">$FD</span>    | <span style="color:#18E637;">Zero Page</span> | Branching |    2 |         4-7c | 
| 0x3B  | **[BOS](#bos)** <span style="color:#EB412C;">$02FD</span>  |  <span style="color:#EB412C;">Absolute</span> | Branching |    3 |         5-8c | 
| 0x3C  | **[BOS](#bos)** <span style="color:#0797EB;">$FD, A</span> |   <span style="color:#0797EB;">Indexed</span> | Branching |    2 |         4-7c | 
| 0x3D  | **[NOP](#nop)**                                            |                                       Implied |     Misc. |    1 |           4c | 
| 0x3E  | **[NOP](#nop)**                                            |                                       Implied |     Misc. |    1 |           4c | 
| 0x3F  | **[HLT](#hlt)**                                            |                                       Implied |     Misc. |    1 |           4c | 

### NOP
Does nothing and passes to the next instruction.

### LDA
Loads a value into the **A Register** from operand or memory depending on [Addressing Mode](#addressing-modes).

### LDB
Loads a value into the **B Register** from operand or memory depending on [Addressing Mode](#addressing-modes).

### STA
Stores the value of the **A Register** in memory

### ADD
Adds a value to the **A Register**, value is determined from the operand depending on [Addressing Mode](#addressing-modes).

Will override the **B Register** with the resolved value to add.

Will update the **Status Register** :
- O : A_8 ^ R_8
- Z : R == 0
- C : R_9 == 1
- N : R_8 == 1

### SUB
Subtracts a value from the **A Register**, value is determined from the operand depending on [Addressing Mode](#addressing-modes).

Will override the **B Register** with the resolved value to subtract.

Will update the **Status Register** :
- O : A_8 ^ R_8
- Z : R == 0
- C : R_9 == 1
- N : R_8 == 1

### CLS
Clears the **Status Register**

### CMP
Compares 2 values, the **A Register** and an operand determined by the [Addressing Mode](#addressing-modes).

Will update the **Status Register** but not the **A Register** :
- O : A_8 ^ R_8
- Z : R == 0
- C : R_9 == 1
- N : R_8 == 1

### PHA
Pushes the content of the **A Register** into the stack.

The stack pointer will be automatically updated.

### PHS
Pushes the content of the **Status Register** into the stack.

The **Stack Pointer** will be automatically updated.

### PLA
Pulls the most recent element from the stack and stores it in to **A Register**

The **Stack Pointer** will be automatically updated.

### PLB
Pulls the most recent element from the stack and stores it in to **B Register**

The **Stack Pointer** will be automatically updated.

### PLS
Pulls the most recent element from the stack and stores it in to **Status Register**

The **Stack Pointer** will be automatically updated.

### JSR
Jumps to a subroutine, and pushes the return address into the stack.

Will take 2 bytes on the stack.

The **Stack Pointer** will be automatically updated.

### RTS
Returns from a subroutine, by pulling the return address into the stack.

Execution will resume at the instruction right after the last ``JSR`` instruction

The **Stack Pointer** will be automatically updated.

### JMP
Jumps to a specified address, determined by the [Addressing Mode](#addressing-modes).

### BCC
Jumps to a specified address, determined by the [Addressing Mode](#addressing-modes) if the **Status Register** has the ``Carry Flag`` cleared : ``Status & 0b0010 == 0b0000``

### BCS
Jumps to a specified address, determined by the [Addressing Mode](#addressing-modes) if the **Status Register** has the ``Carry Flag`` set : ``Status & 0b0010 == 0b0010``

### BNE
Jumps to a specified address, determined by the [Addressing Mode](#addressing-modes) if the **Status Register** has the ``Zero Flag`` cleared : ``Status & 0b0100 == 0b0001``

### BEQ
Jumps to a specified address, determined by the [Addressing Mode](#addressing-modes) if the **Status Register** has the ``Zero Flag`` set : ``Status & 0b0100 == 0b0100``

### BPL
Jumps to a specified address, determined by the [Addressing Mode](#addressing-modes) if the **Status Register** has the ``Negative Flag`` cleared : ``Status & 0b0001 == 0b0000``

### BMI
Jumps to a specified address, determined by the [Addressing Mode](#addressing-modes) if the **Status Register** has the ``Negative Flag`` set : ``Status & 0b0001 == 0b0001``

### BOC
Jumps to a specified address, determined by the [Addressing Mode](#addressing-modes) if the **Status Register** has the ``Overflow Flag`` cleared : ``Status & 0b1000 == 0b0000``

### BOS
Jumps to a specified address, determined by the [Addressing Mode](#addressing-modes) if the **Status Register** has the ``Overflow Flag`` set : ``Status & 0b1000 == 0b1000``

### HLT
Halts the **Clock** until reset

## Memory Blocks

Memory blocks allows you to declare a static block of memory and fill it with arbitrary data.

A Memory block is defined as follows

```
.block $(addr)
    00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F
.endblock
```

- Declaration must start with `.block` and specify the base address
- Size is arbitrary but `addr + size` should not exceed #3FF, in practice higher memory address are allowed, but mirroring will be applied
- A Memory Block can not be mapped to reserved addresses, which are **$0000** and **$0001** that are the **entrypoint of execution**, and **$0300 to $03FF** which is reserved for the **Stack**
- Declaration must end with `.endblock`

## Constants

### Declaration

Constants are the same as a memory block, but only allows for a size of 1 or 2 bytes

A constant if defined as follows

```
.const byte name #FF
.const word name #FFFF
```

Legal types are ``byte`` for 8 bits Constants and ``word`` for 16 bits Constants

- Declaration must start with `.const` followed a type and an alias
- Aliases must start with a letter, and can contain alphanumeric characters, '-' or '\_'
- Value must be specified as hexadecimal and is mandatory

### Usages

See [Addressing Mode](#addressing-modes) examples

## Variable

### Declaration

Variable allows you to abstract a memory address, it will allocate an available address in the address space and use it as a container

A variable is defined as follows

```
.var name #FF
```

- Declaration must start with `.var` followed by an alias
- Aliases must start with a letter, and can contain alphanumeric characters, '-' or '\_'
- Value is specified as Hexadecimal and is optional, if no value is passed it is assumed to be 0

### Usages

See [Addressing Mode](#addressing-modes) examples

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
    ADD #10
    BCC @LOOP
```

This code will loop until the value in the A Register rolls over 0xFF and sets the Carry Flag,
the `BCC` instruction will jump to the address referenced by the `LOOP` label, which in this case is the `ADD #10` instruction
