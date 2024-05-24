package assembler.mapping.token;

import assembler.tokenizing.token.Label;

public class MappedLabel extends MappedToken {

    private final Label token;
    private final MappedOperation referenceOperation;

    public MappedLabel(Label token, MappedOperation referenceOperation) {
        super(referenceOperation.getAddress());
        this.token = token;
        this.referenceOperation = referenceOperation;
    }

    public Label getToken() {
        return token;
    }

    public MappedOperation getReferenceOperation() {
        return referenceOperation;
    }

    @Override
    public String toString() {
        return String.format("[$%02X] %s | ref. {%s}", getAddress(), token.getAlias(), referenceOperation);
    }
}
