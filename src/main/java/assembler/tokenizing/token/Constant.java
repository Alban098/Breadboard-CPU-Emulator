package assembler.tokenizing.token;

import java.util.Objects;

public class Constant extends Token {

    private final String alias;
    private final int value;

    public Constant(String alias, String value) {
        this.alias = alias;
        this.value = Integer.parseInt(value == null ? "0" : value, 16) & 0xFF;
    }

    public String getAlias() {
        return alias;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Const " + alias + "=" + String.format("%02X", value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Constant variable = (Constant) o;
        return value == variable.value && Objects.equals(alias, variable.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, value);
    }
}
