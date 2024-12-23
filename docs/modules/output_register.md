# 8 bits Breadboard Computer - Output Register

## Diagram
<img src="schematics/output_register.png">

## Description
This register will display its value as a decimal value that can be signed or not.

It will drive 4 7 segments display using the same data lines and one EEPROM. this is managed by rapidly switching on and off each display and relying on persistence of vision.

To do so we use a 555 Timer to get a consistent clock signal, and use 2 JK-Flip-Flop to simulate a 2 bit binary counter, this value is used as the 2 MSB of the EEPROM Address to differentiate each digit of a number.
We also use a 2bit to 4 lines selector to activate and deactivate each display one at a time.
This will have the effect of making each display show a different number, creating the illusion of driving all the 7 segment displays at the same time with a quarter of the data lines 

A Switch can control if the value is displayed as signed or not.

! Don't forget to tie signals of the additional flip-flop of the 74HCT107 to GND to avoid potential interference in the display !

Find [here](../../dump/output_register_eeprom_dump.bin) the EEPROM content associated with this wiring

### Signals
| Signal |        Mode |          Binary mask          |                                   Description |
|:-------|------------:|:-----------------------------:|----------------------------------------------:|
| OUT_IN | Active_HIGH | 0010_0000_0000_0000_0000_0000 | Will capture the BUS's value at the next @CLK |
| RST    |  Active_LOW |               X               |               Reset the register's value to 0 |
| CLK    | Active_HIGH |               X               |                         The main clock signal |

### I/O
| Name |   Size |     Type |                  Description |
|:-----|-------:|---------:|-----------------------------:|
| BUS  | 8 bits |       In | Direct connection to the BUS |

## Parts list
| Part                                    | Quantity |
|:----------------------------------------|---------:|
| Breadboard BB830                        |        2 |
| 220Ω Resistor                           |        8 |
| 1kΩ Resistor                            |        2 |
| 100kΩ Resistor                          |        1 |
| 0,01µF Capacitor                        |        2 |
| 0,1µF Capacitor                         |        4 |
| LM555CN (Timer)                         |        1 |
| CD74HCT08E (4x 2-ANDs)                  |        1 |
| CD74HCT107E (Dual JK-Flip-Flops)        |        1 |
| CD74HCT139E (2 bits to 4 lines Decoder) |        1 |
| CD74HCT273E (8x D-Flip-Flops)           |        1 |
| AT28C64B-15PU                           |        1 |
| Red LED                                 |        8 |
| Toggle Switch                           |        1 |
| 7 Segments Display                      |        4 |
