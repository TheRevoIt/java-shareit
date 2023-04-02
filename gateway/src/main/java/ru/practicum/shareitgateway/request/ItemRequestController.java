package ru.practicum.shareitgateway.request;

        import lombok.AllArgsConstructor;
        import org.springframework.http.ResponseEntity;
        import org.springframework.validation.annotation.Validated;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RequestHeader;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;
        import ru.practicum.shareitgateway.request.dto.ItemRequestDto;
        import ru.practicum.shareitgateway.util.Create;

        import javax.validation.constraints.Positive;
        import javax.validation.constraints.PositiveOrZero;

@RestController
@AllArgsConstructor
@RequestMapping("/requests")
@Validated
public class ItemRequestController {
    private ItemRequestClient itemRequestClient;
    @PostMapping
    ResponseEntity<Object> create(@Validated(Create.class) @RequestBody ItemRequestDto itemRequestDto,
                                  @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestClient.create(itemRequestDto, userId);
    }

    @GetMapping("/{requestId}")
    ResponseEntity<Object> getRequestDataById(@RequestHeader("X-Sharer-User-Id") long userId,
                                              @PathVariable long requestId) {
        return itemRequestClient.getRequestDataById(requestId, userId);
    }

    @GetMapping
    ResponseEntity<Object> getItemRequestsData(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestClient.getRequestsData(userId);
    }

    @GetMapping("/all")
    ResponseEntity<Object> getAllRequests(@RequestHeader("X-Sharer-User-Id") long userId,
                                          @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                          @Positive @RequestParam(name = "size", defaultValue = "5") int size) {
        return itemRequestClient.getAllRequests(userId, from, size);
    }
}
