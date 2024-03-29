package ru.practicum.shareit.item;

import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemAndBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

interface ItemService {
    ItemDto create(ItemDto itemDto, long userId);

    ItemDto update(ItemDto itemDto, long itemId, long userId);

    ItemAndBookingDto getById(long itemId, long userId);

    List<ItemAndBookingDto> getAll(long ownerId, PageRequest pageRequest);

    List<ItemDto> search(String text, PageRequest pageRequest);

    CommentDto createComment(CommentDto commentDto, long itemId, long userId);
}
