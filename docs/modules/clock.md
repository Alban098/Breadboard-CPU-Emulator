# 8 bits Breadboard Computer - Clock Module

## Diagram
<img src="schematics/clock.png">

## Description
The 3 LM555CM timers each have a different purpose :
- The first one is in **astable** mode, providing a regular clock signal that is length controllable using the variable resistor.
- The second one is a **monostable** and serve as a filter to debounce the Push Switch's clock signal will manual stepping.
- The last one is a **bistable** and serve the same purpose as the monostable one but for the mode switching switch.

### Signals
| Signal |        Mode |          Binary mask          |                                 Description |
|:-------|------------:|:-----------------------------:|--------------------------------------------:|
| HLT    | Active_HIGH | 1000_0000_0000_0000_0000_0000 |      Prevent any clock cycle from occurring |

### I/O
| Name |   Size |     Type |                         Description |
|:-----|-------:|---------:|------------------------------------:|
| CLK  | 1 bits |      Out |               The main clock signal |
| ~CLK | 1 bits |      Out | The main clock signal, but inverted |


## Parts list
| Part                       | Quantity |
|:---------------------------|---------:|
| Breadboard BB830           |        1 |
| 220Ω Resistor              |        3 |
| 1kΩ Resistor               |        5 |
| 1MΩ Resistor               |        1 |
| 1MΩ Potentiometer          |        1 |
| 0,1µF Capacitor            |        4 |
| 0,01µF Capacitor           |        3 |
| 1µF Capacitor              |        1 |
| Toggle Switch              |        1 |
| Push Button                |        1 |
| LM555CN (Timer)            |        3 |
| CD74HCT04E (8x Inverters)  |        1 |
| CD74HCT08E (4x 2-ANDs)     |        1 |
| CD74HCT32E (2x 4-ORs)      |        1 |
| Green LED                  |        2 |
| Blue LED                   |        1 |
