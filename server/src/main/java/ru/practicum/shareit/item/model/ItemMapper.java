package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemAndBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static Item toItem(ItemDto itemDto, User owner, ItemRequest itemRequest) {
        Item resultItem = new Item(
                itemDto.getName(), itemDto.getDescription(),
                itemDto.getAvailable());
        resultItem.setOwner(owner);
        if (itemRequest != null) {
            resultItem.setItemRequest(itemRequest);
        }
        return resultItem;
    }

    public static void toUpdatedItem(ItemDto itemDto, Item existingItem, User owner) {
        if (!(itemDto.getName() == null || itemDto.getName().isBlank())) {
            existingItem.setName(itemDto.getName());
        }
        if (!(itemDto.getDescription() == null || itemDto.getDescription().isBlank())) {
            existingItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            existingItem.setAvailable(itemDto.getAvailable());
        }
        existingItem.setOwner(owner);
    }

    public static ItemDto toItemDto(Item item) {
        ItemDto resultDto = new ItemDto(item.getId(), item.getName(), item.getDescription(),
                item.isAvailable());
        if (item.getItemRequest() != null) {
            resultDto.setRequestId(item.getItemRequest().getId());
        }
        return resultDto;
    }

    public static ItemAndBookingDto toItemAndBookingDto(Item item) {
        return new ItemAndBookingDto(item.getId(), item.getName(), item.getDescription(),
                item.isAvailable(), null, null, new ArrayList<>());
    }
}
