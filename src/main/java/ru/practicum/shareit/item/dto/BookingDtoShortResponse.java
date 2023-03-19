package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.util.BookingStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BookingDtoShortResponse {
   private long id;
   LocalDateTime start;
   LocalDateTime end;
   private long itemId;
   private long bookerId;
   private BookingStatus status;
}
