# 8 bits Breadboard Computer - B Register

## Diagram
<img src="schematics/B_register.png">

## Description
This register has no bus output because of technical limitations (signal count must be kept under 16)

### Signals
| Signal |        Mode |     Binary mask     |                                   Description |
|:-------|------------:|:-------------------:|----------------------------------------------:|
| B_IN   |  Active_LOW | 0000_1000_0000_0000 | Will capture the BUS's value at the next @CLK |
| RST    | Active_HIGH |          X          |               Reset the register's value to 0 |
| CLK    | Active_HIGH |          X          |                         The main clock signal |

### I/O
| Name |   Size |     Type |                  Description |
|:-----|-------:|---------:|-----------------------------:|
| BUS  | 8 bits |       In | Direct connection to the BUS |
| B    | 8 bits |      Out |       Output of the register |

## Parts list

| Part                                    | Quantity | Unit Price |  Total |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       Link |
|:----------------------------------------|---------:|-----------:|-------:|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| Breadboard BB830                        |        1 |     8,12 € | 8,12 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               [Link](https://www.mouser.fr/ProductDetail/BusBoard-Prototype-Systems/BB830?qs=VEfmQw3KOauhPeTwYxNCaA%3D%3D) |
| 220Ω Resistor                           |        8 |     0,02 € | 0,16 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       [Link](https://www.mouser.fr/ProductDetail/YAGEO/CFR-25JT-52-220R?qs=KUIzHt%2Fe91lrctWTReofaw%3D%3D) |
| 0,1µF Capacitor                         |        1 |     0,37 € | 0,37 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              [Link](https://www.mouser.fr/ProductDetail/Vishay-BC-Components/K104K15X7RF53H5G?qs=sGAEpiMZZMsh%252B1woXyUXj30ZYomYlxpXf%2Fk4SX%252BaKhs%3D) |
| SN74LS173AN (4 bits Register)           |        2 |     1,66 € | 3,32 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  [Link](https://www.mouser.lu/ProductDetail/Texas-Instruments/SN74LS173AN?qs=nMmhAzRCgdAkY4Cck6ihbQ%3D%3D) |
| Red LED                                 |        8 |     0,12 € | 0,96 € |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         [Link](https://www.mouser.lu/ProductDetail/Lumex/SSL-LX5093IT?qs=z5hCOXTvo57cxO7p%252BvLJIw%3D%3D) |
