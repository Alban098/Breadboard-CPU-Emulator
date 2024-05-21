package rendering;

public class Utils {

    /**
     * Convert from an N bits unsigned Integer to its binary representation
     * @param value the RAW value to convert
     * @param nbBits how many bits to consider for conversion
     * @return the N bits String representation
     */
    public static String binaryString(int value, int nbBits) {
        StringBuilder s = new StringBuilder();
        int mask = 0x1 << (nbBits - 1);
        for (int i = 0; i < nbBits; i++) {
            if (i != 0 && i % 4 == 0)
                s.append(" ");
            s.append(((value & mask) != 0) ? "1" : "0");
            value <<= 1;
        }
        return s.toString();
    }
}
