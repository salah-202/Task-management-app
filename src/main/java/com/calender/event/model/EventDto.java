package com.calender.event.model;

import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calender_event")
public class EventDto implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "event_description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "start_date")
    private DateTime startDate;

    @Column(name = "end_date")
    private DateTime endDate;
    @Transient
    private String timeZone;
}
