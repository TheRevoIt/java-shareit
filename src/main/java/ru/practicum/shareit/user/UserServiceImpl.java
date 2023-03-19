package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserDto create(UserDto userDto) {
        User mappedUser = userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(mappedUser);
    }

    public UserDto update(UserDto userDto, long userId) {
        User loadedUser = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        User updatedUser = UserMapper.toUpdatedUser(userDto, loadedUser);
        userRepository.save(updatedUser);
        return UserMapper.toUserDto(updatedUser);
    }

    public UserDto getById(long userId) {
        User loadedUser = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        return UserMapper.toUserDto(loadedUser);
    }

    public void deleteById(long userId) {
        User loadedUser = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        userRepository.deleteById(userId);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}
