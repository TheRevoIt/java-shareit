package ru.practicum.shareitgateway.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareitgateway.booking.validation.EndDateAfterStartDate;
import ru.practicum.shareitgateway.util.Create;
import ru.practicum.shareitgateway.util.Update;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@EndDateAfterStartDate(groups = {Create.class, Update.class})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    @FutureOrPresent(groups = Create.class)
    private LocalDateTime start;
    @Future(groups = Create.class)
    private LocalDateTime end;
    @NotNull(groups = Create.class)
    private long itemId;
}