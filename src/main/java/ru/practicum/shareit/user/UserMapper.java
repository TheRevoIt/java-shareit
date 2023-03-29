package ru.practicum.shareit.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static User toUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getEmail());
    }

    public static void toUpdatedUser(UserDto userDto, User existingUser) {
        if (!(userDto.getName() == null || userDto.getName().isBlank())) {
            existingUser.setName(userDto.getName());
        }
        if (!(userDto.getEmail() == null || userDto.getEmail().isBlank())) {
            existingUser.setEmail(userDto.getEmail());
        }
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
