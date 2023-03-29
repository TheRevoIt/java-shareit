package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.BookingStatus;
import ru.practicum.shareit.util.exception.BookingException;
import ru.practicum.shareit.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    @InjectMocks
    BookingServiceImpl bookingService;
    @Mock
    BookingRepository bookingRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ItemRepository itemRepository;

    private User user;
    private User user1;
    private Item item;
    private Item item1;
    private BookingDto bookingDto;

    @BeforeEach
    void initialData() {
        user = new User("testName", "testEmail@mail.ru");
        user1 = new User("testName1", "testEmail1@mail.ru");
        item = new Item("name1", "description1", true);
        item1 = new Item("name2", "description2", true);
        item.setOwner(user);
        bookingDto = new BookingDto(LocalDateTime.now().plusMinutes(10),
                LocalDateTime.now().plusMinutes(20), 1);
    }

    @Test
    void createItemNotFound() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> bookingService.create(bookingDto, 1));
    }

    @Test
    void createUserNotFound() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> bookingService.create(bookingDto, 1));
    }

    @Test
    void createItemNotAvailableTest() {
        item.setId(1);
        item.setAvailable(false);
        bookingDto.setItemId(item.getId());

        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        assertThrows(BookingException.class, () -> bookingService.create(bookingDto, 5));
    }

    @Test
    void updateItemWhenIncorrectStatus() {
        Booking booking = BookingMapper.toBooking(bookingDto, user, item);
        booking.setBookingStatus(BookingStatus.REJECTED);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        assertThrows(BookingException.class, () -> bookingService.update(1, 1, true));
    }

    @Test
    void getBookingInfoWhenNotOwner() {
        Booking booking = BookingMapper.toBooking(bookingDto, user, item);

        booking.setBookingStatus(BookingStatus.REJECTED);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        assertThrows(NotFoundException.class, () -> bookingService.get(1, 49));

    }

    @Test
    void getBookingInfoWhenNotFound() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.get(1, 1));

    }

    @Test
    void getBookingsByBookerIdWithAllState() {
        Booking booking = BookingMapper.toBooking(bookingDto, user, item);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(bookingRepository.findBookingsByBookerIdOrderByStartDesc(anyLong(), any()))
                .thenReturn(Collections.singletonList(booking));
        List<BookingDtoResponse> bookings = bookingService.getAll(user.getId(), "ALL", PageRequest.of(1, 1));
        assertEquals(1, bookings.size());
    }

    @Test
    void getBookingsByOwnerIdWithAllState() {
        Booking booking = BookingMapper.toBooking(bookingDto, user, item);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(bookingRepository.findBookingsByBookerIdOrderByStartDesc(anyLong(), any()))
                .thenReturn(Collections.singletonList(booking));
        List<BookingDtoResponse> bookings = bookingService.getAll(user.getId(), "ALL", PageRequest.of(1, 1));
        assertEquals(1, bookings.size());
    }

   @Test
    void getBookingsByBookerIdWithFutureState() {
       Booking booking = BookingMapper.toBooking(bookingDto, user, item);
       when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
       when(bookingRepository.findBookingsByBookerIdAndStartAfter(anyLong(), any(), any(), any()))
               .thenReturn(Collections.singletonList(booking));
       List<BookingDtoResponse> bookings = bookingService.getAll(user.getId(), "FUTURE", PageRequest.of(1, 1));
       assertEquals(1, bookings.size());
   }

    @Test
    void getBookingsByOwnerIdWithFutureState() {
        Booking booking = BookingMapper.toBooking(bookingDto, user, item);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(bookingRepository.findBookingsByItemOwnerIdAndStartAfter(anyLong(), any(), any(), any()))
                .thenReturn(Collections.singletonList(booking));
        List<BookingDtoResponse> bookings = bookingService.getAllForOwner(user.getId(), "FUTURE", PageRequest.of(1, 1));
        assertEquals(1, bookings.size());
    }

    @Test
    void getBookingsByUnsupportedState() {
        assertThrows(NotFoundException.class, () -> bookingService.getAllForOwner(user.getId(), "UNKNOWN",
                PageRequest.of(1, 1)));
    }
}