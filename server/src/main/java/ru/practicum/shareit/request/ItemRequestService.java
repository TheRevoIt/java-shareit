package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;

import java.util.List;

interface ItemRequestService {
    ItemRequestDtoResponse create(ItemRequestDto itemRequestDto, long userId);

    ItemRequestDtoResponse getRequestDataById(long requestId, long userId);

    List<ItemRequestDtoResponse> getRequestsData(long userId);

    List<ItemRequestDtoResponse> getAllRequests(long userId, int from, int size);
}
