package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    boolean uniqueEmailCheck(String email);

    User addUser(User user);

    User updateUser(long userId, User user);

    Optional<User> getUserById(long user);

    List<User> getUsers();
}
