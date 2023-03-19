package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemAndBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static Item toItem(ItemDto itemDto, User owner) {
        Item resultItem = new Item(
                itemDto.getName(), itemDto.getDescription(),
                itemDto.getAvailable());
        resultItem.setOwner(owner);
        return resultItem;
    }

    public static Item toUpdatedItem(ItemDto itemDto, Item existingItem, User owner) {
        Item resultItem = new Item(
                itemDto.getName() == null || itemDto.getName().isBlank() ? existingItem.getName() : itemDto.getName(),
                itemDto.getDescription() == null || itemDto.getDescription().isBlank() ?
                        existingItem.getDescription() : itemDto.getDescription(),
                itemDto.getAvailable() == null ? existingItem.isAvailable() : (itemDto.getAvailable()));
        resultItem.setId(existingItem.getId());
        resultItem.setOwner(owner);
        return resultItem;
    }

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(),
                item.isAvailable());
    }

    public static ItemAndBookingDto toItemAndBookingDto(Item item) {
        return new ItemAndBookingDto(item.getId(), item.getName(), item.getDescription(),
                item.isAvailable(), null, null);
    }
}
