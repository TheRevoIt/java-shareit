package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final HashMap<Long, User> users = new HashMap<>();
    private final HashMap<Long, String> emails = new HashMap<>();
    private long id = 1;

    @Override
    public boolean uniqueEmailCheck(String email) {
        return !emails.containsValue(email);
    }

    @Override
    public User addUser(User user) {
        user.setId(id);
        emails.put(user.getId(), user.getEmail());
        users.put(id++, user);
        return user;
    }

    @Override
    public void updateUser(long userId, User user) {
        User userToUpdate = users.get(userId);
        if (Objects.nonNull(user.getEmail())) {
            emails.put(userId, user.getEmail());
            userToUpdate.setEmail(user.getEmail());
        }
        if (Objects.nonNull(user.getName())) {
            userToUpdate.setName(user.getName());
        }
    }

    @Override
    public Optional<User> getUserById(long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public Optional<User> deleteUserById(long userId) {
        if (users.containsKey(userId)) {
            User loadedUser = new User(users.get(userId).getName(), users.get(userId).getName());
            emails.remove(userId);
            users.remove(userId);
            return Optional.of(loadedUser);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}