package com.setronica.eventing.exceptions;

public class NotEnoughSeatsAvailableException extends RuntimeException {
  public static final String ERROR_MESSAGE = "Not enough seats available";
  public NotEnoughSeatsAvailableException(String message) {
    super(message);
  }

  public NotEnoughSeatsAvailableException(String message, Throwable cause) {
    super(message, cause);
  }
}
