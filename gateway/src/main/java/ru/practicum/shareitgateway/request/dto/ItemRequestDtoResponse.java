package ru.practicum.shareitgateway.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareitgateway.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ItemRequestDtoResponse {
    private long id;
    private String description;
    private LocalDateTime created;
    private long requesterId;
    private List<ItemDto> items;
}
