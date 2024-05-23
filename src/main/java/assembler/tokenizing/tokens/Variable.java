package assembler.tokenizing.tokens;

public class Variable {

    private final String alias;
    private final int value;
    private int address;

    public Variable(String alias, String value) {
        this.alias = alias;
        this.value = Integer.parseInt(value, 16);
    }

    public String getAlias() {
        return alias;
    }

    public int getValue() {
        return value;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
