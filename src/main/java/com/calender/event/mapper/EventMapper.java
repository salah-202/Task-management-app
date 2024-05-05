package com.calender.event.mapper;

import com.calender.event.model.EventDto;
import com.google.api.services.calendar.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(source = "event.start.dateTime",target = "startDate")
    @Mapping(source = "event.end.dateTime",target = "endDate")
    @Mapping(source = "event.start.timeZone",target = "timeZone")
    EventDto toDto(Event event);

    Event toEntity(EventDto event);
}
