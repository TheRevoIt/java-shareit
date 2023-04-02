package ru.practicum.shareit.item.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.util.Create;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class ItemDto {
    private final long id;
    private final String name;
    private final String description;
    private final Boolean available;
    private Long requestId;
}
