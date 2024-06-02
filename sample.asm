#BB8BC_ASM_v1

.block $(F0)
    10 11 12 13 14 15 16 17
.endblock
.block $(02F0)
    FF FF F7 FF FF FF FF 55
.endblock

.var VAR 1A             // .var only work with ABSOLUTE addressing mode, as they abstract 16bit addresses

.const byte BYTE F0     // .const byte work with all addressing modes as they are just an in place replacement
.const byte BYTE2 02
.const word WORD 02F7   // .const word only works with ABSOLUTE addressing mode, as they can only represent a 16bit address

:START
    // Addressing modes
    LDA #10             // Loads #10 into A
    LDA $F0             // Loads #F0 from $FO into A
    LDA $02F2           // Loads #F7 from $02F2 into A
    ADD $02, A          // Adds #55 from $02F7 to A

    // variable usages
    LDA VAR             // Loads the value of VAR (#1A) into A
    ADD #25             // Adds #25 to A
    STA VAR             // Stores the value of A into VAR

    // constant byte usages
    LDA #(BYTE)             // Loads #F0 into A
    LDA $(BYTE)             // Loads #10 from $FO into A
    LDA $02(BYTE)           // Loads #FF from $02F0 into A
    LDA $(BYTE2)F2          // Loads #F7 from $02F2 into A
    ADD $(BYTE2), A         // Adds #55 from $02F7 to A

    // constant word usages
    LDA $(WORD)             // Loads #55 from $02F7 into A

:LOOP
    ADD #10
    BCC @LOOP               //loop until overflow over 0xFF
    HLT