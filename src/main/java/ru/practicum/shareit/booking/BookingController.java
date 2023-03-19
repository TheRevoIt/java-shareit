package ru.practicum.shareit.booking;

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
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.user.Create;
import ru.practicum.shareit.user.Update;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    BookingDtoResponse create(@RequestHeader("X-Sharer-User-Id") long userId,
                   @Validated({Create.class}) @RequestBody BookingDto bookingDto) {
        return bookingService.create(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    BookingDtoResponse update(@RequestHeader("X-Sharer-User-Id") long userId,
                              @PathVariable long bookingId,
                              @RequestParam boolean approved) {
        return bookingService.update(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    BookingDtoResponse get(@RequestHeader("X-Sharer-User-Id") long userId,
                              @PathVariable long bookingId) {
        return bookingService.get(bookingId, userId);
    }

    @GetMapping
    List<BookingDtoResponse> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                    @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAll(userId, state);
    }

    @GetMapping("/owner")
    List<BookingDtoResponse> getAllForOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                    @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAllForOwner(userId, state);
    }
}
