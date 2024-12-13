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

/**
 * Service implementation for managing tickets. This includes creating, adding, retrieving, and deleting tickets.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TicketRepository ticketRepository;
    private final MovieRepository movieRepository;
    private final TicketMapper ticketMapper;
    private final UserRepository userRepository;

    /**
     * Creates a new ticket.
     *
     * @param ticketDTO The data transfer object representing the ticket.
     * @return The created TicketDTO.
     * @throws TicketAlreadyExistsException If a ticket with the same ID already exists.
     */
    @Override
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Optional<Ticket> existTicket = ticketRepository.findTicketByTicketId(ticketDTO.getTicketId());
        if (existTicket.isPresent()) {
            throw new TicketAlreadyExistsException("Ticket with ID " + ticketDTO.getTicketId() + " already exists.");
        }

        Ticket savedTicket = ticketRepository.save(ticketMapper.fromDtoToEntity(ticketDTO));
        return ticketMapper.fromEntityToDto(savedTicket);
    }

    /**
     * Adds multiple tickets to the repository based on the request and vendor ID.
     *
     * @param request The request containing ticket details.
     * @param vendorId The ID of the vendor adding the tickets.
     * @return A list of TicketDTOs representing the added tickets.
     * @throws MovieNotFoundException If the specified movie is not found.
     * @throws UserNotFoundException If the specified vendor is not found.
     */
    public List<TicketDTO> addTickets(AddTicketsRequest request, String vendorId) {
        Movie movie = findMovieById(request.getMovieId());
        User vendor = findVendorById(vendorId);

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < request.getNoOfTickets(); i++) {
            Ticket ticket = new Ticket();
            ticket.setMovie(movie);
            ticket.setUser(vendor);
            ticket.setDate(request.getDate());
            ticket.setAvailability(true);
            tickets.add(ticket);
        }
        return ticketRepository.saveAll(tickets).stream()
                .map(ticketMapper::fromEntityToDto)
                .toList();
    }

    /**
     * Retrieves all tickets.
     *
     * @return A list of TicketDTOs representing all tickets.
     */
    @Override
    public List<TicketDTO> findAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(ticketMapper::fromEntityToDto)
                .toList();
    }

    /**
     * Finds a ticket by its ID.
     *
     * @param ticketId The ID of the ticket.
     * @return The TicketDTO representing the ticket.
     * @throws TicketNotFoundException If no ticket is found with the specified ID.
     */
    @Override
    public TicketDTO findTicketById(String ticketId) {
        Ticket existTicket = ticketRepository.findTicketByTicketId(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));

        return ticketMapper.fromEntityToDto(existTicket);
    }

    /**
     * Deletes a ticket by its ID.
     *
     * @param ticketId The ID of the ticket to delete.
     * @throws TicketNotFoundException If no ticket is found with the specified ID.
     */
    @Override
    public void deleteTicket(String ticketId) {
        Ticket existTicket = ticketRepository.findTicketByTicketId(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));

        ticketRepository.delete(existTicket);
    }

    /**
     * Finds all tickets created by a specific vendor.
     *
     * @param userId The ID of the vendor.
     * @return A list of TicketDTOs created by the vendor.
     * @throws UserNotFoundException If no tickets are found for the specified vendor.
     */
    @Override
    public List<TicketDTO> findTicketsByVendorId(String userId) {
        List<Ticket> tickets = ticketRepository.findTicketsByUser_UserId(userId)
                .orElseThrow(() -> new UserNotFoundException("No tickets found for vendor with ID: " + userId));

        return tickets.stream()
                .map(ticketMapper::fromEntityToDto)
                .toList();
    }

    // Helper method to find a movie by its ID
    private Movie findMovieById(String movieId) {
        return movieRepository.findMovieByMovieId(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with ID: " + movieId));
    }

    // Helper method to find a vendor by its ID
    private User findVendorById(String vendorId) {
        return userRepository.findUserByRoleAndUserId(UserRole.ROLE_VENDOR, vendorId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found with ID: " + vendorId));
    }
}