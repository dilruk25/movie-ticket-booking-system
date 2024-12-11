package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.dtos.EventDTO;
import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.MovieServiceImpl;
import com.dilruk.movieticketbooking.services.event.EventServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService extends AbstractUserService {

    private MovieServiceImpl movieService;
    private EventServiceImpl eventService;

    public VendorService(UserRepository userRepository, MovieServiceImpl movieService, EventServiceImpl eventService, UserMapper userMapper) {
        super(userRepository, movieService, eventService, userMapper);
    }

    @Override
    public List<UserDTO> findAllVendors(){
        return userRepository
    }

    @Override
    public List<EventDTO> findAllEvents() {
        return eventService.findAllEvents();
    }

    @Override
    public boolean requestAddMovie(MovieDTO movieDTO, boolean approved) {
        if (approved) {
            movieService.createMovie(movieDTO);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<EventDTO> findEventsByVendorId(String userId) {
        return eventService.findEventsByVendorId(userId);
    }

    public EventDTO addEvent(EventDTO eventDTO) {
        return eventService.createEvent(eventDTO);
    }

    public EventDTO updateEvent(String id, EventDTO eventDTO) {
        return eventService.updateEvent(id,eventDTO);
    }

    public void deleteEvent(String id) {
        eventService.deleteEvent(id);
    }
}
