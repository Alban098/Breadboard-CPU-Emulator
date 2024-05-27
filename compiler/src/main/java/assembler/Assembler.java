/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package assembler;

import assembler.linking.Linker;
import assembler.linking.exception.LinkingException;
import assembler.linking.token.LinkedMemoryBlock;
import assembler.linking.token.LinkedOperation;
import assembler.linking.token.LinkedToken;
import assembler.linking.token.LinkedVariable;
import assembler.mapping.Mapper;
import assembler.mapping.exception.MappingException;
import assembler.tokenizing.Tokenizer;
import assembler.tokenizing.exception.TokenizingException;
import java.io.*;
import java.util.List;

public class Assembler {

  private static final String ASM_FILE = "sample.asm";
  private static final String ROM_FILE = "sample.rom";
  private static final String ASM_VERSION_HEADER = "#BB8BC_ASM_v1";
  private static Tokenizer tokenizer;
  private static Mapper mapper;
  private static Linker linker;

  public static void main(String[] args)
      throws IOException, TokenizingException, MismatchingVersionException, MappingException,
          LinkingException {
    tokenizer = new Tokenizer();
    mapper = new Mapper();
    linker = new Linker();
    createTokens();
    mapTokens();
    linkTokens();
    writeBinary();
  }

  private static void createTokens()
      throws IOException, TokenizingException, MismatchingVersionException {
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

  private static void mapTokens() throws MappingException {
    mapper.mapTokens(tokenizer.getTokenizationResult());
  }

  private static void linkTokens() throws LinkingException {
    linker.linkTokens(mapper.getMappingResult());
  }

  private static void writeBinary() {
    byte[] binary = new byte[0x100];
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
        if (operation.getInstruction().getSize() == 2) {
          binary[operation.getAddress() + 1] =
              operation.getArg() != null ? operation.getArg().byteValue() : 0;
        }
      }
    }

    try (FileOutputStream fos = new FileOutputStream(ROM_FILE)) {
      fos.write(binary);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
