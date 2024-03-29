package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemAndBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.util.Create;
import ru.practicum.shareit.util.Update;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Validated
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                   @Validated({Create.class}) @RequestBody ItemDto itemDto) {
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                   @PathVariable long itemId,
                   @Validated({Update.class}) @RequestBody ItemDto itemDto) {
        return itemService.update(itemDto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    ItemAndBookingDto getById(@RequestHeader("X-Sharer-User-Id") long userId,
                              @PathVariable long itemId) {
        return itemService.getById(itemId, userId);
    }

    @GetMapping
    List<ItemAndBookingDto> getAllItems(@RequestHeader("X-Sharer-User-Id") long userId,
                                        @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                        @Positive @RequestParam(defaultValue = "5") int size) {
        return itemService.getAll(userId, PageRequest.of(from / size, size));
    }

    @GetMapping("/search")
    List<ItemDto> search(@RequestParam(name = "text") String text,
                         @RequestHeader("X-Sharer-User-Id") long userId,
                         @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                         @Positive @RequestParam(defaultValue = "5") int size) {
        if (text.isBlank()) {
            return List.of();
        }
        return itemService.search(text, PageRequest.of(from / size, size));
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@PathVariable long itemId,
                                    @RequestBody @Validated(Create.class) CommentDto commentDto,
                                    @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.createComment(commentDto, itemId, userId);
    }
}