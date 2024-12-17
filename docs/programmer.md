# 8 bits Breadboard Computer - Arduino Programmer

Having **64 KB of addressable space**, manually programming that computer through the **DIP switches everytime it is powered up** became unrealistic.
So the need for an automated way to upload programs became essential.

I was short on parts, so I had the idea of repurposing the **EEPROM programmer** used for the **Control Unit and the Output Register**. to also be able to program this Computer.

To achieve that, the simples way was to add 2 connection ports right next to the DIP switches, and to connect the **address and data pins** of the EEPROM Programmer, and hard-wiring the **Program Switch** to the **Write signal** of the EEPROM Programmer
This enables us to send binary files directly though **XMODEM protocols** using the serial port of the Arduino.

Doing it in that way introduced 2 limitations :
- First limitation of that approach is that we need to manually switch the Computer into programming mode for the process to actually succeed, 
- Second limitation is that we can't leverage the read capabilities of the EEPROM programmer as the DIP switch where we connected it are isolated from the RAM module output

Implementation details can be found [here](https://github.com/Alban098/TommyPROM-ATMEGA-4809)

<img src="programmer.png" />