package com.dilruk.movieticketbooking.services.multplex;

import com.dilruk.movieticketbooking.dtos.MultiplexDTO;

import java.util.List;

public interface MultiplexService {

    MultiplexDTO saveMultiplex(MultiplexDTO multiplexDTO);

    MultiplexDTO getMultiplexByMultiplexId(String multiplexID);

    List<MultiplexDTO> getAllMultiplex();

    MultiplexDTO updateMultiplex(String multiplexId, MultiplexDTO multiplexDTO);

    void deleteMultiplex(String multiplexID);


}