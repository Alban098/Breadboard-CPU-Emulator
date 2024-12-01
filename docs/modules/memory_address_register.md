# 8 bits Breadboard Computer - Memory Address Register

## Diagram
<img src="schematics/memory_address_register.png">

## Description
This register holds the pointer of the current RAM address, it also controls the **Manual Programming Mode** switch

### Signals
| Signal    |        Mode |          Binary mask          |                                   Description |
|:----------|------------:|:-----------------------------:|----------------------------------------------:|
| MAR_IN_16 |  Active_LOW | 0000_0000_0000_0000_0001_0000 | Will capture the BUS's value at the next @CLK |
| RST       | Active_HIGH |               X               |               Reset the register's value to 0 |
| CLK       | Active_HIGH |               X               |                         The main clock signal |

### I/O
| Name |    Size | Type |                                         Description |
|:-----|--------:|-----:|----------------------------------------------------:|
| BUS  | 16 bits |   In |                        Direct connection to the BUS |
| MR   | 16 bits |  Out |                              Output of the register |
| PROG |  1 bits |  Out |  Will set the module in **Manual Programming Mode** |

## Parts list
| Part                                   | Quantity |
|:---------------------------------------|---------:|
| Breadboard BB830                       |        2 |
| 220Ω Resistor                          |       16 |
| 1kΩ Resistor                           |        2 |
| 10kΩ Resistor                          |       16 |
| 0,1µF Capacitor                        |        8 |
| CD74HCT173E (4 bits Register)          |        4 |
| CD74HCT157E (2 lines to 1 bit Encoder) |        4 |
| Yellow LED                             |        1 |
| Green LED                              |        1 |
| Red LED                                |       16 |
| 8 bits DIP switch                      |        2 |
| Toggle Switch                          |        1 |
