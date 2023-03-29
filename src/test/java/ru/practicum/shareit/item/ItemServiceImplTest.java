package ru.practicum.shareit.item;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.exception.CommentException;
import ru.practicum.shareit.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    @Mock
    BookingRepository bookingRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    ItemRequestRepository itemRequestRepository;
    @InjectMocks
    ItemServiceImpl itemService;

    private User user;
    private User user1;
    private Item item;
    private Item item1;
    private ItemDto itemDto;

    @BeforeEach
    void initialData() {
        user = new User("testName", "testEmail@mail.ru");
        user1 = new User("testName1", "testEmail1@mail.ru");
        item = new Item("name1", "description1", true);
        item1 = new Item("name2", "description2", true);
        item.setOwner(user);
        itemDto = new ItemDto(1, "name1", "description1", true);
    }

    @Test
    void createUserNotFoundTest() {
       when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

       assertThrows(NotFoundException.class, () -> itemService.create(itemDto, 1));
    }

    @Test
    void updateItemNotFoundTest() {
        assertThrows(NotFoundException.class, () -> itemService.update(itemDto, 1, 1));
    }

    @Test
    void searchTest() {
        when(itemRepository.findByNameContainingIgnoreCaseAndIsAvailableIsTrueOrDescriptionContainingIgnoreCaseAndIsAvailableIsTrue(any(), any(), any()))
                .thenReturn(Collections.singletonList(item));
        List<ItemDto> items = itemService.search("name1", PageRequest.of(1, 1));
        assertEquals(1, items.size());
    }

    @Test
    void searchTestBlank() {
        when(itemRepository.findByNameContainingIgnoreCaseAndIsAvailableIsTrueOrDescriptionContainingIgnoreCaseAndIsAvailableIsTrue(any(), any(), any()))
                .thenReturn(Collections.emptyList());
        List<ItemDto> items = itemService.search("name1", PageRequest.of(1, 1));
        assertEquals(0, items.size());
    }

    @Test
    void commentUserWithoutBookingTest() {
        CommentDto commentDto = new CommentDto(1, "text", LocalDateTime.now(), user.getName());

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(itemRepository.findById(1L)).thenReturn(Optional.ofNullable(item));

        when(bookingRepository.existsByBookerIdAndItemIdAndEndBefore(anyLong(), anyLong(), any()))
                .thenThrow(new CommentException("The user can not leave a comment to this item"));

        assertThrows(CommentException.class , () -> itemService.createComment(commentDto, 1, 1));
    }
}