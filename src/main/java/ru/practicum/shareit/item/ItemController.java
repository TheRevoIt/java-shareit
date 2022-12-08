package ru.practicum.shareit.item;

import org.springframework.validation.annotation.Validated;
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

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {
    ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    ItemDto createItem(@RequestHeader("X-Sharer-User-Id") long userId,
                       @Validated({Create.class}) @RequestBody ItemDto itemDto) {
        return itemService.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    ItemDto patchItem(@PathVariable long itemId,
                      @Validated({Update.class}) @RequestBody ItemDto itemDto) {
        return itemService.updateItem(itemId, itemDto);
    }
}