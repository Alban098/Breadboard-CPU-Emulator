# 8 bits Breadboard Computer - Arithmetic Unit

## Diagram
<img src="schematics/alu.png">

## Description
The 2 SN74LS86AN are used to convert B into it's 2's complement for SUB mode

### Signals
| Signal  |        Mode |          Binary mask          |                                  Description |
|:--------|------------:|:-----------------------------:|---------------------------------------------:|
| ALU_OUT |  Active_LOW | 0000_1000_0000_0000_0000_0000 |          Writes the current value to the BUS |
| SUB     | Active_HIGH | 0001_0000_0000_0000_0000_0000 |  Subtract B from A, result is signed integer |

### I/O
| Name  |   Size |     Type |                                          Description |
|:------|-------:|---------:|-----------------------------------------------------:|
| BUS   | 8 bits | In / Out |                         Direct connection to the BUS |
| A     | 8 bits |       In | Direct input from the A Register, before transceiver |
| B     | 8 bits |       In |                     Direct input from the B Register |
| ALU   | 8 bits |      Out |            Output of the ALU, before the Transceiver |
| Carry | 1 bits |      Out |   High if a carry has rippled out of the 8 bit range |

## Parts list
| Part                                 | Quantity | Unit Price | 
|:-------------------------------------|---------:|-----------:|
| Breadboard BB830                     |        1 |     8,12 € |
| 220Ω Resistor                        |        8 |     0,02 € |
| 0,1µF Capacitor                      |        5 |     0,37 € |
| CD74HCT86E  (4x 2-XORs)              |        2 |     0,81 € |
| CD74HCT245E (8 bits Bus Transceiver) |        1 |     0,80 € |
| CD74HCT283E (4 bits Full Adder)      |        2 |     0,90 € |
| Red LED                              |        8 |     0,12 € |
