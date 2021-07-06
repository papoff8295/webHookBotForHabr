package ru.popov.telegrambot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event_cash")
@Getter
@Setter
//serves to save unhandled events after rebooting heroku
public class EventCashEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id", columnDefinition = "serial")
    private long id;

    @Column(name = "time")
    private Date date;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private long userId;

    public EventCashEntity() {
    }

    public static EventCashEntity eventTo(Date date, String description, long userId) {
        EventCashEntity eventCashEntity = new EventCashEntity();
        eventCashEntity.setDate(date);
        eventCashEntity.setDescription(description);
        eventCashEntity.setUserId(userId);
        return eventCashEntity;
    }
}
