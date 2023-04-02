package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class ShareItTests {

    @Test
    void contextLoads() {
    }

    @Test
    void main() {
        ShareItApp.main(new String[]{});
    }
}
