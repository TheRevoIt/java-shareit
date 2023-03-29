package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemAndBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class BookingDtoTest {
    private UserDto userDto;
    private ItemDto itemDto;

    @Autowired
    private JacksonTester<ItemAndBookingDto> json;

    @BeforeEach
    void setup() {
        userDto = new UserDto(
                1,
                "test",
                "test@mail.ru"
        );
        itemDto = new ItemDto(
                1,
                "test",
                "test",
                true
        );
    }

    @Test
    void test() throws IOException {
        User user = UserMapper.toUser(userDto);
        Item item = ItemMapper.toItem(itemDto, user, null);
        item.setId(1);
        ItemAndBookingDto dto = ItemMapper.toItemAndBookingDto(item);

        JsonContent<ItemAndBookingDto> jsonContent = json.write(dto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathStringValue("$.name").isEqualTo("test");
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.available").isEqualTo(true);
        assertThat(jsonContent).extractingJsonPathStringValue("$.description").isEqualTo("test");
    }
}