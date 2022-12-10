package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.util.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemDto create(ItemDto itemDto, long userId) {
        User owner = userRepository.getById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
        Item mappedItem = itemRepository.add(ItemMapper.toItem(itemDto, owner));
        mappedItem.setOwner(owner);
        return ItemMapper.toItemDto(mappedItem);
    }

    public ItemDto update(ItemDto itemDto, long itemId, long userId) {
        User owner = userRepository.getById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%x не найден", userId)));
        Item loadedItem = validateOwner(userId, itemId);
        Item mappedItem = ItemMapper.toUpdatedItem(itemDto, loadedItem, owner);
        itemRepository.update(mappedItem);
        return ItemMapper.toItemDto(mappedItem);
    }

    ItemDto getById(long itemId) {
        return ItemMapper.toItemDto(itemRepository.getById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Предмет с id=%x не найден", itemId))));
    }

    List<ItemDto> getAll(long ownerId) {
        return itemRepository.getAll().stream().filter(item -> item.getOwner().getId() == ownerId)
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    List<ItemDto> search(String text) {
        String searchText = text.toLowerCase();
        return itemRepository.getAll().stream().filter(item ->
                        (item.getDescription().toLowerCase().contains(searchText) ||
                                item.getName().toLowerCase().contains(searchText)) &&
                                item.getItemStatus() == ItemStatus.AVAILABLE)
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    private Item validateOwner(long userId, long itemId) {
        Item loadedItem = itemRepository.getById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Предмет с id=%x не найден", itemId)));
        if (loadedItem.getOwner().getId() != userId) {
            throw new NotFoundException(String.format("Пользователь с id=%x не является владельцем данного предмета", userId));
        }
        return loadedItem;
    }
}