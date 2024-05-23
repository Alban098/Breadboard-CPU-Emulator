#BB8BC_ASM_v1

.block $(F0)
    10 11 12 32 87 E4 FF DE
.var X 1A
:START
    LDA 25
    ADD 12
    ADD $(F1)
    STA E0
:LOOP
    LDA 0
    ADD 10
    BCC @LOOP #loop until overflow over 0xFF
    HLT

