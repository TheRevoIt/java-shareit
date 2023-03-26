package ru.practicum.shareit.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class UserMapper {
    static User toUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getEmail());
    }

    static void toUpdatedUser(UserDto userDto, User existingUser) {
        if (!(userDto.getName() == null || userDto.getName().isBlank())) {
            existingUser.setName(userDto.getName());
        }
        if (!(userDto.getEmail() == null || userDto.getEmail().isBlank())) {
            existingUser.setEmail(userDto.getEmail());
        }
    }

    static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
