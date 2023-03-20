package ru.practicum.shareit.booking.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.BookingDtoShortResponse;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingMapper {
    public static Booking toBooking(BookingDto bookingDto, User booker, Item item) {
        Booking resultBooking = new Booking(
                bookingDto.getStart(), bookingDto.getEnd(),
                item, booker);
        return resultBooking;
    }

    public static BookingDtoShortResponse toBookingDtoShortResponse(Booking booking) {
        return new BookingDtoShortResponse(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getBooker().getId(),
                booking.getBookingStatus());
    }

    public static BookingDtoResponse toBookingDtoResponse(Booking booking) {
        return new BookingDtoResponse(booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                toBookerDto(booking.getBooker()),
                toItemDto(booking.getItem()),
                booking.getBookingStatus());
    }

    private static BookingDtoResponse.Item toItemDto(Item item) {
        return new BookingDtoResponse.Item(item.getId(), item.getName());
    }

    private static BookingDtoResponse.Booker toBookerDto(User user) {
        return new BookingDtoResponse.Booker(user.getId(), user.getName());
    }
}
