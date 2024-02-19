package com.setronica.eventing.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerOrderRepository extends JpaRepository<TicketOrder, Integer> {
}
