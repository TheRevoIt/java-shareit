package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.BookingState;
import ru.practicum.shareit.util.BookingStatus;
import ru.practicum.shareit.util.exception.BookingException;
import ru.practicum.shareit.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public BookingDtoResponse create(BookingDto bookingDto, long userId) {
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow(() ->
                new NotFoundException(String.format("Предмет с id=%d не найден", bookingDto.getItemId())));
        User booker = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        if (!item.isAvailable()) {
            throw new BookingException("This item is not available for booking");
        }
        if (userId == item.getOwner().getId()) {
            throw new BookingException("Unable to book your own item");
        }
        Booking booking = BookingMapper.toBooking(bookingDto, booker, item);
        booking.setBookingStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);
        return BookingMapper.toBookingDtoResponse(booking);
    }

    @Override
    @Transactional
    public BookingDtoResponse update(long bookingId, long userId, boolean approved) {
        User booker = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException(String.format("Бронирование с id=%d не найдено", bookingId)));
        if (booking.getBookingStatus().equals(BookingStatus.REJECTED) || booking.getBookingStatus()
                .equals(BookingStatus.APPROVED)) {
            throw new BookingException("Booking can not be updated");
        }
        if (userId != booking.getItem().getOwner().getId()) {
            throw new NotFoundException("This user can not approve the booking");
        }
        if (approved) {
            booking.setBookingStatus(BookingStatus.APPROVED);
        } else {
            booking.setBookingStatus(BookingStatus.REJECTED);
        }
        bookingRepository.save(booking);
        return BookingMapper.toBookingDtoResponse(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDtoResponse get(long bookingId, long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException(String.format("Бронирование с id=%d не найдено", bookingId)));
        if (userId == booking.getBooker().getId() || userId == booking.getItem().getOwner().getId()) {
            return BookingMapper.toBookingDtoResponse(booking);
        }
        throw new NotFoundException("The user with id " + userId + " is unable to see the booking information");
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDtoResponse> getAll(long userId, String state) {
        User booker = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        List<Booking> bookings = List.of();
        switch (BookingState.valueOf(state)) {
            case ALL:
                bookings = bookingRepository.findBookingsByBookerIdOrderByStartDesc(userId);
                break;
            case FUTURE:
                bookings = bookingRepository.findBookingsByBookerIdAndStartAfter(userId, LocalDateTime.now(),
                        Sort.by(Sort.Direction.DESC, "start"));
                break;
            case WAITING:
                bookings = bookingRepository.findBookingByBookerIdAndBookingStatusOrderByStartDesc(userId,
                        BookingStatus.WAITING);
                break;
            case REJECTED:
                bookings = bookingRepository.findBookingByBookerIdAndBookingStatusOrderByStartDesc(userId,
                        BookingStatus.REJECTED);
                break;
            case CURRENT:
                bookings = bookingRepository.findBookingsByBookerIdCurrent(userId, LocalDateTime.now());
                break;
            case PAST:
                bookings = bookingRepository.findBookingsByBookerIdAndEndBefore(userId, LocalDateTime.now(),
                        Sort.by(Sort.Direction.DESC, "start"));
                break;
        }
        return bookings.stream().map(BookingMapper::toBookingDtoResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDtoResponse> getAllForOwner(long userId, String state) {
        User booker = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        List<Booking> bookings = List.of();
        switch (BookingState.valueOf(state)) {
            case ALL:
                bookings = bookingRepository.findBookingsByItemOwnerIdOrderByStartDesc(userId);
                break;
            case FUTURE:
                bookings = bookingRepository.findBookingsByItemOwnerIdAndStartAfter(userId, LocalDateTime.now(),
                        Sort.by(Sort.Direction.DESC, "start"));
                break;
            case WAITING:
                bookings = bookingRepository.findBookingByItemOwnerIdAndBookingStatusOrderByStartDesc(userId,
                        BookingStatus.WAITING);
                break;
            case REJECTED:
                bookings = bookingRepository.findBookingByItemOwnerIdAndBookingStatusOrderByStartDesc(userId,
                        BookingStatus.REJECTED);
                break;
            case CURRENT:
                bookings = bookingRepository.findBookingsByItemOwnerCurrent(userId, LocalDateTime.now());
                break;
            case PAST:
                bookings = bookingRepository.findBookingsByItemOwnerIdAndEndBefore(userId, LocalDateTime.now(),
                        Sort.by(Sort.Direction.DESC, "start"));
                break;
        }
        return bookings.stream().map(BookingMapper::toBookingDtoResponse).collect(Collectors.toList());
    }
}