package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.util.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
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
    private Long requestId;
}
