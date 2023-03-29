package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.request.model.ItemRequestsMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;
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
class ItemRequestServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    ItemRequestRepository itemRequestRepository;
    @InjectMocks
    ItemRequestServiceImpl itemRequestService;

    private User user;
    private User user1;
    private Item item;
    private Item item1;
    private ItemRequestDto itemRequestDto;

    @BeforeEach
    void initialData() {
        user = new User("testName", "testEmail@mail.ru");
        user1 = new User("testName1", "testEmail1@mail.ru");
        item = new Item("name1", "description1", true);
        item1 = new Item("name2", "description2", true);
        item.setOwner(user);
        itemRequestDto = new ItemRequestDto(1, "description", 1, LocalDateTime.now());
    }

    @Test
    void createWhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemRequestService.create(itemRequestDto, 1));
    }

    @Test
    void getRequestsTestUserFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        List<ItemRequestDtoResponse> requests = itemRequestService.getRequestsData(user.getId());
        assertEquals(0, requests.size());
    }

    @Test
    void getRequestNotFoundTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(itemRequestRepository.findById(anyLong())).thenThrow(new NotFoundException("Request with this id is not found"));

        assertThrows(NotFoundException.class, () -> itemRequestService.getRequestDataById(1, 1));
    }

    @Test
    void getItemRequestTest() {
        ItemRequestDto itemRequestDto = new ItemRequestDto(1, "text", 1, LocalDateTime.now());
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(itemRequestRepository.findItemRequestsByRequesterIdNot(anyLong(), any()))
                .thenReturn(Collections.singletonList(ItemRequestsMapper.toItemRequest(itemRequestDto, user)));
        List<ItemRequestDtoResponse> requestDtoResponses = itemRequestService.getAllRequests(1, 1, 1);
        assertEquals(1, requestDtoResponses.size());
    }
}