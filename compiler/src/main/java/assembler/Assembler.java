/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler;

import assembler.linking.Linker;
import assembler.linking.token.LinkedMemoryBlock;
import assembler.linking.token.LinkedOperation;
import assembler.linking.token.LinkedToken;
import assembler.linking.token.LinkedVariable;
import assembler.mapping.Mapper;
import assembler.tokenizing.Tokenizer;
import assembler.tokenizing.exception.MismatchingVersionException;
import java.io.*;
import java.util.List;
import org.apache.log4j.PropertyConfigurator;

public class Assembler {

  private static final String ASM_VERSION_HEADER = "#BB8BC_ASM_v1";
  public static final int MAX_FILE_SIZE = 0x400;
  public static final int MAX_ADDRESS = 0x3FF;
  public static final int STACK_POINTER_MIN_ADDRESS = 0x300;
  private static Tokenizer tokenizer;
  private static Mapper mapper;
  private static Linker linker;

  public static void main(String[] args) throws IOException, MismatchingVersionException {
    PropertyConfigurator.configure("./log4j.properties");

    boolean foundFile = false;
    boolean foundBinary = false;
    String file = null;
    String binary = null;
    for (String arg : args) {
      if (foundFile) {
        file = arg;
      }
      if (foundBinary) {
        binary = arg;
      }
      foundFile = "-f".equals(arg);
      foundBinary = "-o".equals(arg);
    }

    tokenizer = new Tokenizer();
    mapper = new Mapper();
    linker = new Linker();
    if (file == null || createTokens(file)) {
      return;
    }
    if (mapTokens()) {
      return;
    }
    if (linkTokens()) {
      return;
    }
    if (binary == null) {
      binary = file.split("\\.")[0] + ".rom";
    }
    writeBinary(binary);
  }

  private static boolean createTokens(String file) throws IOException, MismatchingVersionException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
    return tokenizer.hasErrors();
  }

  private static boolean mapTokens() {
    mapper.mapTokens(tokenizer.getTokenizationResult());
    return mapper.hasErrors();
  }

  private static boolean linkTokens() {
    linker.linkTokens(mapper.getMappingResult());
    return linker.hasErrors();
  }

  private static void writeBinary(String file) {
    byte[] binary = new byte[MAX_FILE_SIZE];
    List<LinkedToken> tokens = linker.getLinkingResult();
    for (LinkedToken token : tokens) {
      if (token instanceof LinkedMemoryBlock memoryBlock) {
        for (int i = 0; i < memoryBlock.getData().length; i++) {
          binary[memoryBlock.getAddress() + i] = (byte) memoryBlock.getData()[i];
        }
      } else if (token instanceof LinkedVariable variable) {
        binary[variable.getAddress()] = (byte) variable.getData();
      } else if (token instanceof LinkedOperation operation) {
        binary[operation.getAddress()] = (byte) operation.getInstruction().getOpcode();
        if (operation.getInstruction().getSize() > 1) {
          for (int i = 1; i < operation.getInstruction().getSize(); i++) {
            binary[operation.getAddress() + i] =
                (byte)
                    ((operation.getArgs() != null && operation.getArgs().length >= i)
                        ? operation.getArgs()[i - 1]
                        : 0);
          }
        }
      }
    }

    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(binary);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
