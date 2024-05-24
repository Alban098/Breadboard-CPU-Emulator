package assembler.mapping.token;

import assembler.tokenizing.token.Operation;

public class MappedOperation extends MappedToken {

    private final Operation token;

    public MappedOperation(int address, Operation token) {
        super(address);
        this.token = token;
    }

    public Operation getToken() {
        return token;
    }

    @Override
    public String toString() {
        return String.format("[$%02X] %s", getAddress(), token.toString());
    }
}
