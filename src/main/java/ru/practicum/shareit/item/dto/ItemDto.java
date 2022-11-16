package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.user.Create;
import ru.practicum.shareit.user.Update;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    //@NotBlank(groups = {Update.class})
    //private final long id;
    @NotBlank(groups = {Create.class, Update.class})
    private final String name;
    //@NotNull(groups = {Create.class})
    private User owner;
    @NotBlank(groups = {Create.class, Update.class})
    private final String description;
    @NotNull(groups = {Create.class, Update.class})
    private final boolean available;
}
