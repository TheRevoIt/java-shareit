package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.util.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto create(UserDto userDto) {
        User mappedUser = userRepository.add(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(mappedUser);
    }

    public UserDto update(long userId, UserDto userDto) {
        User loadedUser = userRepository.getById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
        User mappedUser = UserMapper.toUpdatedUser(userDto, loadedUser);
        userRepository.update(userId, mappedUser);
        return UserMapper.toUserDto(mappedUser);
    }

    public UserDto getById(long userId) {
        User loadedUser = userRepository.getById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
        return UserMapper.toUserDto(loadedUser);
    }

    public void deleteById(long userId) {
        userRepository.deleteById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
    }

    public List<UserDto> getAll() {
        return userRepository.getAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}
