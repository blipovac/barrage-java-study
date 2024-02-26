package com.setronica.eventing.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentGatewayResponseDto {
  private boolean success;
  private ZonedDateTime paymentDate;
  private BigDecimal amount;
  private String cardToken;
}
