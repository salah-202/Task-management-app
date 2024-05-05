package com.calender.event.controller;

import com.calender.event.model.EventDto;
import com.calender.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("v1/events")
public class EventController {
    private final EventService eventService;
    @Autowired
    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<String> addEvent(@RequestBody EventDto event) throws IOException {
        String addedEventId = eventService.addEvent(event);
        return new ResponseEntity<>(addedEventId, HttpStatus.CREATED);
    }
    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEventById(@PathVariable String eventId) throws IOException {
        return new ResponseEntity<>(eventService.getEvent(eventId), HttpStatus.OK);
    }
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEventById(@PathVariable String eventId) throws IOException {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/{eventId}")
    public ResponseEntity<Void> updateEventById(@PathVariable String eventId,@RequestBody EventDto eventDto) throws IOException {
        eventService.updateEvent(eventId,eventDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
