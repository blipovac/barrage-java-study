package com.setronica.eventing.exceptions;

public class PaymentFailedException extends RuntimeException {
  public static final String ERROR_MESSAGE =
      "Payment gateway failed to process payment. Please try again later.";

  public PaymentFailedException() {
    super(ERROR_MESSAGE);
  }

  public PaymentFailedException(Throwable cause) {
    super(ERROR_MESSAGE, cause);
  }
}
