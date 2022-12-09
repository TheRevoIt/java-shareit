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

    public UserDto createUser(UserDto userDto) {
        User mappedUser = userRepository.addUser(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(mappedUser);
    }

    public UserDto updateUser(long userId, UserDto userDto) {
        User loadedUser = userRepository.getUserById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
        User mappedUser = UserMapper.toUpdatedUser(userDto, loadedUser);
        userRepository.updateUser(userId, mappedUser);
        return UserMapper.toUserDto(mappedUser);
    }

    public UserDto getUserById(long userId) {
        User loadedUser = userRepository.getUserById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
        return UserMapper.toUserDto(loadedUser);
    }

    public void deleteUserById(long userId) {
        userRepository.deleteUserById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.getUsers().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}
