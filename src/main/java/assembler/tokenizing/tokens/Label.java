package assembler.tokenizing.tokens;

public class Label {

    private final String alias;
    private final int address;

    public Label(String alias, String address) throws MalformedAddress {
        this.alias = alias;
        if (!address.startsWith("$")) {
            throw new MalformedAddress();
        }
        this.address = Integer.parseInt(address.replaceAll("[$()]", ""), 16);
    }

    public String getAlias() {
        return alias;
    }

    public int getAddress() {
        return address;
    }
}
