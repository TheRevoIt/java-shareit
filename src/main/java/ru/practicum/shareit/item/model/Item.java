package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.item.ItemStatus;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

@Data
public class Item {
    private long id;
    private String name;
    private User owner;
    private String description;
    private ItemStatus itemStatus;
    private ItemRequest request;

    Item(String name, String description, ItemStatus status) {
        this.name = name;
        this.description = description;
        this.itemStatus = status;
    }
}
