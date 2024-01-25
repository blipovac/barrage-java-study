package com.setronica.eventing.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventRepository {

    private final ObjectMapper objectMapper;
    private final String fileName = "/static/events.json";

    public EventRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Event> findAll() {
        return readEventsFromFile(fileName);
    }

    public List<Event> searchEvents(String q) {
        return readEventsFromFile(fileName).stream().filter(event -> event.getTitle().contains(q))
                .collect(Collectors.toList());
    }

    private List<Event> readEventsFromFile(String fileName) {
        InputStream inputStream = null;

        // i am unfamiliar with try() {} catch() {} syntax, so i used the old way
        // but would like to know more about the new one

        try {
            inputStream = EventRepository.class.getResourceAsStream(fileName);

            if (inputStream == null) {
                return Collections.emptyList();
            }

            return objectMapper.readValue(inputStream, EventList.class);
        } catch (Exception e) {
            e.printStackTrace();

            return Collections.emptyList();
        }
    }
}
