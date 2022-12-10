package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    boolean uniqueEmailCheck(String email);

    User add(User user);

    void update(long userId, User user);

    Optional<User> getById(long userId);

    Optional<User> deleteById(long userId);

    List<User> getAll();
}
