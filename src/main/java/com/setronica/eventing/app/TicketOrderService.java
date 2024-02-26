package com.setronica.eventing.app;

import com.setronica.eventing.dto.PaymentGatewayResponseDto;
import com.setronica.eventing.exceptions.EntityNotFoundException;
import com.setronica.eventing.exceptions.NotEnoughSeatsAvailableException;
import com.setronica.eventing.exceptions.PaymentFailedException;
import com.setronica.eventing.persistence.MockPaymentGatewayRepository;
import com.setronica.eventing.persistence.Payment;
import com.setronica.eventing.persistence.PaymentRepository;
import com.setronica.eventing.persistence.TickerOrderRepository;
import com.setronica.eventing.persistence.TicketOrder;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketOrderService {
  private final TickerOrderRepository tickerOrderRepository;
  private final MockPaymentGatewayRepository paymentGatewayRepository;
  private final PaymentRepository paymentRepository;

  public TicketOrderService(TickerOrderRepository tickerOrderRepository,
                            MockPaymentGatewayRepository paymentGatewayRepository,
                            PaymentRepository paymentRepository) {
    this.tickerOrderRepository = tickerOrderRepository;
    this.paymentGatewayRepository = paymentGatewayRepository;
    this.paymentRepository = paymentRepository;
  }

  public List<TicketOrder> getAll() {
    log.debug("Fetching all ticket orders");
    List<TicketOrder> ticketOrders = tickerOrderRepository.findAll();
    log.debug("Finished fetching all ticket orders");

    return ticketOrders;
  }

  public TicketOrder getById(Integer id) {
    log.debug("Fetching ticket order with id: " + id);

    TicketOrder ticketOrder = tickerOrderRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("TicketOrder" + id + "not found"));

    log.debug("Finished fetching ticket order with id: " + id);

    return ticketOrder;
  }

  public TicketOrder create(TicketOrder ticketOrder) {
    log.debug("Booking a ticket order");
    TicketOrder bookedTicketOrder = bookTicket(ticketOrder);
    log.debug("Successfully booked ticket order: " + bookedTicketOrder.getId());

    log.info("Processing payment for ticket order:" + bookedTicketOrder.getId());
    PaymentGatewayResponseDto gatewayResponse =
        paymentGatewayRepository.processPayment(BigDecimal.valueOf(ticketOrder.getAmount()),
            "made up card token");

    paymentRepository.save(Payment.fromGatewayResponse(gatewayResponse, bookedTicketOrder.getId()));

    if (!gatewayResponse.isSuccess()) {
      log.warn(
          "Payment failed for ticket order: " + bookedTicketOrder.getId() + ". Cancelling booking");
      cancelBooking(bookedTicketOrder);
    }

    log.info("Payment processed successfully for ticket order: " + bookedTicketOrder.getId());

    log.debug("Finalizing ticket order");
    completeBooking(bookedTicketOrder);
    log.debug("Successfully finalized ticket order");

    return bookedTicketOrder;
  }

  private void completeBooking(TicketOrder bookedTicketOrder) {
    bookedTicketOrder.setPaymentStatus(TicketOrder.PAYMENT_STATUS.SALE);
    tickerOrderRepository.save(bookedTicketOrder);
  }

  private TicketOrder bookTicket(TicketOrder ticketOrder) {
    try {
      return tickerOrderRepository.save(ticketOrder);
    } catch (DataAccessException e) {
      if (e.getCause() instanceof SQLException sqlException) {
        if (sqlException.getMessage().contains(NotEnoughSeatsAvailableException.ERROR_MESSAGE)) {
          throw new NotEnoughSeatsAvailableException(
              NotEnoughSeatsAvailableException.ERROR_MESSAGE, sqlException);
        }
      } else {
        throw e;
      }

      return null;
    }
  }

  private void cancelBooking(TicketOrder bookedTicketOrder) {
    bookedTicketOrder.setPaymentStatus(TicketOrder.PAYMENT_STATUS.CANCELLED);
    tickerOrderRepository.save(bookedTicketOrder);
    throw new PaymentFailedException();
  }

  public void update(Integer id, TicketOrder ticketOrder) {
    log.debug("Updating ticket order with id: " + id);

    tickerOrderRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("TicketOrder" + id + "not found"));

    ticketOrder.setId(id);
    tickerOrderRepository.save(ticketOrder);

    log.debug("Successfully updated with id: " + id);
  }

  public void delete(Integer id) {
    log.debug("Deleting ticket order with id: " + id);
    tickerOrderRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("TicketOrder" + id + "not found"));
    tickerOrderRepository.deleteById(id);
    log.debug("Successfully deleted ticket order with id: " + id);
  }
}
