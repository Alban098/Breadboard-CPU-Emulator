# 8 bits Breadboard Computer - Control Unit

## Diagram
<img src="schematics/control_unit.png">

## Description
Internally, each instruction has a certain number of micro-steps, this is handled by a 4bits counter that will keep track of the current micro-step.
Each instruction should therefore has a last micro-step that is responsible for resetting the counter.

The 2 EEPROMs are used as combinational logic substitutes, the address is of the form
```
Step(4bit)_Flags(4bit)_opcode(6bit)
```
The RST signal is controlled by a Push Switch on this module
The CLK signal will be used to increase the Step Counter

A subset of signals is inverted, this is due to the fact that in their respective modules, they are **Active_LOW**, the inverting logic in handle centrally instead of having a chip on each module

### Signals
| Signal |        Mode |     Binary mask     |           Description |
|:-------|------------:|:-------------------:|----------------------:|
| CLK    | Active_HIGH |          X          | The main clock signal |

### I/O
| Name |   Size | Type |                                              Description |
|:-----|-------:|-----:|---------------------------------------------------------:|
| IR   | 6 bits |   In | The 6 least significant bits of the Instruction Register |
| *_F  | 4 bits |   In |      The 4 least significant bits of the Status Register |

## Parts list

| Part                                    | Quantity | Unit Price |   Total |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      Link |
|:----------------------------------------|---------:|-----------:|--------:|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| Breadboard BB830                        |        2 |     8,12 € |  8,12 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              [Link](https://www.mouser.fr/ProductDetail/BusBoard-Prototype-Systems/BB830?qs=VEfmQw3KOauhPeTwYxNCaA%3D%3D) |
| 220Ω Resistor                           |       20 |     0,02 € |  0,40 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      [Link](https://www.mouser.fr/ProductDetail/YAGEO/CFR-25JT-52-220R?qs=KUIzHt%2Fe91lrctWTReofaw%3D%3D) |
| 0,1µF Capacitor                         |        2 |     0,37 € |  0,74 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             [Link](https://www.mouser.fr/ProductDetail/Vishay-BC-Components/K104K15X7RF53H5G?qs=sGAEpiMZZMsh%252B1woXyUXj30ZYomYlxpXf%2Fk4SX%252BaKhs%3D) |
| SN74LS00N (4x 2-NANDs)                  |        1 |     0,61 € |  0,61 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               [Link](https://www.mouser.lu/ProductDetail/Texas-Instruments/SN74LS00N?qs=spW5eSrOWB6G5wECF%252BEZFA%3D%3D) |
| SN74LS04N (8x Inverters)                |        2 |     0,62 € |  1,22 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   [Link](https://www.mouser.lu/ProductDetail/Texas-Instruments/SN74LS04N?qs=spW5eSrOWB4DgGWIHPxzvg%3D%3D) |
| SN74LS161AN (4 bits Counter)            |        1 |     0,84 € |  0,84 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               [Link](https://www.mouser.lu/ProductDetail/Texas-Instruments/SN74LS161AN?qs=pt%2FIv5r0EPdAOdsihx36Qg%3D%3D) |
| 28C256                                  |        2 |    10,81 € | 21,62 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        [Link](https://www.mouser.fr/ProductDetail/Microchip-Technology/AT28C256-15PU?qs=MAR%2F2X5XOp7eAU2%2FlNw9oA%3D%3D) |
| Yellow LED                              |        4 |     0,23 € |  0,92 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           [Link](https://www.mouser.lu/ProductDetail/Kingbright/WP7113YT?qs=YPg7lQ8MWSfGn6TgDjFnaQ%3D%3D) |
| Blue LED                                |       20 |     0,40 € |  8,00 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        [Link](https://www.mouser.lu/ProductDetail/Kingbright/WP7083QBD-G?qs=vEWui5pQpatbKK552qzGvw%3D%3D) |
| Push Button                             |        1 |     0,20 € |   0,2 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       [Link](https://www.mouser.lu/ProductDetail/E-Switch/TL1105PF160Q?qs=iWbXB9lueyCtFh0h%2FBaqIg%3D%3D) |