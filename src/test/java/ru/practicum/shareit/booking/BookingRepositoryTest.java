package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureTestDatabase
@DataJpaTest
@RunWith(SpringRunner.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BookingRepositoryTest {
    final TestEntityManager testEntityManager;

    @Test
    void contextLoads() {
        assertNotNull(testEntityManager);
    }
}