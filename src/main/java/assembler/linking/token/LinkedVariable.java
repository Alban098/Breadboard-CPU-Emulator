package assembler.linking.token;

/**
 * Represents a Variable
 * <p>.var $(addr)</p>
 * <p>  FF FF FF ...</p>
 * that as been tokenized, mapped and linked, at this point it is valid
 */
public class LinkedVariable extends LinkedToken {

    private final int data;

    public LinkedVariable(int address, int data) {
        super(address);
        this.data = data;
    }

    public int getData() {
        return data;
    }

    @Override
    public String toString() {
        return String.format("[$%02X] var=%02X", getAddress(), data);
    }
}
