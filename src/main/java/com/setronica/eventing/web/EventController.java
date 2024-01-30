package com.setronica.eventing.web;

import com.setronica.eventing.app.EventService;
import com.setronica.eventing.persistence.Event;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("event/api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> findAll() {
        return eventService.getAll();
    }

    @GetMapping("/{id}")
    public Event findById(
            @PathVariable Integer id
    ) {
        // different kinds of errors can occur here
        // how would we handle them in the controller?
        // would we have a bunch of catch block for each kind of exception?
        // e.g. one for not found, one for database error, one for every kind of business logic error?
        try {
            return eventService.getById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found", e);
        }
    }

    @PostMapping()
    public Event createEvent(
            @RequestBody Event event
    ) {
        // validation?
        return eventService.create(event);
    }

    @PatchMapping("/{id}")
    public void updateEvent(
            @PathVariable Integer id,
            @RequestBody Event event
    ) {
        try {
            eventService.update(id, event);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found", e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(
            @PathVariable Integer id
    ) {
        try {
            eventService.delete(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found", e);
        }
    }

    @GetMapping("search")
    public List<Event> searchEvents(
            @RequestParam String q
    ) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
