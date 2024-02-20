package com.setronica.eventing.app;

import com.setronica.eventing.exceptions.EntityNotFoundException;
import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventService {

  private final EventRepository eventRepository;

  public EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public List<Event> getAll() {
    log.debug("Fetching all events");
    List<Event> eventSchedules = eventRepository.findAll();
    log.debug("Finished fetching all events");

    return eventSchedules;
  }

  public Event getById(Integer id) throws NoSuchElementException {
    log.debug("Fetching event with id: " + id);

    Event eventSchedule = eventRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Event" + id + "not found"));

    log.debug("Finished fetching event with id: " + id);

    return eventSchedule;
  }

  public Event create(Event event) {
    log.debug("Creating event");
    Event createdEvent = eventRepository.save(event);
    log.debug("Successfully created event");

    return createdEvent;
  }

  public void update(Integer id, Event event) throws NoSuchElementException {
    log.debug("Updating event with id: " + id);

    eventRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Event" + id + "not found"));

    event.setId(id);
    eventRepository.save(event);

    log.debug("Successfully updated event with id: " + id);
  }

  public void delete(Integer id) throws NoSuchElementException {
    log.debug("Deleting event with id: " + id);
    eventRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Event" + id + "not found"));
    eventRepository.deleteById(id);
    log.debug("Successfully deleted event with id: " + id);
  }
}
