package emulation.component;

public class Bus implements Formattable {

    private int state = 0;

    public int read() {
        return state;
    }

    public void write(int value) {
        this.state = value;
    }

    @Override
    public String hexString() {
        return String.format("0x%02X", state);
    }

    @Override
    public String binaryString() {
        return String.format("%8s", Integer.toBinaryString(state)).replaceAll(" ", "0");
    }
}
