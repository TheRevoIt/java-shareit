package ru.practicum.shareit.request.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class ItemRequestDtoTest {
    @Autowired
    private JacksonTester<ItemRequestDto> json;
    ItemRequestDto itemRequestDto;

    @BeforeEach
    void setup() {
        itemRequestDto = new ItemRequestDto(
                1,
                "description",
                1,
                LocalDateTime.now()
        );
    }

    @Test
    void test() throws IOException {
        JsonContent<ItemRequestDto> jsonContent = json.write(itemRequestDto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.requesterId").isEqualTo(1);
    }
}