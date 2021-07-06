package ru.popov.telegrambot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popov.telegrambot.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByEventId(long id);
}
