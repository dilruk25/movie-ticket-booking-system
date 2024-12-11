package com.dilruk.movieticketbooking.dtos;

import com.dilruk.movieticketbooking.enums.SeatType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatDTO {
    private String seatId;
    private String seatName;
    private SeatType seatType;
}
