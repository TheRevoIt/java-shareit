package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.dto.BookingDtoShortResponse;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemAndBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentMapper;
import ru.practicum.shareit.item.model.CommentRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.exception.CommentException;
import ru.practicum.shareit.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto create(ItemDto itemDto, long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        Item mappedItem = itemRepository.save(ItemMapper.toItem(itemDto, owner));
        mappedItem.setOwner(owner);
        return ItemMapper.toItemDto(mappedItem);
    }

    @Transactional
    @Override
    public ItemDto update(ItemDto itemDto, long itemId, long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        Item loadedItem = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Предмет с id=%d не найден", itemId)));
        ItemMapper.toUpdatedItem(itemDto, loadedItem, owner);
        return ItemMapper.toItemDto(loadedItem);
    }

    @Override
    public ItemAndBookingDto getById(long itemId, long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Предмет с id=%d не найден", itemId)));
        return getItemAndBookingAndComments(userId, List.of(item)).get(0);
    }

    @Override
    public List<ItemAndBookingDto> getAll(long ownerId) {
        User owner = userRepository.findById(ownerId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", ownerId)));
        List<Item> results = itemRepository.findAll().stream().filter(item -> item.getOwner().getId() == ownerId)
                .sorted(Comparator.comparing(Item::getId))
                .collect(Collectors.toList());
        return getItemAndBookingAndComments(ownerId, results);
    }

    @Override
    public List<ItemDto> search(String text) {
        String searchText = text.toLowerCase();
        return itemRepository.findAll().stream().filter(item ->
                        (item.getDescription().toLowerCase().contains(searchText) ||
                                item.getName().toLowerCase().contains(searchText)) &&
                                item.isAvailable())
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto createComment(CommentDto commentDto, long itemId, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Предмет с id=%d не найден", itemId)));
        Comment comment = CommentMapper.dtoToComment(commentDto, user, item);

        if (!bookingRepository.existsByBookerIdAndItemIdAndEndBefore(userId, itemId,
                LocalDateTime.now())) {
            throw new CommentException("The user can not leave a comment to this item");
        }
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());
        comment.setItem(item);
        commentRepository.save(comment);
        return CommentMapper.commentToDto(comment);
    }

    private List<ItemAndBookingDto> getItemAndBookingAndComments(long userId, List<Item> items) {
        List<Long> itemIds = items.stream().map(Item::getId).collect(Collectors.toList());
        LocalDateTime currentTime = LocalDateTime.now();
        Map<Long, BookingDtoShortResponse> previous = bookingRepository.findBookingsByListOfItemsLast(itemIds,
                        currentTime, userId)
                .stream()
                .map(BookingMapper::toBookingDtoShortResponse)
                .collect(Collectors.toMap(BookingDtoShortResponse::getItemId, item -> item, (first, second) -> first));
        Map<Long, BookingDtoShortResponse> next = bookingRepository.findBookingsByListOfItemsNext(itemIds,
                        currentTime, userId)
                .stream()
                .map(BookingMapper::toBookingDtoShortResponse)
                .collect(Collectors.toMap(BookingDtoShortResponse::getItemId, item -> item, (first, second) -> first));
        List<ItemAndBookingDto> results = items.stream()
                .map(ItemMapper::toItemAndBookingDto)
                .collect(Collectors.toList());
        Map<Long, List<Comment>> comments = commentRepository.findByItemId_IdIn(itemIds).stream().collect(Collectors.groupingBy(comment ->
                comment.getItem().getId()));
        results.forEach(item -> {
            item.setLastBooking(previous.get(item.getId()));
            item.setNextBooking(next.get(item.getId()));
            item.getComments().addAll(comments.getOrDefault(item.getId(), List.of()).stream()
                    .map(CommentMapper::commentToDto).collect(Collectors.toList()));
        });
        return results;
    }
}