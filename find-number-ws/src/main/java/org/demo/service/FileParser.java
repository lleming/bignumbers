package org.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parse file with number and save value into database.
 */
public class FileParser implements Callable<String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileParser.class);
  private static final Charset UTF8 = StandardCharsets.UTF_8;
  private static final StandardOpenOption READ_OPTION = StandardOpenOption.READ;
  private static final int BUFFER_SIZE = 1024 * 1024 * 8;

  private final Path path;

  public FileParser(Path path) {
    this.path = path;
  }


  @Override
  public String call() throws Exception {
    long fileSize = Files.size(path);
    try (BufferedReader reader = Files.newBufferedReader(path, UTF8, StandardOpenOption.READ)) {
      char[] buffer = new char[BUFFER_SIZE];
      while (true) {
        int readed = reader.read(buffer);
        int lastComma = 0;
      }

    } catch (IOException e) {
      LOGGER.error("IO Error is occurred during parsing {}", path.getFileName().toString(), e);
      throw new RuntimeException("IO Error", e);
    }
  }

  private List<String> readBuffer(int read, char[] buffer) {
    List<String> result = new ArrayList();
    int lastComma = 0;
    for (int i = 0; i < read; i++) {
      char c = buffer[i];
      if (c == ',') {
        String number = new String(buffer, lastComma, i);
        lastComma = i;
      }
    }
    return result;
  }
}
