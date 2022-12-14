package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.user.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemDto {
    private final long id;
    @NotBlank(groups = {Create.class})
    private final String name;
    @NotBlank(groups = {Create.class})
    private final String description;
    @NotNull(groups = {Create.class})
    private final Boolean available;
}
