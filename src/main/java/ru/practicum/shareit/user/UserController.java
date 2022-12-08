package ru.practicum.shareit.user;

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

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {
   UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    UserDto createUser(@Validated({Create.class}) @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PatchMapping(path = "/{userId}")
    UserDto updateUser(@PathVariable long userId, @Validated({Update.class}) @RequestBody UserDto userDto) {
        return userService.updateUser(userId, userDto);
    }

    @GetMapping(path = "/{userId}")
    UserDto userGetById(@PathVariable long userId) {
       return userService.getUserById(userId);
    }

    @DeleteMapping(path = "/{userId}")
    void userDeleteById(@PathVariable long userId) {
        userService.deleteUserById(userId);
    }

    @GetMapping
    List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }


}