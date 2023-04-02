package ru.practicum.shareitgateway.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
import ru.practicum.shareitgateway.item.dto.CommentDto;
import ru.practicum.shareitgateway.item.dto.ItemDto;
import ru.practicum.shareitgateway.util.Create;
import ru.practicum.shareitgateway.util.Update;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @Validated({Create.class}) @RequestBody ItemDto itemDto) {
        return itemClient.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @PathVariable long itemId,
                                  @Validated({Update.class}) @RequestBody ItemDto itemDto) {
        return itemClient.update(itemDto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    ResponseEntity<Object> getById(@RequestHeader("X-Sharer-User-Id") long userId,
                                   @PathVariable long itemId) {
        return itemClient.getById(userId, itemId);
    }

    @GetMapping
    ResponseEntity<Object> getAllItems(@RequestHeader("X-Sharer-User-Id") long userId,
                                       @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                       @RequestParam(defaultValue = "5") @Positive int size) {
        return itemClient.getAll(userId, from, size);
    }

    @GetMapping("/search")
    ResponseEntity<Object> search(@RequestParam(name = "text") String text,
                                  @RequestHeader("X-Sharer-User-Id") long userId,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam(defaultValue = "5") @Positive int size) {
        return itemClient.search(text, userId, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@PathVariable long itemId,
                                                @RequestBody @Validated(Create.class) CommentDto commentDto,
                                                @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemClient.createComment(commentDto, itemId, userId);
    }
}