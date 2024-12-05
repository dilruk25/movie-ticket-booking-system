package com.dilruk.movieticketbooking.common.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeatConfig {
    private char seatRowCount;
    private int seatColumnCount;
    private String seatName;
}