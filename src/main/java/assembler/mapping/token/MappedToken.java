package assembler.mapping.token;

public abstract class MappedToken {
    private final int address;

    public MappedToken(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }
}
