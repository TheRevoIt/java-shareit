package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

interface ItemRepository {

    Item addItem(Item item);

    void updateItem(Item item);

    Optional<Item> getItemById(long itemId);

    List<Item> getItems();
}
