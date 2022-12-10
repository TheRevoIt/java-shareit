package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
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
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.Create;
import ru.practicum.shareit.user.Update;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                       @Validated({Create.class}) @RequestBody ItemDto itemDto) {
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    ItemDto patch(@RequestHeader("X-Sharer-User-Id") long userId,
                      @PathVariable long itemId,
                      @Validated({Update.class}) @RequestBody ItemDto itemDto) {
        return itemService.update(itemDto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    ItemDto getById(@RequestHeader("X-Sharer-User-Id") long userId,
                        @PathVariable long itemId) {
        return itemService.getById(itemId);
    }

    @GetMapping
    List<ItemDto> getAllItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getAll(userId);
    }

    @GetMapping("/search")
    List<ItemDto> search(@RequestParam(name = "text") @NotBlank String text,
                              @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.search(text);
    }
}