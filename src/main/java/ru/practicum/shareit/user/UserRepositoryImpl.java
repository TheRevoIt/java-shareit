package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private HashMap<Long, User> users = new HashMap<>();
    private HashSet<String> emails = new HashSet<>();
    private long id = 1;
    // TODO: 16/11/2022 Обновление должно изменять email адрес 

    @Override
    public boolean uniqueEmailCheck(String email) {
        return !emails.contains(email);
    }

    @Override
    public User addUser(User user) {
        user.setId(id);
        emails.add(user.getEmail());
        users.put(id++, user);
        return user;
    }

    @Override
    public User updateUser(long userId, User user) {
        User userToUpdate = users.get(userId);
        if (Objects.nonNull(user.getEmail())) {
            emails.add(user.getEmail());
            userToUpdate.setEmail(user.getEmail());
        }
        if (Objects.nonNull(user.getName())) {
            userToUpdate.setName(user.getName());
        }
        return userToUpdate;
    }

    @Override
    public Optional<User> getUserById(long userId) {
        return Optional.of(users.get(userId));
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}
