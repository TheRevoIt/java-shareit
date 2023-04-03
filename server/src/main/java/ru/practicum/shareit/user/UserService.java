package ru.practicum.shareit.user;

import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

interface UserService {
    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto, long userId);

    UserDto getById(long userId);

    ResponseEntity<Object> deleteById(long userId);

    List<UserDto> findAll();
}
