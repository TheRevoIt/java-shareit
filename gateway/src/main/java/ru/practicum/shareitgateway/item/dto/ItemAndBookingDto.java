package ru.practicum.shareitgateway.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ItemAndBookingDto {
    private final long id;
    private final String name;
    private final String description;
    private final Boolean available;
    private BookingDtoShortResponse lastBooking;
    private BookingDtoShortResponse nextBooking;
    private List<CommentDto> comments;
}
