package assembler.tokenizing.tokens;

public class Variable extends Token {

    private final String alias;
    private final int value;

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

    @Override
    public String toString() {
        return "Var " + alias + "=" + String.format("%02X", value);
    }
}
