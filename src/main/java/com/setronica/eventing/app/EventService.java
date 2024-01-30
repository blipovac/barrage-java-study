package com.setronica.eventing.app;

import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public Event getById(Integer id) throws NoSuchElementException {
        return eventRepository.findById(id).orElseThrow();
    }

    public Event create(Event event) {
        return eventRepository.save(event);
    }

    public void update(Integer id, Event event) throws NoSuchElementException {
        eventRepository.findById(id).orElseThrow();
        event.setId(id);
        eventRepository.save(event);
    }

    public void delete(Integer id) throws NoSuchElementException {
        eventRepository.findById(id).orElseThrow();
        eventRepository.deleteById(id);
    }
}
