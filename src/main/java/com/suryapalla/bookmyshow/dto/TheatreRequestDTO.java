package com.suryapalla.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreRequestDTO {
    private String name;
    private String address;

    private Integer cityId;
}
