package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.util.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ItemDto {
    private final long id;
    @NotBlank(groups = {Create.class})
    @Size(max = 255)
    private final String name;
    @NotBlank(groups = {Create.class})
    @Size(max = 512)
    private final String description;
    @NotNull(groups = {Create.class})
    private final Boolean available;
}
