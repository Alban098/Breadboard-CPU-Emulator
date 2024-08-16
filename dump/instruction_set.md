|    Hex|  Assembly| Addressing Mode|   Size| Clock cycles|
|:-----:|:---------|---------------:|------:|------------:|
| 0x00 | NOP  | IMP | 1 |   4c |
| 0x01 | LDA #FD | IMM | 2 |   5c |
| 0x02 | LDA $FD | Z_P | 2 |   8c |
| 0x03 | LDA $00FD | ABS | 3 |   9c |
| 0x04 | LDB #FD | IMM | 2 |   5c |
| 0x05 | LDB $FD | Z_P | 2 |   8c |
| 0x06 | LDB $00FD | ABS | 3 |   9c |
| 0x07 | STA $FD | Z_P | 2 |   8c |
| 0x08 | STA $00FD | ABS | 3 |   9c |
| 0x09 | NOP  | IMP | 1 |   4c |
| 0x0A | NOP  | IMP | 1 |   4c |
| 0x0B | ADD #FD | IMM | 2 |   6c |
| 0x0C | ADD $FD | Z_P | 2 |   9c |
| 0x0D | ADD $00FD | ABS | 3 |   10c |
| 0x0E | ADD $FD, A | IDX | 2 |   9c |
| 0x0F | SUB #FD | IMM | 2 |   6c |
| 0x10 | SUB $FD | Z_P | 2 |   9c |
| 0x11 | SUB $00FD | ABS | 3 |   10c |
| 0x12 | SUB $FD, A | IDX | 2 |   9c |
| 0x13 | NOP  | IMP | 1 |   4c |
| 0x14 | CLS  | IMP | 1 |   3c |
| 0x15 | CMP  | IMP | 1 |   3c |
| 0x16 | CMP #FD | IMM | 2 |   6c |
| 0x17 | CMP $FD | Z_P | 2 |   9c |
| 0x18 | CMP $00FD | ABS | 3 |   10c |
| 0x19 | PHA  | IMP | 1 |   4c |
| 0x1A | NOP  | IMP | 1 |   4c |
| 0x1B | PHS  | IMP | 1 |   4c |
| 0x1C | PLA  | IMP | 1 |   5c |
| 0x1D | PLB  | IMP | 1 |   5c |
| 0x1E | PLS  | IMP | 1 |   5c |
| 0x1F | NOP  | IMP | 1 |   4c |
| 0x20 | JSR $00FD | ABS | 3 |   11c |
| 0x21 | RTS  | IMP | 1 |   12c |
| 0x22 | JMP $FD | Z_P | 2 |   7c |
| 0x23 | JMP $00FD | ABS | 3 |   8c |
| 0x24 | JMP $FD, A | IDX | 2 |   7c |
| 0x25 | BCS $FD | Z_P | 2 | 4-7c |
| 0x26 | BCS $00FD | ABS | 3 | 5-8c |
| 0x27 | BCS $FD, A | IDX | 2 | 4-7c |
| 0x28 | BCC $FD | Z_P | 2 | 4-7c |
| 0x29 | BCC $00FD | ABS | 3 | 5-8c |
| 0x2A | BCC $FD, A | IDX | 2 | 4-7c |
| 0x2B | BNE $FD | Z_P | 2 | 4-7c |
| 0x2C | BNE $00FD | ABS | 3 | 5-8c |
| 0x2D | BNE $FD, A | IDX | 2 | 4-7c |
| 0x2E | BEQ $FD | Z_P | 2 | 4-7c |
| 0x2F | BEQ $00FD | ABS | 3 | 5-8c |
| 0x30 | BEQ $FD, A | IDX | 2 | 4-7c |
| 0x31 | BPL $FD | Z_P | 2 | 4-7c |
| 0x32 | BPL $00FD | ABS | 3 | 5-8c |
| 0x33 | BPL $FD, A | IDX | 2 | 4-7c |
| 0x34 | BMI $FD | Z_P | 2 | 4-7c |
| 0x35 | BMI $00FD | ABS | 3 | 5-8c |
| 0x36 | BMI $FD, A | IDX | 2 | 4-7c |
| 0x37 | BOC $FD | Z_P | 2 | 4-7c |
| 0x38 | BOC $00FD | ABS | 3 | 5-8c |
| 0x39 | BOC $FD, A | IDX | 2 | 4-7c |
| 0x3A | BOS $FD | Z_P | 2 | 4-7c |
| 0x3B | BOS $00FD | ABS | 3 | 5-8c |
| 0x3C | BOS $FD, A | IDX | 2 | 4-7c |
| 0x3D | NOP  | IMP | 1 |   4c |
| 0x3E | NOP  | IMP | 1 |   4c |
| 0x3F | HLT  | IMP | 1 |   4c |
| 0x40 | NOP  | IMP | 1 |   4c |
| 0x41 | NOP  | IMP | 1 |   4c |
| 0x42 | NOP  | IMP | 1 |   4c |
| 0x43 | NOP  | IMP | 1 |   4c |
| 0x44 | NOP  | IMP | 1 |   4c |
| 0x45 | NOP  | IMP | 1 |   4c |
| 0x46 | NOP  | IMP | 1 |   4c |
| 0x47 | NOP  | IMP | 1 |   4c |
| 0x48 | NOP  | IMP | 1 |   4c |
| 0x49 | NOP  | IMP | 1 |   4c |
| 0x4A | NOP  | IMP | 1 |   4c |
| 0x4B | NOP  | IMP | 1 |   4c |
| 0x4C | NOP  | IMP | 1 |   4c |
| 0x4D | NOP  | IMP | 1 |   4c |
| 0x4E | NOP  | IMP | 1 |   4c |
| 0x4F | NOP  | IMP | 1 |   4c |
| 0x50 | NOP  | IMP | 1 |   4c |
| 0x51 | NOP  | IMP | 1 |   4c |
| 0x52 | NOP  | IMP | 1 |   4c |
| 0x53 | NOP  | IMP | 1 |   4c |
| 0x54 | NOP  | IMP | 1 |   4c |
| 0x55 | NOP  | IMP | 1 |   4c |
| 0x56 | NOP  | IMP | 1 |   4c |
| 0x57 | NOP  | IMP | 1 |   4c |
| 0x58 | NOP  | IMP | 1 |   4c |
| 0x59 | NOP  | IMP | 1 |   4c |
| 0x5A | NOP  | IMP | 1 |   4c |
| 0x5B | NOP  | IMP | 1 |   4c |
| 0x5C | NOP  | IMP | 1 |   4c |
| 0x5D | NOP  | IMP | 1 |   4c |
| 0x5E | NOP  | IMP | 1 |   4c |
| 0x5F | NOP  | IMP | 1 |   4c |
| 0x60 | NOP  | IMP | 1 |   4c |
| 0x61 | NOP  | IMP | 1 |   4c |
| 0x62 | NOP  | IMP | 1 |   4c |
| 0x63 | NOP  | IMP | 1 |   4c |
| 0x64 | NOP  | IMP | 1 |   4c |
| 0x65 | NOP  | IMP | 1 |   4c |
| 0x66 | NOP  | IMP | 1 |   4c |
| 0x67 | NOP  | IMP | 1 |   4c |
| 0x68 | NOP  | IMP | 1 |   4c |
| 0x69 | NOP  | IMP | 1 |   4c |
| 0x6A | NOP  | IMP | 1 |   4c |
| 0x6B | NOP  | IMP | 1 |   4c |
| 0x6C | NOP  | IMP | 1 |   4c |
| 0x6D | NOP  | IMP | 1 |   4c |
| 0x6E | NOP  | IMP | 1 |   4c |
| 0x6F | NOP  | IMP | 1 |   4c |
| 0x70 | NOP  | IMP | 1 |   4c |
| 0x71 | NOP  | IMP | 1 |   4c |
| 0x72 | NOP  | IMP | 1 |   4c |
| 0x73 | NOP  | IMP | 1 |   4c |
| 0x74 | NOP  | IMP | 1 |   4c |
| 0x75 | NOP  | IMP | 1 |   4c |
| 0x76 | NOP  | IMP | 1 |   4c |
| 0x77 | NOP  | IMP | 1 |   4c |
| 0x78 | NOP  | IMP | 1 |   4c |
| 0x79 | NOP  | IMP | 1 |   4c |
| 0x7A | NOP  | IMP | 1 |   4c |
| 0x7B | NOP  | IMP | 1 |   4c |
| 0x7C | NOP  | IMP | 1 |   4c |
| 0x7D | NOP  | IMP | 1 |   4c |
| 0x7E | NOP  | IMP | 1 |   4c |
| 0x7F | NOP  | IMP | 1 |   4c |
| 0x80 | NOP  | IMP | 1 |   4c |
| 0x81 | NOP  | IMP | 1 |   4c |
| 0x82 | NOP  | IMP | 1 |   4c |
| 0x83 | NOP  | IMP | 1 |   4c |
| 0x84 | NOP  | IMP | 1 |   4c |
| 0x85 | NOP  | IMP | 1 |   4c |
| 0x86 | NOP  | IMP | 1 |   4c |
| 0x87 | NOP  | IMP | 1 |   4c |
| 0x88 | NOP  | IMP | 1 |   4c |
| 0x89 | NOP  | IMP | 1 |   4c |
| 0x8A | NOP  | IMP | 1 |   4c |
| 0x8B | NOP  | IMP | 1 |   4c |
| 0x8C | NOP  | IMP | 1 |   4c |
| 0x8D | NOP  | IMP | 1 |   4c |
| 0x8E | NOP  | IMP | 1 |   4c |
| 0x8F | NOP  | IMP | 1 |   4c |
| 0x90 | NOP  | IMP | 1 |   4c |
| 0x91 | NOP  | IMP | 1 |   4c |
| 0x92 | NOP  | IMP | 1 |   4c |
| 0x93 | NOP  | IMP | 1 |   4c |
| 0x94 | NOP  | IMP | 1 |   4c |
| 0x95 | NOP  | IMP | 1 |   4c |
| 0x96 | NOP  | IMP | 1 |   4c |
| 0x97 | NOP  | IMP | 1 |   4c |
| 0x98 | NOP  | IMP | 1 |   4c |
| 0x99 | NOP  | IMP | 1 |   4c |
| 0x9A | NOP  | IMP | 1 |   4c |
| 0x9B | NOP  | IMP | 1 |   4c |
| 0x9C | NOP  | IMP | 1 |   4c |
| 0x9D | NOP  | IMP | 1 |   4c |
| 0x9E | NOP  | IMP | 1 |   4c |
| 0x9F | NOP  | IMP | 1 |   4c |
| 0xA0 | NOP  | IMP | 1 |   4c |
| 0xA1 | NOP  | IMP | 1 |   4c |
| 0xA2 | NOP  | IMP | 1 |   4c |
| 0xA3 | NOP  | IMP | 1 |   4c |
| 0xA4 | NOP  | IMP | 1 |   4c |
| 0xA5 | NOP  | IMP | 1 |   4c |
| 0xA6 | NOP  | IMP | 1 |   4c |
| 0xA7 | NOP  | IMP | 1 |   4c |
| 0xA8 | NOP  | IMP | 1 |   4c |
| 0xA9 | NOP  | IMP | 1 |   4c |
| 0xAA | NOP  | IMP | 1 |   4c |
| 0xAB | NOP  | IMP | 1 |   4c |
| 0xAC | NOP  | IMP | 1 |   4c |
| 0xAD | NOP  | IMP | 1 |   4c |
| 0xAE | NOP  | IMP | 1 |   4c |
| 0xAF | NOP  | IMP | 1 |   4c |
| 0xB0 | NOP  | IMP | 1 |   4c |
| 0xB1 | NOP  | IMP | 1 |   4c |
| 0xB2 | NOP  | IMP | 1 |   4c |
| 0xB3 | NOP  | IMP | 1 |   4c |
| 0xB4 | NOP  | IMP | 1 |   4c |
| 0xB5 | NOP  | IMP | 1 |   4c |
| 0xB6 | NOP  | IMP | 1 |   4c |
| 0xB7 | NOP  | IMP | 1 |   4c |
| 0xB8 | NOP  | IMP | 1 |   4c |
| 0xB9 | NOP  | IMP | 1 |   4c |
| 0xBA | NOP  | IMP | 1 |   4c |
| 0xBB | NOP  | IMP | 1 |   4c |
| 0xBC | NOP  | IMP | 1 |   4c |
| 0xBD | NOP  | IMP | 1 |   4c |
| 0xBE | NOP  | IMP | 1 |   4c |
| 0xBF | NOP  | IMP | 1 |   4c |
| 0xC0 | NOP  | IMP | 1 |   4c |
| 0xC1 | NOP  | IMP | 1 |   4c |
| 0xC2 | NOP  | IMP | 1 |   4c |
| 0xC3 | NOP  | IMP | 1 |   4c |
| 0xC4 | NOP  | IMP | 1 |   4c |
| 0xC5 | NOP  | IMP | 1 |   4c |
| 0xC6 | NOP  | IMP | 1 |   4c |
| 0xC7 | NOP  | IMP | 1 |   4c |
| 0xC8 | NOP  | IMP | 1 |   4c |
| 0xC9 | NOP  | IMP | 1 |   4c |
| 0xCA | NOP  | IMP | 1 |   4c |
| 0xCB | NOP  | IMP | 1 |   4c |
| 0xCC | NOP  | IMP | 1 |   4c |
| 0xCD | NOP  | IMP | 1 |   4c |
| 0xCE | NOP  | IMP | 1 |   4c |
| 0xCF | NOP  | IMP | 1 |   4c |
| 0xD0 | NOP  | IMP | 1 |   4c |
| 0xD1 | NOP  | IMP | 1 |   4c |
| 0xD2 | NOP  | IMP | 1 |   4c |
| 0xD3 | NOP  | IMP | 1 |   4c |
| 0xD4 | NOP  | IMP | 1 |   4c |
| 0xD5 | NOP  | IMP | 1 |   4c |
| 0xD6 | NOP  | IMP | 1 |   4c |
| 0xD7 | NOP  | IMP | 1 |   4c |
| 0xD8 | NOP  | IMP | 1 |   4c |
| 0xD9 | NOP  | IMP | 1 |   4c |
| 0xDA | NOP  | IMP | 1 |   4c |
| 0xDB | NOP  | IMP | 1 |   4c |
| 0xDC | NOP  | IMP | 1 |   4c |
| 0xDD | NOP  | IMP | 1 |   4c |
| 0xDE | NOP  | IMP | 1 |   4c |
| 0xDF | NOP  | IMP | 1 |   4c |
| 0xE0 | NOP  | IMP | 1 |   4c |
| 0xE1 | NOP  | IMP | 1 |   4c |
| 0xE2 | NOP  | IMP | 1 |   4c |
| 0xE3 | NOP  | IMP | 1 |   4c |
| 0xE4 | NOP  | IMP | 1 |   4c |
| 0xE5 | NOP  | IMP | 1 |   4c |
| 0xE6 | NOP  | IMP | 1 |   4c |
| 0xE7 | NOP  | IMP | 1 |   4c |
| 0xE8 | NOP  | IMP | 1 |   4c |
| 0xE9 | NOP  | IMP | 1 |   4c |
| 0xEA | NOP  | IMP | 1 |   4c |
| 0xEB | NOP  | IMP | 1 |   4c |
| 0xEC | NOP  | IMP | 1 |   4c |
| 0xED | NOP  | IMP | 1 |   4c |
| 0xEE | NOP  | IMP | 1 |   4c |
| 0xEF | NOP  | IMP | 1 |   4c |
| 0xF0 | NOP  | IMP | 1 |   4c |
| 0xF1 | NOP  | IMP | 1 |   4c |
| 0xF2 | NOP  | IMP | 1 |   4c |
| 0xF3 | NOP  | IMP | 1 |   4c |
| 0xF4 | NOP  | IMP | 1 |   4c |
| 0xF5 | NOP  | IMP | 1 |   4c |
| 0xF6 | NOP  | IMP | 1 |   4c |
| 0xF7 | NOP  | IMP | 1 |   4c |
| 0xF8 | NOP  | IMP | 1 |   4c |
| 0xF9 | NOP  | IMP | 1 |   4c |
| 0xFA | NOP  | IMP | 1 |   4c |
| 0xFB | NOP  | IMP | 1 |   4c |
| 0xFC | NOP  | IMP | 1 |   4c |
| 0xFD | NOP  | IMP | 1 |   4c |
| 0xFE | NOP  | IMP | 1 |   4c |
| 0xFF | NOP  | IMP | 1 |   4c |
