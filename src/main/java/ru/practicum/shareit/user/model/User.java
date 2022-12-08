package ru.practicum.shareit.user.model;

import lombok.Data;
import ru.practicum.shareit.user.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class User {
    private long id;
    @NotBlank(groups = {Create.class})
    private String name;
    @Email(groups = {Create.class})
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}