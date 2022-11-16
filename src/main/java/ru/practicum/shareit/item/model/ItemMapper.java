package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.model.User;

import static ru.practicum.shareit.item.ItemStatus.AVAILABLE;
import static ru.practicum.shareit.item.ItemStatus.BOOKED;

public class ItemMapper {
    public static Item toItem(ItemDto itemDto, User owner) {
        Item resultItem = new Item(itemDto.getName(), itemDto.getDescription(),
                itemDto.isAvailable() ? AVAILABLE : BOOKED);
        resultItem.setOwner(owner);
        return resultItem;
    }

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(item.getName(), item.getDescription(),
                item.getItemStatus() == AVAILABLE);
    }
}
