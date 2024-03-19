package com.suryapalla.bookmyshow.model;

import com.suryapalla.bookmyshow.model.constant.TicketStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
public class Ticket extends BaseModel{
    private LocalDateTime timeOfBooking;
    private Double totalAmount;
    @OneToMany
    private List<ShowSeat> showSeats;
    @ManyToOne
    private Event event;
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;
}
