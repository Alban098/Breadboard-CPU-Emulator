# Breadboard CPU Emulator
<img src="img/img_0.png">

## Compiler

This CLI will compile an assembly file into a .rom file that can be executed by the emulator and ran on the actual Breadboard computer (WIP).

See [assembly details](docs/assembly.md) for more details on the Assembly language itself.

### Usage
You can compile a file by passing the following parameter to the CLI of the compiler.
```
java -jar compiler.jar -f sample.asm -o sample.rom
```
if no ``-o X`` is passed, the binary file will be saved with the same name as the input file with a ``.rom`` extension.

## Emulator
The emulator is based on real world breadboard computer inspired by [this project](https://eater.net/8bit) that is not yet finished.

To run a binary file, just pass its path as an argument to the emulator.

### ROM & Instructions dumps
To facilitate physical assembly and proper emulation from instruction opcode down to signal indices in the control words,
the emulator has the ability to dump the content of it's program ROM, both in ["humanly readable format"](dump/ROM_dump.md) and as the binary blob that should be written to the 3 EEPROM that will be located in the **Control Unit** :
- [Dump 0](dump/ROM_dump.bin0) for the 8 least bits of the **Control Word**
- [Dump 1](dump/ROM_dump.bin1) for the 8 middle bits of the **Control Word**
- [Dump 2](dump/ROM_dump.bin2) for the 8 most significant bits of the **Control Word**

The emulator can also dump the [current instruction set](dump/instruction_set.md).

## Physical Computer
More details about the Breadboard computer in itself can be found [there](docs/modules.md), along with the full [parts list](docs/parts_list.md)

### EEPROM Programmer
That build uses some 28C series EEPROM to emulate large combinatorial logic, those will need to be programmed

I used that **EEPROM Programmer** : https://tomnisbet.github.io/TommyPROM/docs/hardware
If like me, you used an **Arduino Nano Every** instead of a **Nano V3** for the **EEPROM Programmer**, that Programmer will need some rework to actually work as intended (see [this repo](https://github.com/Alban098/TommyPROM-ATMEGA-4809) for the adapted code and wiring diagram)

## Roadmap
* [x] Instruction set
* [x] Assembly compiler
* [x] Emulator
  * [x] Components
  * [x] Signals
  * [x] Instructions
* [x] Documentation
* [x] Electrical diagrams
* [x] Electronic components ordering
* [ ] Physical assembly
  * [x] Clock module
  * [x] Program counter
  * [x] Registers
  * [x] ALU
  * [x] Status register
  * [x] Memory modules
  * [ ] Control Unit
  * [x] [Arduino Programmer](docs/programmer.md)

## Images
<img src="img/img_1.png">
<img src="img/img_0.png">
<img src="img/img_2.png">