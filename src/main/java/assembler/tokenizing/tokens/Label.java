package assembler.tokenizing.tokens;

public class Label extends Token {

    private final String alias;
    private Operator referenceOperator;

    public Label(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public Token getReferenceOperator() {
        return referenceOperator;
    }

    public void setReferenceOperator(Operator referenceOperator) {
        this.referenceOperator = referenceOperator;
    }

    @Override
    public String toString() {
        return "Label '" + alias + "' ref. " + referenceOperator;
    }
}
