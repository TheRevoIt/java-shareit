package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@DataJpaTest
@RunWith(SpringRunner.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemRequestRepositoryTest {
    final TestEntityManager testEntityManager;
    final ItemRequestRepository itemRequestRepository;
    private ItemRequest itemRequest1;
    private ItemRequest itemRequest2;
    private User user;
    private User user2;
    private Item item;
    private Item item1;


    @BeforeEach
    void initialData() {
        testEntityManager.clear();
        user = new User("testName", "testEmail");
        user2 = new User("testName2", "testEmail2");
        item = new Item("name1", "description1", true);
        item1 = new Item("name2", "description2", true);
        itemRequest1 = new ItemRequest();
        itemRequest1.setDescription("test");
        itemRequest1.setRequester(user);
        itemRequest1.setCreated(LocalDateTime.now());
        itemRequest2 = new ItemRequest();
        itemRequest2.setDescription("test2");
        itemRequest2.setRequester(user2);
        itemRequest2.setCreated(LocalDateTime.now());
    }

    @Test
    void contextLoads() {
        assertNotNull(testEntityManager);
    }

    @Test
    void findByRequesterIdTest() {
        testEntityManager.persist(user);
        testEntityManager.persist(user2);
        testEntityManager.persist(itemRequest1);
        testEntityManager.persist(itemRequest2);
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterId(1);
        assertEquals(1,itemRequests.size());
        assertEquals("test",itemRequests.get(0).getDescription());
    }

    @Test
    void findByRequesterIdNotInTest() {
        testEntityManager.persist(user);
        testEntityManager.persist(user2);
        testEntityManager.persist(itemRequest1);
        testEntityManager.persist(itemRequest2);
        PageRequest pageRequest = PageRequest.of(0,10);
        List<ItemRequest> itemRequests = itemRequestRepository.findItemRequestsByRequesterIdNot(1, pageRequest);
        assertEquals(1,itemRequests.size());
        assertEquals("test2",itemRequests.get(0).getDescription());
    }
}