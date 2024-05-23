package assembler;

import assembler.tokenizing.Tokenizer;
import assembler.tokenizing.exception.TokenizingException;

import java.io.*;

public class Assembler {

    private static final String ASM_FILE = "sample.asm";
    private static final String ASM_VERSION_HEADER = "#BB8BC_ASM_v1";
    private static Tokenizer tokenizer;
    private static byte[] result = new byte[0x100];

    public static void main(String[] args) throws IOException, TokenizingException, MismatchingVersionException {
        tokenizer = new Tokenizer();
        createTokens();
        System.out.println(" ");
    }

    private static void createTokens() throws IOException, TokenizingException, MismatchingVersionException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ASM_FILE))) {
            String header = reader.readLine();
            if (!ASM_VERSION_HEADER.equals(header)) {
                throw new MismatchingVersionException("Wrong ASM Header !");
            }
            do {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                tokenizer.tokenize(line);
            } while (true);
        }
    }


}
