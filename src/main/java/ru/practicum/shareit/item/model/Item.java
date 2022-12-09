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
    private String name;
    @NotNull(groups = {Create.class})
    private User owner;
    @NotBlank(groups = {Create.class})
    private String description;
    @NotNull(groups = {Create.class})
    private ItemStatus itemStatus;
    @NotNull(groups = {Create.class})
    private ItemRequest request;

    public Item(String name, String description, ItemStatus status) {
        this.name = name;
        this.description = description;
        this.itemStatus = status;
    }
}
