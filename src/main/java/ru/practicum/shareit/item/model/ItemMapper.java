package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.model.User;

import static ru.practicum.shareit.item.ItemStatus.AVAILABLE;
import static ru.practicum.shareit.item.ItemStatus.BOOKED;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static Item toItem(ItemDto itemDto, User owner) {
        Item resultItem = new Item(
                itemDto.getName(), itemDto.getDescription(),
                itemDto.getAvailable() ? AVAILABLE : BOOKED);
        resultItem.setOwner(owner);
        return resultItem;
    }

    public static Item toUpdatedItem(ItemDto itemDto, Item existingItem, User owner) {
        Item resultItem = new Item(
                itemDto.getName() == null || itemDto.getName().isBlank() ? existingItem.getName() : itemDto.getName(),
                itemDto.getDescription() == null || itemDto.getDescription().isBlank() ?
                        existingItem.getDescription() : itemDto.getDescription(),
                itemDto.getAvailable() == null ? existingItem.getItemStatus() : (itemDto.getAvailable() ? AVAILABLE : BOOKED));
        resultItem.setId(existingItem.getId());
        resultItem.setOwner(owner);
        return resultItem;
    }

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(),
                item.getItemStatus() == AVAILABLE);
    }
}
