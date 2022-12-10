package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.util.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class UserService {
    private final UserRepository userRepository;

    public UserDto create(UserDto userDto) {
        User mappedUser = userRepository.add(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(mappedUser);
    }

    public UserDto update(long userId, UserDto userDto) {
        User loadedUser = userRepository.getById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
        User mappedUser = UserMapper.toUpdatedUser(userDto, loadedUser);
        if (!Objects.equals(loadedUser.getEmail(), mappedUser.getEmail())) {
            if (!userRepository.uniqueEmailCheck(mappedUser.getEmail())) {
                throw new IllegalArgumentException("При обновлении пользователя использован повторяющийся email");
            }
        }
        userRepository.update(userId, mappedUser);
        return UserMapper.toUserDto(mappedUser);
    }

    UserDto getById(long userId) {
        User loadedUser = userRepository.getById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
        return UserMapper.toUserDto(loadedUser);
    }

    void deleteById(long userId) {
        userRepository.deleteById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
    }

    List<UserDto> getAll() {
        return userRepository.getAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}
