package ru.popov.telegrambot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popov.telegrambot.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);
}
