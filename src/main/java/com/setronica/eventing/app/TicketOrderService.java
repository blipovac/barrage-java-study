package com.setronica.eventing.app;

import com.setronica.eventing.exceptions.EntityNotFoundException;
import com.setronica.eventing.persistence.TicketOrder;
import com.setronica.eventing.persistence.TickerOrderRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TicketOrderService {
  private final TickerOrderRepository tickerOrderRepository;

  public TicketOrderService(TickerOrderRepository tickerOrderRepository) {
    this.tickerOrderRepository = tickerOrderRepository;
  }

  public List<TicketOrder> getAll() {
    return tickerOrderRepository.findAll();
  }

  public TicketOrder getById(Integer id) {
    return tickerOrderRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("TickerOrder" + id + "not found"));
  }

  public TicketOrder create(TicketOrder ticketOrder) {
    return tickerOrderRepository.save(ticketOrder);
  }

  public void update(Integer id, TicketOrder ticketOrder) {
    tickerOrderRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("TickerOrder" + id + "not found"));
    ticketOrder.setId(id);
    tickerOrderRepository.save(ticketOrder);
  }

  public void delete(Integer id) {
    tickerOrderRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("TickerOrder" + id + "not found"));
    tickerOrderRepository.deleteById(id);
  }
}
