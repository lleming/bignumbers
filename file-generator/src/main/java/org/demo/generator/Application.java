package org.demo.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.awt.image.ImageWatched.Link;

/**
 * Hello world!
 */
public class Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

  private static ExecutorService pool;
  private static CompletionService<String> completionService;

  public static void main(String[] args)
      throws IOException, InterruptedException, ExecutionException {

    if (args.length < 1) {
      throw new IllegalArgumentException("Provide at least one argument of base directory...");
    }
    Path baseDirectory = Paths.get(args[0]);
    if (Files.notExists(baseDirectory, LinkOption.NOFOLLOW_LINKS)) {
      throw new IllegalArgumentException(
          String.format("Base directory '%s' doesn't exist", baseDirectory.toString()));
    }
    if (Files.list(baseDirectory).count() > 0l) {
      throw new IllegalArgumentException("Provided base directory is not empty");
    }

    List<String> files = fileNames();

    files.stream()
        .map(file -> new Generator(baseDirectory.resolve(file)))
        .forEach(g -> completionService().submit(g));

    int count = files.size();
    while (count-- > 0) {
      String file = completionService().take().get();
      LOGGER.info("Completed file " + file);
    }

    LOGGER.info("Generation of files is completed");
    shutdown();
  }

  /**
   * Create list of file name those are used to store generated big numbers. File name pattern is
   * <code>file_nn.dat</code>. Variable nn have value between 0 and 19. Total there are 20 files.
   *
   * @return list of file name
   */
  private static List<String> fileNames() {
    return IntStream.range(0, 20).mapToObj(i -> "file_" + Integer.valueOf(i) + ".dat").collect(
        Collectors.toList());
  }

  /**
   * Create and return completion service.
   *
   * @see CompletionService
   */
  private static CompletionService<String> completionService() {
    if (pool == null) {
      pool = Executors.newFixedThreadPool(4);
      completionService = new ExecutorCompletionService<>(pool);
    }
    return completionService;
  }

  /**
   * Null safe alias for {@link ThreadPoolExecutor#shutdown()}.
   */
  private static void shutdown() {
    if (pool != null) {
      pool.shutdown();
    }
  }
}
