package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
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
    UserDto create(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PatchMapping(path = "/{userId}")
    UserDto update(@RequestBody UserDto userDto,
                   @PathVariable long userId) {
        return userService.update(userDto, userId);
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
        return userService.findAll();
    }
}