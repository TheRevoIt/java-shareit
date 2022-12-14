package ru.practicum.shareit.user.dto;

import lombok.Data;
import ru.practicum.shareit.user.Create;
import ru.practicum.shareit.user.Update;
import ru.practicum.shareit.util.validation.UniqueEmailConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    private final long id;
    @NotBlank(groups = {Create.class})
    private final String name;
    @NotBlank(groups = {Create.class})
    @UniqueEmailConstraint(groups = {Create.class})
    @Email(groups = {Create.class, Update.class})
    private final String email;
}
