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
