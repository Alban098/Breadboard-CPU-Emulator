## Guidelines
This assembly language has some global restrictions :
* It only supports 8 bits values
* The largest address is 0xFF = 255
* The size after compilation must not exceed 0xFF = 256 bytes

## Memory Blocks
Memory block allows you to declare a static block of memory and fill it with arbitrary data.
A Memory block is defined as follows
```
.block $(addr)
    00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F
.endblock
```
* Declaration must start with ``.block`` and specify the base address
* Size is arbitrary but ``addr + size`` must not exceed 255
* Declaration must end with ``.endblock``


## Constants
### Declaration
Constant are the same as a memory block, but only allows for a size of 1 byte

A constant if defined as follows
```
.const name FF
```
* Declaration must start with ``.const`` followed by its name
* Name must start with a letter, and can contain alphanumeric character or '_'
* Value is specified as Hexadecimal and mandatory
* 
### Usages
Constant have 2 use cases.

Assuming the following declaration ```.const A 10``` we have 2 possible use cases :
* ``ADD _A`` represent direct addressing, this will be compiled as ``ADD 10`` and add 10 to the A Register
* ``ADD $_A`` represent indirect addressing, this will be compiled as ``ADD $(10)`` and add the value at address $10 to the A Register


## Variable
### Declaration
Variable allows you to abstract a memory address, it will allocate an available address in the address space and use it as a container

A variable is defined as follows
```
.var name FF
```
* Declaration must start with ``.var`` followed by its name
* Name must start with a letter, and can contain alphanumeric character or '_'
* Value is specified as Hexadecimal and is optional, if no value is passed it is assumed to be 0

### Usages
Variable have 2 use cases.

Assuming the following declaration ```.var B FF``` with an assigned address of ``$F0`` we have 2 possible use cases :
* ``STA *B`` represent direct addressing, this will be compiled as ``STA F0`` and store the content of the A Register at address ``F0``
* ``LDA $B`` represent indirect addressing, this will be compiled as ``LDA $(F0)`` and load value FF into the A Register


## Labels
### Declaration
Labels are just memory address alias, they are placed one line before an instruction and will reference the address of that instruction
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
This code will loop until the value in the A Register rolls aver 0xFF and sets the Carry Flag,
the ``BCC`` instruction will jump to the address referenced by the ``LOOP`` label, which in this case is the ``ADD 10`` instruction


## Instructions
