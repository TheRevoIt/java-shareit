package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.item.dto.BookingDtoShortResponse;
import ru.practicum.shareit.util.BookingStatus;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BookingControllerTest {
    final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    @MockBean
    BookingService bookingService;
    private BookingDtoResponse bookingDtoResponse;
    private BookingDtoShortResponse bookingDto;

    @BeforeEach
    void setup() {
        BookingDtoResponse.Item item = new BookingDtoResponse.Item(
                1,
                "test"
        );

        BookingDtoResponse.Booker booker = new BookingDtoResponse.Booker(
                1,
                "test"
        );

        bookingDto = new BookingDtoShortResponse(
                1,
                LocalDateTime.now().plusMinutes(10),
                LocalDateTime.now().plusMinutes(20),
                1,
                1,
                BookingStatus.WAITING
        );

        bookingDtoResponse = new BookingDtoResponse(
                1,
                LocalDateTime.now().plusMinutes(10),
                LocalDateTime.now().plusMinutes(20),
                booker,
                item,
                BookingStatus.WAITING
        );
    }

    @Test
    void createTest() throws Exception {
        when(bookingService.create(any(), anyLong()))
                .thenReturn(bookingDtoResponse);

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.id").value(1L))
                .andExpect(jsonPath("$.item.name").value("test"))
                .andExpect(jsonPath("$.booker.name").value("test"));
    }

    @Test
    void createError() throws Exception {
        when(bookingService.create(any(), anyLong())).thenReturn(bookingDtoResponse);

        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now());

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTest() throws Exception {
        when(bookingService.update(anyLong(), anyLong(), anyBoolean())).thenReturn(bookingDtoResponse);
        bookingDtoResponse.setStatus(BookingStatus.APPROVED);

        mockMvc.perform(patch("/bookings/1?approved=true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    void getTest() throws Exception {
        when(bookingService.get(anyLong(), anyLong())).thenReturn(bookingDtoResponse);

        mockMvc.perform(get("/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.booker.name").value("test"))
                .andExpect(jsonPath("$.item.name").value("test"))
                .andExpect(jsonPath("$.item.id").value(1L));

    }

    @Test
    void getAllTest() throws Exception {
        when(bookingService.getAll(anyLong(), any(), any())).thenReturn(Collections.singletonList(bookingDtoResponse));

        mockMvc.perform(get("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].booker.name").value("test"))
                .andExpect(jsonPath("$[0].item.name").value("test"))
                .andExpect(jsonPath("$[0].item.id").value(1L));

    }

    @Test
    void getAllForOwnerTest() throws Exception {
        when(bookingService.getAllForOwner(anyLong(), any(), any())).thenReturn(Collections.singletonList(bookingDtoResponse));

        mockMvc.perform(get("/bookings/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].booker.name").value("test"))
                .andExpect(jsonPath("$[0].item.name").value("test"))
                .andExpect(jsonPath("$[0].item.id").value(1L));

    }
}