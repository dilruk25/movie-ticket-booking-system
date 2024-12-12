package com.dilruk.movieticketbooking.api.request;

import com.dilruk.movieticketbooking.enums.UserRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
