package com.dilruk.movieticketbooking.services.multplex;

import com.dilruk.movieticketbooking.dtos.MultiplexDTO;

import java.util.List;

public class MultiplexServiceImpl implements MultiplexService {

    /**
     * @param multiplexDTO
     * @return
     */
    @Override
    public MultiplexDTO saveMultiplex(MultiplexDTO multiplexDTO) {
        return null;
    }

    /**
     * @param multiplexID
     * @return
     */
    @Override
    public MultiplexDTO getMultiplexByMultiplexId(String multiplexID) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<MultiplexDTO> getAllMultiplex() {
        return List.of();
    }

    /**
     * @param multiplexId
     * @param multiplexDTO
     * @return
     */
    @Override
    public MultiplexDTO updateMultiplex(String multiplexId, MultiplexDTO multiplexDTO) {
        return null;
    }

    /**
     * @param multiplexID
     */
    @Override
    public void deleteMultiplex(String multiplexID) {

    }
}
