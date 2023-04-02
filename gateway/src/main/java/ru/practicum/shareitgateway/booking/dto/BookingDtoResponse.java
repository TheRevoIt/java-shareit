package ru.practicum.shareitgateway.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareitgateway.util.BookingStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BookingDtoResponse {
    private long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Booker booker;
    private Item item;
    private BookingStatus status;

    public long getItemId() {
        return item.getId();
    }

    @Data
    public static class Item {
        private final long id;
        private final String name;
    }

    @Data
    public static class Booker {
        private final long id;
        private final String name;
    }
}
