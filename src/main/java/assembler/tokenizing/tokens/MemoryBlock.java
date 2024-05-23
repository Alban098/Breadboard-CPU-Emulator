package assembler.tokenizing.tokens;

import java.util.ArrayList;
import java.util.List;

public class MemoryBlock {

    private final String alias;
    private final int address;
    private final List<Integer> data = new ArrayList<>();

    public MemoryBlock(String alias, String address) throws MalformedAddress {
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

    public List<Integer> getData() {
        return data;
    }

    public void addData(int value) {
        data.add(value);
    }
}
