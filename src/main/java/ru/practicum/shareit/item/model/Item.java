package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.item.ItemStatus;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.Create;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {
    private long id;
    @NotBlank(groups = {Create.class})
    private final String name;
    @NotNull(groups = {Create.class})
    private User owner;
    @NotBlank(groups = {Create.class})
    private final String description;
    @NotNull(groups = {Create.class})
    private final ItemStatus itemStatus;
    @NotNull(groups = {Create.class})
    private ItemRequest request;
}
