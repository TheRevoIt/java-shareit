package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.util.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class ItemService {
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
        mappedItem.setOwner(owner);
        return ItemMapper.toItemDto(mappedItem);
    }

    public ItemDto updateItem(ItemDto itemDto, long itemId, long userId) {
        User owner = userRepository.getUserById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
        Item loadedItem = validateOwner(userId, itemId);
        Item mappedItem = ItemMapper.toUpdatedItem(itemDto, loadedItem, owner);
        itemRepository.updateItem(mappedItem);
        return ItemMapper.toItemDto(mappedItem);
    }

    private Item validateOwner(long userId, long itemId) {
        Item loadedItem = itemRepository.getItemById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Предмет с id=%x не найден", itemId)));
        if (loadedItem.getOwner().getId() != userId) {
            throw new NotFoundException(String.format("Пользователь с id=%x не является владельцем данного предмета", userId));
        }
        return loadedItem;
    }

    public ItemDto getItemById(long itemId) {
        return ItemMapper.toItemDto(itemRepository.getItemById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Предмет с id=%x не найден", itemId))));
    }

    public List<ItemDto> getAllItems(long ownerId) {
        return itemRepository.getItems().stream().filter(item -> item.getOwner().getId() == ownerId)
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    public List<ItemDto> searchItems(String text) {
        String searchText = text.toLowerCase();
        if (searchText.length() < 1) {
            return new ArrayList<>();
        }
        return itemRepository.getItems().stream().filter(item ->
                        (item.getDescription().toLowerCase().contains(searchText) ||
                                item.getName().toLowerCase().contains(searchText)) &&
                                item.getItemStatus() == ItemStatus.AVAILABLE)
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }
}