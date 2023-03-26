package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestsMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.exception.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private ItemRepository itemRepository;
    private UserRepository userRepository;
    private ItemRequestRepository itemRequestRepository;

    @Override
    @Transactional
    public ItemRequestDtoResponse create(ItemRequestDto itemRequestDto, long userId) {
        User requester = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        ItemRequest itemRequest = ItemRequestsMapper.toItemRequest(itemRequestDto, requester);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequestRepository.save(itemRequest);
        return ItemRequestsMapper.toItemRequestDtoResponse(itemRequest);
    }

    @Override
    public ItemRequestDtoResponse getRequestDataById(long requestId, long userId) {
        User requester = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException(String.format("Запрос с id=%d не найден", requestId)));
        List<ItemDto> items = itemRepository.findItemsByItemRequestId(requestId).stream()
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
        ItemRequestDtoResponse itemRequestDtoResponse = ItemRequestsMapper.toItemRequestDtoResponse(itemRequest);
        itemRequestDtoResponse.setItems(items);
        return itemRequestDtoResponse;
    }

    @Override
    public List<ItemRequestDtoResponse> getRequestsData(long userId) {
        User requester = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        List<ItemRequestDtoResponse> itemRequestDtoResponses = itemRequestRepository.findAllByRequesterId(userId)
                .stream().map(ItemRequestsMapper::toItemRequestDtoResponse).collect(Collectors.toList());
        addItemsToRequests(itemRequestDtoResponses);
        return itemRequestDtoResponses;
    }

    @Override
    public List<ItemRequestDtoResponse> getAllRequests(long userId, int from, int size) {
        User requester = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
        List<ItemRequestDtoResponse> itemRequestResponses = itemRequestRepository.findItemRequestsByRequesterIdNot(userId,
                        PageRequest.of(from / size, size)).stream()
                .map(ItemRequestsMapper::toItemRequestDtoResponse)
                .collect(Collectors.toList());
        addItemsToRequests(itemRequestResponses);
        return itemRequestResponses;
    }

    private void addItemsToRequests(List<ItemRequestDtoResponse> itemRequestDtoResponses) {
        Map<Long, ItemRequestDtoResponse> requests = itemRequestDtoResponses.stream()
                .collect(Collectors.toMap(ItemRequestDtoResponse::getId, x -> x, (one, two) -> one));
        List<Long> itemRequestsIds = requests.values().stream()
                .map(ItemRequestDtoResponse::getId).collect(Collectors.toList());
        List<ItemDto> itemDtos = itemRepository.findItemsByItemRequestIdIn(itemRequestsIds)
                .stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
        for (ItemDto dto : itemDtos) {
            requests.get(dto.getRequestId()).getItems().add(dto);
        }
    }
}
