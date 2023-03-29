package ru.practicum.shareit.user.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class UserDtoTest {
    @Autowired
    private JacksonTester<UserDto> json;

    UserDto userDto;

    @BeforeEach
    void setup() {
        userDto = new UserDto(
                1,
                "test",
                "test@mail.ru"
        );
    }

    @Test
    void test() throws IOException {
        JsonContent<UserDto> jsonContent = json.write(userDto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathStringValue("$.name").isEqualTo("test");
        assertThat(jsonContent).extractingJsonPathStringValue("$.email").isEqualTo("test@mail.ru");
    }
}