package com.dilruk.movieticketbooking.cinema.services;

import com.dilruk.movieticketbooking.cinema.dtos.MultiplexDTO;

import java.util.List;

public interface MultiplexService {

    MultiplexDTO saveMultiplex(MultiplexDTO multiplexDTO);

    MultiplexDTO getMultiplexByMultiplexId(String multiplexID);

    List<MultiplexDTO> getAllMultiplex();

    MultiplexDTO updateMultiplex(String multiplexId, MultiplexDTO multiplexDTO);

    void deleteMultiplex(String multiplexID);


}