package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.util.exception.NotUniqueEmailException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@Service
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto userDto) {
        User mappedUser = userRepository.addUser(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(mappedUser);
    }

    public UserDto updateUser(long userId, UserDto userDto) {
        User mappedUser = userRepository.updateUser(userId, UserMapper.toUser(userDto));
        return UserMapper.toUserDto(mappedUser);
    }
}
