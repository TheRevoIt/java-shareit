package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    UserDto create(@Validated({Create.class}) @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PatchMapping(path = "/{userId}")
    UserDto update(@PathVariable long userId, @Validated({Update.class}) @RequestBody UserDto userDto) {
        return userService.update(userId, userDto);
    }

    @GetMapping(path = "/{userId}")
    UserDto getById(@PathVariable long userId) {
        return userService.getById(userId);
    }

    @DeleteMapping(path = "/{userId}")
    void deleteById(@PathVariable long userId) {
        userService.deleteById(userId);
    }

    @GetMapping
    List<UserDto> getAll() {
        return userService.getAll();
    }
}