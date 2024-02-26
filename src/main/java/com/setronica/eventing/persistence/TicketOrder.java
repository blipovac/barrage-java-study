package com.setronica.eventing.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"ticket_order\"")
@Setter
@Getter
public class TicketOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private int amount;

    @Column
    private int eventScheduleId;

    @Column
    private PAYMENT_STATUS paymentStatus;

    public enum PAYMENT_STATUS {
        BOOKED,
        SALE,
        REFUNDED,
        CANCELLED
    }
}
