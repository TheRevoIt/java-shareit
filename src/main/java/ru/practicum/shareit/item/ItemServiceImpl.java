package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.dto.BookingDtoShortResponse;
import ru.practicum.shareit.item.dto.ItemAndBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public ItemDto create(ItemDto itemDto, long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        Item mappedItem = itemRepository.save(ItemMapper.toItem(itemDto, owner));
        mappedItem.setOwner(owner);
        return ItemMapper.toItemDto(mappedItem);
    }

    @Override
    public ItemDto update(ItemDto itemDto, long itemId, long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        Item loadedItem = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Предмет с id=%d не найден", itemId)));
        Item updatedItem = ItemMapper.toUpdatedItem(itemDto, loadedItem, owner);
        itemRepository.save(updatedItem);
        return ItemMapper.toItemDto(updatedItem);
    }

    public ItemAndBookingDto getById(long itemId, long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Предмет с id=%d не найден", itemId)));
        return getItemAndBookingDto(userId, List.of(item)).get(0);
    }

    public List<ItemAndBookingDto> getAll(long ownerId) {
        User owner = userRepository.findById(ownerId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", ownerId)));
        List<Item> results = itemRepository.findAll().stream().filter(item -> item.getOwner().getId() == ownerId)
                .sorted(Comparator.comparing(Item::getId))
                .collect(Collectors.toList());
        return getItemAndBookingDto(ownerId, results);
    }

    public List<ItemDto> search(String text) {
        String searchText = text.toLowerCase();
        return itemRepository.findAll().stream().filter(item ->
                        (item.getDescription().toLowerCase().contains(searchText) ||
                                item.getName().toLowerCase().contains(searchText)) &&
                                item.isAvailable())
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    private List<ItemAndBookingDto> getItemAndBookingDto(long userId, List<Item> items) {
        List<Long> ids = items.stream().map(Item::getId).collect(Collectors.toList());
        LocalDateTime currentTime = LocalDateTime.now();
        Map<Long, BookingDtoShortResponse> previous = bookingRepository.findBookingsByListOfItemsLast(ids,
                        currentTime, userId )
                .stream()
                .map(BookingMapper::toBookingDtoShortResponse)
                .collect(Collectors.toMap(BookingDtoShortResponse::getItemId, item -> item, (first, second) -> first));
        Map<Long, BookingDtoShortResponse> next = bookingRepository.findBookingsByListOfItemsNext(ids,
                        currentTime, userId)
                .stream()
                .map(BookingMapper::toBookingDtoShortResponse)
                .collect(Collectors.toMap(BookingDtoShortResponse::getItemId, item -> item, (first, second) -> first));
        List<ItemAndBookingDto> results = items.stream()
                .map(ItemMapper::toItemAndBookingDto)
                .collect(Collectors.toList());
        results.forEach(item -> {
            item.setLastBooking(previous.get(item.getId()));
            item.setNextBooking(next.get(item.getId()));
        });
        return results;
    }
}
