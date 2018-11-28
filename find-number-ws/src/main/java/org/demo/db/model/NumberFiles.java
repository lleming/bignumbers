package org.demo.db.model;

import java.math.BigInteger;

public class NumberFiles {

  private BigInteger number;
  private String filename;

  public BigInteger getNumber() {
    return number;
  }

  public void setNumber(BigInteger number) {
    this.number = number;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  @Override
  public String toString() {
    return "NumberFiles{" +
        ", number=" + number +
        ", filename='" + filename + '\'' +
        '}';
  }
}
