# 8 bits Breadboard Computer - Stack Pointer

## Diagram
<img src="schematics/stack_pointer.png">

## Description
To increment and decrement without using signed integers, we can use the ``Carry In`` to add 1 if the corresponding signal is set, and add #FF to cause an overflow to simulate a subtraction by 1
On overflow during will trigger the ``HTL`` signal

### Signals
| Signal       |          Mode |          Binary mask          |                                            Description |
|:-------------|--------------:|:-----------------------------:|-------------------------------------------------------:|
| STACK_PUSH   |   Active_HIGH | 0000_0001_0000_0000_0000_0000 |              Will increment the value on the next @CLK |
| STACK_POP    |   Active_HIGH | 0000_0010_0000_0000_0000_0000 |              Will decrement the value on the next @CLK |
| STACK_OUT_16 |    Active_LOW | 0000_0100_0000_0000_0000_0000 | Writes the current value to the BUS prepended with #FF |
| RST          |   Active_HIGH |               X               |                    Reset the register's value to #FFFF |
| CLK          |   Active_HIGH |               X               |                                  The main clock signal |

### I/O
| Name |    Size |     Type |                                    Description |
|:-----|--------:|---------:|-----------------------------------------------:|
| BUS  | 16 bits | In / Out |                   Direct connection to the BUS |

## Parts list
| Part                                 | Quantity | Unit Price |
|:-------------------------------------|---------:|-----------:|
| Breadboard BB830                     |        3 |     8,12 € |
| 220Ω Resistor                        |        8 |     0,02 € |
| 0,1µF Capacitor                      |       13 |     0,37 € |
| CD74HCT173E (4 bits Register)        |        2 |     0,73 € | 
| CD74HCT245E (8 bits Bus Transceiver) |        3 |     0,80 € |
| CD74HCT283E (4 bits Full Adder)      |        2 |     0,90 € |
| CD74HCT00E (4x 2-NANDs)              |        1 |     0,57 € |
| CD74HCT08E (4x 2-ANDs)               |        2 |     0,55 € |
| CD74HCT30E (1x 8-NANDs)              |        1 |     0,47 € |
| CD74HCT32E (4x 2-ORs)                |        2 |     0,47 € |
| Red LED                              |        8 |     0,12 € |
