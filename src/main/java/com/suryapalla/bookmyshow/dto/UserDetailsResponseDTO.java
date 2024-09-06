package com.suryapalla.bookmyshow.dto;

import com.suryapalla.bookmyshow.model.Ticket;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDetailsResponseDTO {
    private String username;
    private String email;
    private String role;
    private List<Ticket> tickets;
}
