package ru.practicum.shareitgateway.booking;

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
import ru.practicum.shareitgateway.booking.dto.BookingDto;
import ru.practicum.shareitgateway.util.Create;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/bookings")
@Validated
@RequiredArgsConstructor
public class BookingController {
    final BookingClient bookingClient;

    @PostMapping
    ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @Validated(Create.class) @RequestBody BookingDto bookingDto) {
        return bookingClient.create(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @PathVariable long bookingId,
                                  @RequestParam Boolean approved) {
        return bookingClient.update(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    ResponseEntity<Object> get(@RequestHeader("X-Sharer-User-Id") long userId,
                               @PathVariable long bookingId) {
        return bookingClient.get(userId, bookingId);
    }

    @GetMapping
    ResponseEntity<Object> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @RequestParam(defaultValue = "ALL") String state,
                                  @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                  @Positive @RequestParam(defaultValue = "5") int size) {
        return bookingClient.getAll(userId, state, from, size);
    }

    @GetMapping("/owner")
    ResponseEntity<Object> getAllForOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                          @RequestParam(defaultValue = "ALL") String state,
                                          @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                          @Positive @RequestParam(defaultValue = "5") int size) {
        return bookingClient.getAllForOwner(userId, state, from, size);
    }
}
