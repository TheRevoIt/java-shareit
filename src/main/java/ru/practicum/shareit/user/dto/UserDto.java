package ru.practicum.shareit.user.dto;

import lombok.Data;
import ru.practicum.shareit.util.Create;
import ru.practicum.shareit.util.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    private final long id;
    @NotBlank(groups = {Create.class})
    @Size(max = 255)
    private final String name;
    @NotBlank(groups = {Create.class})
    @Email(groups = {Create.class, Update.class})
    @Size(max = 512)
    private final String email;
}
