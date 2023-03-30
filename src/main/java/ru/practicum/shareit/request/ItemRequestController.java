package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.util.Create;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/requests")
@Validated
public class ItemRequestController {
    private ItemRequestService itemRequestService;

    @PostMapping
    ItemRequestDtoResponse create(@Validated(Create.class) @RequestBody ItemRequestDto itemRequestDto,
                                  @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestService.create(itemRequestDto, userId);
    }

    @GetMapping("/{requestId}")
    ItemRequestDtoResponse getRequestDataById(@RequestHeader("X-Sharer-User-Id") long userId,
                                              @PathVariable long requestId) {
        return itemRequestService.getRequestDataById(requestId, userId);
    }

    @GetMapping
    List<ItemRequestDtoResponse> getItemRequestsData(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestService.getRequestsData(userId);
    }

    @GetMapping("/all")
    List<ItemRequestDtoResponse> getAllRequests(@RequestHeader("X-Sharer-User-Id") long userId,
                                                @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                                @Positive @RequestParam(name = "size", defaultValue = "5") int size) {
        return itemRequestService.getAllRequests(userId, from, size);
    }
}
