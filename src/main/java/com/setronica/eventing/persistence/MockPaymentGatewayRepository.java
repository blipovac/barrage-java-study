package com.setronica.eventing.persistence;

import com.setronica.eventing.dto.PaymentGatewayResponseDto;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Repository;

@Repository
public class MockPaymentGatewayRepository {
  public PaymentGatewayResponseDto processPayment(BigDecimal amount, String cardToken) {
    return new PaymentGatewayResponseDto(true, ZonedDateTime.now(), amount, cardToken);
  }
}
