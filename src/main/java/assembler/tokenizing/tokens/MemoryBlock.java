package assembler.tokenizing.tokens;

import assembler.tokenizing.exception.MalformedAddressException;

import java.util.ArrayList;
import java.util.List;

public class MemoryBlock extends Token {

    private final int address;
    private final List<Integer> data = new ArrayList<>();

    public MemoryBlock(String address) throws MalformedAddressException {
        if (!address.startsWith("$")) {
            throw new MalformedAddressException();
        }
        this.address = Integer.parseInt(address.replaceAll("[$()]", ""), 16);
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Block ");
        builder
                .append(String.format("%02X", address))
                .append(" [")
                .append(String.format("%X", data.size()))
                .append("Bytes] { ");
        data.forEach(data -> builder.append(String.format("%02X ", data)));
        builder.append("}");
        return builder.toString();
    }
}
