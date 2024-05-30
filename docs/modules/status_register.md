# 8 bits Breadboard Computer - Status Register

## Diagram
<img src="schematics/status_register.png">

## Description
The status register is responsible for capturing the state of the ALU's flags after certain instructions.
#### Overflow
If the result has changed sign, this flag is only relevant if you consider your values as signed integers.

This flag will be set if the 8th bit of the A Register is different from the 8th bit of the result, we can compute is as follows ``(A_8 ^ R_8) != 0``

#### Zero 
If the result of the computation is 0.

This flag is set when all bits of the Result are 0, we can compute is as follows ``(~(R_0 | R_1) & ~(R_2 | R_3)) &(~(R_4 | R_5) & ~(R_6 | R_7))``
#### Carry 
If the previous operation has produced a carry to ripple out of the 8 bit range.

This flag is simply captured from the ALU's output **CARRY** signal

#### Negative 
If the most significant bit of the result is set to 1, meaning a negative number if in signed mode .

This flag is simply captured as the 8th bit of the result 

### Signals
| Signal   |        Mode |     Binary mask     |                                          Description |
|:---------|------------:|:-------------------:|-----------------------------------------------------:|
| SR_LATCH |  Active_LOW | 0000_0000_1000_0000 | Will capture the state of the flags at the next @CLK |
| RST      | Active_HIGH |          X          |                      Reset the register's value to 0 |
| CLK      | Active_HIGH |          X          |                                The main clock signal |

### I/O
| Name  |   Size | Type |                                            Description |
|:------|-------:|-----:|-------------------------------------------------------:|
| ALU   | 8 bits |   In |              Output of the ALU, before the transceiver |
| A_8   |  1 bit |   In |  The 8th bit of the A Register, before the transceiver |
| CARRY |  1 bit |   In |                              The CARRY flag of the ALU |
| *_F   |  4 bit |  Out |           The 4 least significant bits of the Register |

## Parts list

| Part                                   | Quantity | Unit Price |  Total |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           Link |
|:---------------------------------------|---------:|-----------:|-------:|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| Breadboard BB830                       |        1 |     8,12 € | 8,12 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   [Link](https://www.mouser.fr/ProductDetail/BusBoard-Prototype-Systems/BB830?qs=VEfmQw3KOauhPeTwYxNCaA%3D%3D) |
| 220Ω Resistor                          |        4 |     0,02 € |  0,8 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           [Link](https://www.mouser.fr/ProductDetail/YAGEO/CFR-25JT-52-220R?qs=KUIzHt%2Fe91lrctWTReofaw%3D%3D) |
| 0,1µF Capacitor                        |        1 |     0,37 € | 0,37 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  [Link](https://www.mouser.fr/ProductDetail/Vishay-BC-Components/K104K15X7RF53H5G?qs=sGAEpiMZZMsh%252B1woXyUXj30ZYomYlxpXf%2Fk4SX%252BaKhs%3D) |
| SN74LS173AN (4 bits Register)          |        1 |     1,66 € | 1,66 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      [Link](https://www.mouser.lu/ProductDetail/Texas-Instruments/SN74LS173AN?qs=nMmhAzRCgdAkY4Cck6ihbQ%3D%3D) |
| SN74LS02N (4x 2-NORs)                  |        1 |     0,51 € | 0,51 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    [Link](https://www.mouser.lu/ProductDetail/Texas-Instruments/SN74LS02N?qs=spW5eSrOWB4KM%252BpAgRke5g%3D%3D) |
| SN74LS08N (4x 2-ANDs)                  |        1 |     0,72 € | 0,72 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        [Link](https://www.mouser.lu/ProductDetail/Texas-Instruments/SN74LS08N?qs=spW5eSrOWB4y5MjcKVGhlA%3D%3D) |
| SN74LS86AN (4x 2-XORs)                 |        1 |     0,62 € | 0,62 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     [Link](https://www.mouser.lu/ProductDetail/Texas-Instruments/SN74LS86AN?qs=mTHRaKC2c7P%2FJtp1i2FCFw%3D%3D) |
| Red LED                                |        4 |     0,12 € | 0,96 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             [Link](https://www.mouser.lu/ProductDetail/Lumex/SSL-LX5093IT?qs=z5hCOXTvo57cxO7p%252BvLJIw%3D%3D) |
