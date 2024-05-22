package emulation.constant;

public enum Flag {
    ZERO(0b100),
    CARRY(0b010),
    NEGATIVE(0b001);

    final int mask;

    Flag(int mask) {
        this.mask = mask;
    }

    public int getMask() {
        return mask;
    }
}
