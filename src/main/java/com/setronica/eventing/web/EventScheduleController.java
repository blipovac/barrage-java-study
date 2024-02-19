package com.setronica.eventing.web;

import com.setronica.eventing.app.EventScheduleService;
import com.setronica.eventing.persistence.EventSchedule;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("event/api/v1/event-schedules")
public class EventScheduleController {
  private final EventScheduleService eventScheduleService;

  public EventScheduleController(EventScheduleService eventScheduleService) {
    this.eventScheduleService = eventScheduleService;
  }

  @GetMapping
  public List<EventSchedule> findAll() {
    return eventScheduleService.getAll();
  }

  @GetMapping("/{id}")
  public EventSchedule findById(
      @PathVariable Integer id
  ) {
    return eventScheduleService.getById(id);
  }

  @PostMapping()
  public EventSchedule createEventSchedule(
      @RequestBody EventSchedule eventSchedule
  ) {
    return eventScheduleService.create(eventSchedule);
  }

  @PatchMapping("/{id}")
  public void updateEventSchedule(
      @PathVariable Integer id,
      @RequestBody EventSchedule eventSchedule) {
    eventScheduleService.update(id, eventSchedule);
  }

  @DeleteMapping("/{id}")
    public void deleteEventSchedule(
        @PathVariable Integer id
    ) {
        eventScheduleService.delete(id);
    }
}
