package com.setronica.eventing.web;

import com.setronica.eventing.app.TicketOrderService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.setronica.eventing.persistence.TicketOrder;

@RestController
@RequestMapping("event/api/v1/ticket-orders")
public class TicketOrderController {
  private final TicketOrderService ticketOrderService;

  public TicketOrderController(TicketOrderService ticketOrderService) {
    this.ticketOrderService = ticketOrderService;
  }

  @GetMapping
  public List<TicketOrder> findAll() {
    return ticketOrderService.getAll();
  }

  @GetMapping("/{id}")
    public TicketOrder findById(
        @PathVariable Integer id
    ) {
        return ticketOrderService.getById(id);
    }

    @PostMapping
    public TicketOrder createTicketOrder(
        @RequestBody TicketOrder ticketOrder
    ) {
        return ticketOrderService.create(ticketOrder);
    }

    @PatchMapping("/{id}")
    public void updateTicketOrder(
        @PathVariable Integer id,
        @RequestBody TicketOrder ticketOrder
    ) {
        ticketOrderService.update(id, ticketOrder);
    }

    @DeleteMapping("/{id}")
    public void deleteTicketOrder(
        @PathVariable Integer id
    ) {
        ticketOrderService.delete(id);
    }
}
