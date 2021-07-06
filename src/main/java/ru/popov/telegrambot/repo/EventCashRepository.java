package ru.popov.telegrambot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popov.telegrambot.entity.EventCashEntity;

public interface EventCashRepository extends JpaRepository<EventCashEntity, Long> {
    EventCashEntity findById(long id);
}
