package com.calender.event.mapper;

import com.calender.event.model.EventDto;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-05T17:17:49+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Private Build)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public EventDto toDto(Event event) {
        if ( event == null ) {
            return null;
        }

        EventDto eventDto = new EventDto();

        eventDto.setStartDate( eventStartDateTime( event ) );
        eventDto.setEndDate( eventEndDateTime( event ) );
        eventDto.setTimeZone( eventStartTimeZone( event ) );
        eventDto.setId( event.getId() );
        eventDto.setSummary( event.getSummary() );
        eventDto.setDescription( event.getDescription() );
        eventDto.setLocation( event.getLocation() );

        return eventDto;
    }

    @Override
    public Event toEntity(EventDto event) {
        if ( event == null ) {
            return null;
        }

        Event event1 = new Event();

        event1.setDescription( event.getDescription() );
        event1.setId( event.getId() );
        event1.setLocation( event.getLocation() );
        event1.setSummary( event.getSummary() );

        return event1;
    }

    private DateTime eventStartDateTime(Event event) {
        if ( event == null ) {
            return null;
        }
        EventDateTime start = event.getStart();
        if ( start == null ) {
            return null;
        }
        DateTime dateTime = start.getDateTime();
        if ( dateTime == null ) {
            return null;
        }
        return dateTime;
    }

    private DateTime eventEndDateTime(Event event) {
        if ( event == null ) {
            return null;
        }
        EventDateTime end = event.getEnd();
        if ( end == null ) {
            return null;
        }
        DateTime dateTime = end.getDateTime();
        if ( dateTime == null ) {
            return null;
        }
        return dateTime;
    }

    private String eventStartTimeZone(Event event) {
        if ( event == null ) {
            return null;
        }
        EventDateTime start = event.getStart();
        if ( start == null ) {
            return null;
        }
        String timeZone = start.getTimeZone();
        if ( timeZone == null ) {
            return null;
        }
        return timeZone;
    }
}
