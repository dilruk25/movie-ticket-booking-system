package com.dilruk.movieticketbooking.services.ticket;

import com.dilruk.movieticketbooking.api.request.AddTicketsRequest;
import com.dilruk.movieticketbooking.dtos.TicketDTO;
import com.dilruk.movieticketbooking.enums.UserRole;
import com.dilruk.movieticketbooking.exceptions.MovieNotFoundException;
import com.dilruk.movieticketbooking.exceptions.TicketAlreadyExistsException;
import com.dilruk.movieticketbooking.exceptions.TicketNotFoundException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.TicketMapper;
import com.dilruk.movieticketbooking.models.movie.Movie;
import com.dilruk.movieticketbooking.models.ticket.Ticket;
import com.dilruk.movieticketbooking.models.user.User;
import com.dilruk.movieticketbooking.repositories.MovieRepository;
import com.dilruk.movieticketbooking.repositories.TicketRepository;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final MovieRepository movieRepository;
    private final TicketMapper ticketMapper;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);


    @Override
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Optional<Ticket> existTicket = ticketRepository.findTicketByTicketId(ticketDTO.getTicketId());
        if (existTicket.isPresent()) {
            throw new TicketAlreadyExistsException("Ticket already exists");
        }

        Ticket savedTicket = ticketRepository.save(ticketMapper.fromDtoToEntity(ticketDTO));
        return ticketMapper.fromEntityToDto(savedTicket);
    }

    public List<TicketDTO> addTickets(AddTicketsRequest request, String vendorId) {
        Movie movie = movieRepository.findMovieByMovieId(request.getMovieId())
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        User vendor = userRepository.findUserByRoleAndUserId(UserRole.VENDOR, vendorId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found"));

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < request.getNoOfTickets(); i++) {
            Ticket ticket = new Ticket();
            ticket.setMovie(movie);
            ticket.setUser(vendor);
            ticket.setDate(request.getDate());
            ticket.setAvailability(true);
            tickets.add(ticket);
        }
        return ticketRepository.saveAll(tickets).stream().map(ticketMapper::fromEntityToDto).toList();
    }

    @Override
    public List<TicketDTO> findAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(ticketMapper::fromEntityToDto).toList();
    }

    @Override
    public TicketDTO findTicketById(String ticketId) {
        Ticket existTicket = ticketRepository.findTicketByTicketId(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with the id: " + ticketId));

        return ticketMapper.fromEntityToDto(existTicket);
    }

    @Override
    public void deleteTicket(String ticketId) {
        Ticket existTicket = ticketRepository.findTicketByTicketId(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with the id: " + ticketId));

        ticketRepository.delete(existTicket);
    }

    @Override
    public List<TicketDTO> findTicketsByVendorId(String userId) {
        List<Ticket> tickets = ticketRepository.findTicketsByUser_UserId(userId).orElse(new ArrayList<>());
        if (tickets.isEmpty()) {
            logger.warn("No tickets available: {}", userId);
        }
        return tickets.stream().map(ticketMapper::fromEntityToDto).toList();
    }
}
