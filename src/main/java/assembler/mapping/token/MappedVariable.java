package assembler.mapping.token;

import assembler.tokenizing.token.Variable;

public class MappedVariable extends MappedToken {

    private final Variable token;

    public MappedVariable(int address, Variable token) {
        super(address);
        this.token = token;
    }

    public Variable getToken() {
        return token;
    }

    @Override
    public String toString() {
        return String.format("[$%02X] %s", getAddress(), token.toString());
    }
}
