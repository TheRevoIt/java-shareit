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

    static User toUpdatedUser(UserDto userDto, User existingUser) {
        User resultUser = new User(
                userDto.getName() == null || userDto.getName().isBlank() ? existingUser.getName() : userDto.getName(),
                userDto.getEmail() == null || userDto.getEmail().isBlank() ? existingUser.getEmail() : userDto.getEmail());
        resultUser.setId(existingUser.getId());
        return resultUser;
    }

    static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
