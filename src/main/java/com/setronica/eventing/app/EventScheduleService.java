package com.setronica.eventing.app;

import com.setronica.eventing.exceptions.EntityNotFoundException;
import com.setronica.eventing.persistence.EventSchedule;
import com.setronica.eventing.persistence.EventScheduleRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EventScheduleService {
  private final EventScheduleRepository eventScheduleRepository;

  public EventScheduleService(EventScheduleRepository eventScheduleRepository) {
    this.eventScheduleRepository = eventScheduleRepository;
  }

  public List<EventSchedule> getAll() {
    return eventScheduleRepository.findAll();
  }

  public EventSchedule getById(Integer id) {
    return eventScheduleRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("EventSchedule" + id + "not found"));
  }

  public EventSchedule create(EventSchedule eventSchedule) {
    return eventScheduleRepository.save(eventSchedule);
  }

  public void update(Integer id, EventSchedule eventSchedule) {
    eventScheduleRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("EventSchedule" + id + "not found"));
    eventSchedule.setId(id);
    eventScheduleRepository.save(eventSchedule);
  }

  public void delete(Integer id) {
    eventScheduleRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("EventSchedule" + id + "not found"));
    eventScheduleRepository.deleteById(id);
  }
}
