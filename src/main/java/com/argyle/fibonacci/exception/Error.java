package com.argyle.fibonacci.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

  private static final long serialVersionUID = 1L;

  @JsonProperty private int code;

  @JsonProperty private String message;

  @JsonProperty private List<String> details;

  public Error(){}

  public Error(final ErrorCodes issue) {
    code = issue.getCode();
    message = issue.getFormattedMessage();
  }

  public Error(final ErrorCodes issue, final Object... args) {
    code = issue.getCode();
    message = issue.getFormattedMessage(args);
  }

  public Error(final ErrorCodes issue, final List<String> details) {
    this(issue);
    this.details = details;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<String> getDetails() {
    return details;
  }

  public void setDetails(List<String> details) {
    this.details = details;
  }

  @Override
  public String toString() {
    return String.format("Error{code= %s, message='%s' details= '%s'}", code, message, details);
  }
}
