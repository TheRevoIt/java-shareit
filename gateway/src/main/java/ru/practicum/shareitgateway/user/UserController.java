package ru.practicum.shareitgateway.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareitgateway.user.dto.UserDto;
import ru.practicum.shareitgateway.util.Create;
import ru.practicum.shareitgateway.util.Update;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserClient userClient;

    @PostMapping
    ResponseEntity<Object> create(@Validated({Create.class}) @RequestBody UserDto userDto) {
        return userClient.create(userDto);
    }

    @PatchMapping(path = "/{userId}")
    ResponseEntity<Object> update(@Validated({Update.class}) @RequestBody UserDto userDto,
                   @PathVariable long userId) {
        return userClient.update(userDto, userId);
    }

    @GetMapping(path = "/{userId}")
    ResponseEntity<Object> getById(@PathVariable long userId) {
        return userClient.getById(userId);
    }

    @DeleteMapping(path = "/{userId}")
    void deleteById(@PathVariable long userId) {
        userClient.deleteById(userId);
    }

    @GetMapping
    ResponseEntity<Object> getAll() {
        return userClient.findAll();
    }
}