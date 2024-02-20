package com.setronica.eventing.app;

import com.setronica.eventing.exceptions.EntityNotFoundException;
import com.setronica.eventing.persistence.EventSchedule;
import com.setronica.eventing.persistence.EventScheduleRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventScheduleService {
  private final EventScheduleRepository eventScheduleRepository;

  public EventScheduleService(EventScheduleRepository eventScheduleRepository) {
    this.eventScheduleRepository = eventScheduleRepository;
  }

  public List<EventSchedule> getAll() {
    log.debug("Fetching all event schedules");
    List<EventSchedule> eventSchedules = eventScheduleRepository.findAll();
    log.debug("Finished fetching all event schedules");

    return eventSchedules;
  }

  public EventSchedule getById(Integer id) {
    log.debug("Fetching event schedule with id: " + id);

    EventSchedule eventSchedule = eventScheduleRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("EventSchedule" + id + "not found"));

    log.debug("Finished fetching event schedule with id: " + id);

    return eventSchedule;
  }

  public EventSchedule create(EventSchedule eventSchedule) {
    log.debug("Creating event schedule");
    EventSchedule createdEventSchedule = eventScheduleRepository.save(eventSchedule);
    log.debug("Successfully created event schedule");

    return createdEventSchedule;
  }

  public void update(Integer id, EventSchedule eventSchedule) {
    log.debug("Updating event schedule with id: " + id);

    eventScheduleRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("EventSchedule" + id + "not found"));

    eventSchedule.setId(id);
    eventScheduleRepository.save(eventSchedule);

    log.debug("Successfully updated with id: " + id);
  }

  public void delete(Integer id) {
    log.debug("Deleting event schedule with id: " + id);
    eventScheduleRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("EventSchedule" + id + "not found"));
    eventScheduleRepository.deleteById(id);
    log.debug("Successfully deleted event schedule with id: " + id);
  }
}
