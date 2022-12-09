package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

class UserMapper {
    public static User toUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getEmail());
    }

    public static User toUpdatedUser(UserDto userDto, User existingUser) {
        User resultUser = new User(
                userDto.getName() == null ? existingUser.getName() : userDto.getName(),
                userDto.getEmail() == null ? existingUser.getEmail() : userDto.getEmail());
        resultUser.setId(existingUser.getId());
        return resultUser;
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
