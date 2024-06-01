# Breadboard CPU Emulator

## Compiler

This CLI will compile an assembly file into a .rom file that can be executed by the emulator and ran on the actual Breadboard computer (WIP)

See [assembly details](/assembly.md) for more details on the Assembly language itself

### Usage
You can compile a file by passing the following parameter to the CLI of the compiler
```
java -jar compiler.jar -f sample.asm -o sample.rom
```
if no ``-o X`` is passed, the binary file will be saved with the same name as the input file with a ``.rom`` extension

## Emulator
The emulator is based on real world breadboard computer inspired by [this project](https://eater.net/8bit) that is not yet finished.
(full schematics will be added to this repo once finished)

To run a binary file, just pass its path as an argument to the emulator

WARNING Not all Instruction have been tested, some of them may be bugged !

## Physical Computer
More details about the Breadboard computer in itself can be found [there](docs/modules.md), along with the full [parts list](docs/parts_list.md)

## Roadmap
* [x] Instruction set
* [ ] Assembly compiler
* [x] Emulator (WIP)
  * [x] Components
  * [x] Signals
  * [x] Instructions
* [ ] Logic and electric diagrams
* [ ] Electronic part ordering (See [part list](/parts_list.md))
* [ ] Physical assembly
  * [ ] Clock module
  * [ ] Program counter
  * [ ] Registers
  * [ ] ALU
  * [ ] Status register
  * [ ] Memory modules
  * [ ] Control Unit
  * [ ] ESP32 based programmer