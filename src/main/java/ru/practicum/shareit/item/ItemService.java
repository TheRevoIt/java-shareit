package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.util.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public ItemDto createItem(ItemDto itemDto, long userId) {
        User owner = userRepository.getUserById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
        Item mappedItem = itemRepository.addItem(ItemMapper.toItem(itemDto, owner));
        return ItemMapper.toItemDto(mappedItem);
    }

    public ItemDto updateItem(long itemId, ItemDto itemDto) {
        User owner = itemRepository.getItemById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден",itemId)))
                .getOwner();
        Item mappedItem = ItemMapper.toItem(itemDto, owner);
        mappedItem.setId(itemId);
        itemRepository.updateItem(itemId, mappedItem);
        return itemDto;
    }
}