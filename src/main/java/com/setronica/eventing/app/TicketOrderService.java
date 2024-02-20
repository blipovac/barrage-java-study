package com.setronica.eventing.app;

import com.setronica.eventing.exceptions.EntityNotFoundException;
import com.setronica.eventing.exceptions.NotEnoughSeatsAvailableException;
import com.setronica.eventing.persistence.TickerOrderRepository;
import com.setronica.eventing.persistence.TicketOrder;
import java.sql.SQLException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketOrderService {
  private final TickerOrderRepository tickerOrderRepository;

  public TicketOrderService(TickerOrderRepository tickerOrderRepository) {
    this.tickerOrderRepository = tickerOrderRepository;
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
    log.debug("Creating ticket order");
    TicketOrder createdTicketOrder = null;

    try {
      createdTicketOrder = tickerOrderRepository.save(ticketOrder);
    } catch (DataAccessException e) {
      if (e.getCause() instanceof SQLException sqlException) {
        if (sqlException.getMessage().contains(NotEnoughSeatsAvailableException.ERROR_MESSAGE)) {
          throw new NotEnoughSeatsAvailableException(
              NotEnoughSeatsAvailableException.ERROR_MESSAGE, sqlException);
        }
      } else {
        throw e;
      }
    }

    log.debug("Successfully created ticket order");

    return createdTicketOrder;
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
