package ru.practicum.shareit.booking;

import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import java.util.List;

interface BookingService {
    BookingDtoResponse create(BookingDto bookingDto, long userId);

    BookingDtoResponse update(long bookingId, long userId, boolean approved);

    BookingDtoResponse get(long bookingId, long userId);

    List<BookingDtoResponse> getAll(long userId, String state, PageRequest pageRequest);

    List<BookingDtoResponse> getAllForOwner(long userId, String state, PageRequest pageRequest);
}
