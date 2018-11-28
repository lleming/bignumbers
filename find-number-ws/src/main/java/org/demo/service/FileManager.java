package org.demo.service;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileManager {

  private FileStorage fileStorage;

  @Autowired
  public FileManager(FileStorage fileStorage) {
    this.fileStorage = fileStorage;
  }

  @PostConstruct
  public void parse() {

  }

}
