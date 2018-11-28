package org.demo.generator;

import java.io.BufferedWriter;
import java.io.IOError;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is file generator implementation. Generate random numbers and write them into file.
 */
public class Generator implements Callable<String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(Generator.class);
  private static final Charset UTF8 = StandardCharsets.UTF_8;
  private static final StandardOpenOption CREATE = StandardOpenOption.CREATE;
  private static final long MAX_FILE_SIZE = 1_268_955_908l;
  private static final int BATCH_SIZE = 10000;
  private static final int BITS = 448;
  private static final int BUFFER_SIZE = 1024 * 1024;

  private final Path file;
  private long fileLength = 0l;
  private int batchSize;
  private int bufferSize;
  private int bitsNum;

  /**
   * Create generator with default characteristics.
   *
   * @param file to write generated numbers.
   */
  public Generator(Path file) {
    this(file, BATCH_SIZE);
  }

  /**
   * Create generator with batch size and rest default characteristics.
   *
   * @param file to write generated numbers.
   * @param batchSize of generated number sequence.
   */
  public Generator(Path file, int batchSize) {
    this.file = file;
    this.batchSize = batchSize;
    this.bitsNum = BITS;
    this.bufferSize = BUFFER_SIZE;
    this.bufferSize = BUFFER_SIZE;
  }


  @Override
  public String call() {
    LOGGER.info("Start generator of file " + file);
    String oldName = Thread.currentThread().getName();
    Thread.currentThread().setName("Thread-GenFile-" + file.getFileName().toString());

    /*main business logic is in here*/
    writeFile();

    LOGGER.info("Generator is done");
    return this.file.getFileName().toString();
  }

  private void writeFile() {
    try (BufferedWriter writer = Files.newBufferedWriter(file, UTF8, CREATE)) {
      StringBuilder builder = new StringBuilder(bufferSize);
      int i = 0;
      while (fileLength < MAX_FILE_SIZE) {
        while (i++ < batchSize) {
          String number = nextNumber();
          builder.append(number).append(',');
          fileLength += number.length();
        }
        writer.write(builder.toString());
        builder.setLength(0);
        i = 0;
        LOGGER.info(
            "Next batch is written of file " + file.getFileName().toString() + " file size " + Long
                .valueOf(fileLength));
      }
    } catch (IOException e) {
      LOGGER.error("IO Error", e);
      throw new RuntimeException("Error is occurred", e);
    }
  }

  private String nextNumber() {
    int bits = ThreadLocalRandom.current().nextInt(bitsNum);
    BigInteger number = new BigInteger(bits, ThreadLocalRandom.current());
    return number.toString();
  }
}
