#BB8BC_ASM_v1

.block $(F0)
    10 11 12 32 87 E4 FF DE
.endblock
.var X 1A
.const B F0

:START
    LDA $X //load the value at the location of X
    ADD $_B
    ADD $(F1)
    STA *X
    LDA 0
:LOOP
    ADD 10
    BCC @LOOP //loop until overflow over 0xFF
    HLT
