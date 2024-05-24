package assembler.linking.token;

/**
 * Base class for a Linked Token
 */
public abstract class LinkedToken {

    /**
     * The base address of the token
     */
    private final int address;

    /**
     * Creates a new LinkedToken
     *
     * @param address the base address of the token
     */
    public LinkedToken(int address) {
        this.address = address;
    }

    /**
     * Returns the base address of the token
     *
     * @return the base address of the token
     */
    public int getAddress() {
        return address;
    }
}
