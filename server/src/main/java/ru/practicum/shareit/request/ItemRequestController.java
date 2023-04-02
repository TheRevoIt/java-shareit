package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
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

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/requests")
public class ItemRequestController {
    private ItemRequestService itemRequestService;

    @PostMapping
    ItemRequestDtoResponse create(@RequestBody ItemRequestDto itemRequestDto,
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
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "5") int size) {
        return itemRequestService.getAllRequests(userId, from, size);
    }
}
