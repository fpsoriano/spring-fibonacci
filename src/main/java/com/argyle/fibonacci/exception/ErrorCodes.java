package com.argyle.fibonacci.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.IllegalFormatException;

public enum ErrorCodes {
  MALFORMED_REQUEST(1, "Malformed Request."),
  INTERNAL_SERVER_ERROR(2, "Internal Server error.");

  private static final Logger LOG = LoggerFactory.getLogger(ErrorCodes.class);

  private final int code;
  private final String message;

  ErrorCodes(final int code, final String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getFormattedMessage(final Object... args) {
    if (message == null) {
      return "";
    }

    try {
      return String.format(message, args);
    } catch (final IllegalFormatException e) {
      LOG.error(e.getMessage(), e);
      return message.replace("%s", "");
    }
  }
}
