package org.demo.db.model;

/**
 * Operation result code.
 */
public enum ResultCode {

  /**
   * Number is found.
   */
  OK("00", "Число %s найдено."),

  /**
   * Number is not found.
   */
  NOT_FOUND("01", "Число не найдено ни в одном файле"),

  /**
   * Error is occurred during search.
   */
  ERROR("02", "Произошла ошибка во время выполнения");

  private final String codeValue;
  private final String message;

  ResultCode(String codeValue, String message) {
    this.codeValue = codeValue;
    this.message = message;
  }

  public String codeValue() {
    return this.codeValue;
  }

  public String message() {
    return message;
  }
}