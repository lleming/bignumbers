package org.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;

@Component
public class FileStorage {

  private static final Pattern PATTERN = Pattern.compile("^file_\\\\d\\\\d\\\\.txt$");
  private final Path baseDirectory;

  public FileStorage(String baseDirectory) {
    Path path = Paths.get(baseDirectory);
    if (!path.toFile().exists()) {
      throw new IllegalArgumentException("Basedir doesn't exists.");
    }
    this.baseDirectory = path;
  }

  public List<String> files() throws IOException {
    return IntStream.range(0, 20).mapToObj(i -> "file_" + Integer.valueOf(i) + ".txt")
        .collect(Collectors.toList());
  }

  public InputStream open(String file) throws IOException {
    return Files.newInputStream(baseDirectory.resolve(file), StandardOpenOption.READ);
  }
}
