package ru.popov.telegrambot.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.popov.telegrambot.model.EventFreq;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "user_events")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "event_id", columnDefinition = "serial")
    private int eventId;

    @Column(name = "time")
    @NotNull(message = "Need date!")
    private Date date;

    @Column(name = "description")
    @Size(min = 4, max = 200, message = "Description must be between 0 and 200 chars!")
    private String description;

    @Column(name = "event_freq", columnDefinition = "TIME")
    @Enumerated(EnumType.STRING)
    private EventFreq freq;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Event() {
    }

    public Event(int eventId,
                 @NotNull(message = "Need date!") Date date,
                 @Size(min = 4, max = 200, message = "Description must be between 0 and 200 chars!")
                         String description,
                 EventFreq freq, User user) {
        this.eventId = eventId;
        this.date = date;
        this.description = description;
        this.freq = freq;
        this.user = user;
    }
}
