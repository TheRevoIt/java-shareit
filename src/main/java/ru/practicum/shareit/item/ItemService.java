package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemAndBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(ItemDto itemDto, long userId);

    ItemDto update(ItemDto itemDto, long itemId, long userId);

    ItemAndBookingDto getById(long itemId, long userId);

    List<ItemAndBookingDto> getAll(long ownerId);

    List<ItemDto> search(String text);
}
