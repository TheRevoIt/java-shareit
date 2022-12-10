package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

interface ItemRepository {

    Item add(Item item);

    void update(Item item);

    Optional<Item> getById(long itemId);

    List<Item> getAll();
}
