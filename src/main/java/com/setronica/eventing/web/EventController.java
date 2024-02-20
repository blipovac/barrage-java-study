package com.setronica.eventing.web;

import com.setronica.eventing.app.EventService;
import com.setronica.eventing.persistence.Event;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    return eventService.getById(id);

  }

  @PostMapping()
  public Event createEvent(
      @RequestBody Event event
  ) {
    return eventService.create(event);
  }

  @PatchMapping("/{id}")
  public void updateEvent(
      @PathVariable Integer id,
      @RequestBody Event event
  ) {

    eventService.update(id, event);

  }

  @DeleteMapping("/{id}")
  public void deleteEvent(
      @PathVariable Integer id
  ) {

    eventService.delete(id);

  }

  @GetMapping("search")
  public List<Event> searchEvents(
      @RequestParam String q
  ) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
