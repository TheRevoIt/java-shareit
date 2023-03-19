package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

interface UserService {
    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto, long userId);

//    UserDto update(long userId, UserDto userDto) {
//        User loadedUser = userRepository.getById(userId).orElseThrow(() ->
//                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
//        User mappedUser = UserMapper.toUpdatedUser(userDto, loadedUser);
//        userRepository.update(userId, mappedUser);
//        return UserMapper.toUserDto(mappedUser);
//    }

    UserDto getById(long userId);

    void deleteById(long userId);

    List<UserDto> findAll();
}
