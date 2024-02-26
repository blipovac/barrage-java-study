package com.setronica.eventing.persistence;

import com.setronica.eventing.dto.PaymentGatewayResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"payments\"")
@Setter
@Getter
public class Payment {
  @Column
  private boolean success;
  @Column
  private ZonedDateTime paymentDate;
  @Column
  private BigDecimal amount;
  @Column
  private String cardToken;
  @Column
  private int ticketOrderId;

  public static Payment fromGatewayResponse(PaymentGatewayResponseDto gatewayResponse,
                                            int ticketOrderId) {
    Payment payment = new Payment();
    payment.setSuccess(gatewayResponse.isSuccess());
    payment.setPaymentDate(ZonedDateTime.now());
    payment.setAmount(gatewayResponse.getAmount());
    payment.setCardToken(gatewayResponse.getCardToken());
    payment.setTicketOrderId(ticketOrderId);
    return payment;
  }
}
