package ru.popov.telegrambot.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.popov.telegrambot.entity.Event;
import ru.popov.telegrambot.entity.User;
import ru.popov.telegrambot.repo.EventRepository;
import ru.popov.telegrambot.repo.UserRepository;

import java.util.List;

@Service
public class EventDAO {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventDAO(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public List<Event> findByUserId(long userId) {
        User user = userRepository.findById(userId);
        return user.getEvents();
    }
    public List<Event> findAllEvent() {
       return eventRepository.findAll();
    }

    public Event findByEventId(long eventId) {
        return eventRepository.findByEventId(eventId);
    }

    public void remove(Event event) {
        eventRepository.delete(event);
    }

    public void save(Event event) {
        eventRepository.save(event);
    }
}
