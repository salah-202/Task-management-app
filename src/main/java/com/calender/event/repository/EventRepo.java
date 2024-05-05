package com.calender.event.repository;

import com.calender.event.model.EventDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<EventDto,String> {
}
