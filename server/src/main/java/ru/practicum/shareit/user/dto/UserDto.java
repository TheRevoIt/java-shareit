package ru.practicum.shareit.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UserDto {
    private final long id;
    private final String name;
    @EqualsAndHashCode.Include
    private final String email;
}
