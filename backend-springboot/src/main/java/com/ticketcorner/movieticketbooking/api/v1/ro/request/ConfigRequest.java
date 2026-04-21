package com.ticketcorner.movieticketbooking.api.v1.ro.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigRequest {

    @NotEmpty
    private int totalTickets;
    @NotEmpty
    private int ticketReleaseRate;
    @NotEmpty
    private int customerRetrievalRate;
    @NotEmpty
    private int maxTicketCapacity;

}
