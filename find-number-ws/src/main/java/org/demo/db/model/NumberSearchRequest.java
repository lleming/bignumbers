package org.demo.db.model;

import java.math.BigInteger;

public class NumberSearchRequest {

  private BigInteger id;
  private ResultCode resultCode;
  private BigInteger lookingNumber;
  private String filenames;
  private String error;

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public ResultCode getResultCode() {
    return resultCode;
  }

  public void setResultCode(ResultCode resultCode) {
    this.resultCode = resultCode;
  }

  public BigInteger getLookingNumber() {
    return lookingNumber;
  }

  public void setLookingNumber(BigInteger lookingNumber) {
    this.lookingNumber = lookingNumber;
  }

  public String getFilenames() {
    return filenames;
  }

  public void setFilenames(String filenames) {
    this.filenames = filenames;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}
