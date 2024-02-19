package com.setronica.eventing.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "\"event_schedule\"")
@Getter
@Setter
public class EventSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int eventId;

    @Column
    private LocalDate date;

    @Column
    private int availableSeats;

    @Column
    private BigDecimal price;
}
