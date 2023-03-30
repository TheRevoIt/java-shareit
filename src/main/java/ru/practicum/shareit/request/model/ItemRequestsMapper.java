package ru.practicum.shareit.request.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequestsMapper {
    public static ItemRequest toItemRequest(ItemRequestDto dto, User requester) {
        ItemRequest resultRequest = new ItemRequest(dto.getDescription(), dto.getCreated());
        resultRequest.setRequester(requester);
        return resultRequest;
    }

    public static ItemRequestDtoResponse toItemRequestDtoResponse(ItemRequest itemRequest) {
        return new ItemRequestDtoResponse(itemRequest.getId(),
                itemRequest.getDescription(), itemRequest.getCreated(),
                itemRequest.getRequester().getId(), new ArrayList<>());
    }
}