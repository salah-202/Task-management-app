package com.calender.event.service;

import com.calender.event.mapper.EventMapper;
import com.calender.event.model.EventDto;
import com.calender.event.repository.EventRepo;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class EventService {
    private final EventRepo eventRepo;
    private final Calendar calendarService;
    @Autowired
    public EventService(EventRepo eventRepo,Calendar calendarService){
        this.eventRepo = eventRepo;
        this.calendarService = calendarService;
    }
    @Transient
    public String addEvent(EventDto event) throws IOException {
        Event addedEvent = EventMapper.INSTANCE.toEntity(event);

        EventDateTime start = convertToEventDateTime(event.getStartDate(),event.getTimeZone());
        addedEvent.setStart(start);

        EventDateTime end = convertToEventDateTime(event.getEndDate(),event.getTimeZone());
        addedEvent.setEnd(end);

        Event createdEvent = calendarService.events().insert("primary", addedEvent).execute();
        event.setId(createdEvent.getId());
        return eventRepo.save(event).getId();
    }
    @Cacheable(value = "eventDto",key = "#eventId")
    public EventDto getEvent(String eventId) throws IOException {
        Event event = calendarService.events().get("primary", eventId).execute();
        return EventMapper.INSTANCE.toDto(event);
    }

    @Transient
    @CacheEvict(cacheNames = "eventDto",key = "#eventId")
    public void deleteEvent(String eventId) throws IOException{
        calendarService.events().delete("primary", eventId).execute();
        eventRepo.deleteById(eventId);
    }
    @Transient
    @Cacheable(cacheNames = "eventDto",key = "#eventId")
    public void updateEvent(String eventId,EventDto eventDto) throws IOException{
        Event event = calendarService.events().get("primary", eventId).execute();
        EventDto updatedEvent = eventRepo.getReferenceById(eventId);

        if(Objects.nonNull(eventDto.getSummary())) {
            event.setSummary(eventDto.getSummary());
            updatedEvent.setSummary(eventDto.getSummary());
        }
        if(Objects.nonNull(eventDto.getDescription())){
            event.setDescription(eventDto.getDescription());
            updatedEvent.setDescription(eventDto.getDescription());
        }
        if(Objects.nonNull(eventDto.getLocation())){
            event.setLocation(eventDto.getLocation());
            updatedEvent.setLocation(eventDto.getLocation());
        }
        if(Objects.nonNull(eventDto.getStartDate())) {
            event.setStart(convertToEventDateTime(eventDto.getStartDate(),event.getStart().getTimeZone()));
            updatedEvent.setStartDate(eventDto.getStartDate());
        }
        if(Objects.nonNull(eventDto.getEndDate())) {
            event.setEnd(convertToEventDateTime(eventDto.getEndDate(),event.getStart().getTimeZone()));
            updatedEvent.setEndDate(eventDto.getEndDate());
        }
        if(Objects.nonNull(eventDto.getTimeZone())) {
            event.getStart().setTimeZone(eventDto.getTimeZone());
            event.getEnd().setTimeZone(eventDto.getTimeZone());
            updatedEvent.setTimeZone(eventDto.getTimeZone());
        }

        calendarService.events().update("primary", eventId,event).execute();
        eventRepo.save(updatedEvent);
    }
    private EventDateTime convertToEventDateTime(DateTime date,String timeZone){
        return new EventDateTime()
                .setDateTime(date)
                .setTimeZone(timeZone);
    }

}
