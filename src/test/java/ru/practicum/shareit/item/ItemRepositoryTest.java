package ru.practicum.shareit.item;

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
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureTestDatabase
@DataJpaTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRepositoryTest {
    final TestEntityManager testEntityManagerItems;
    final ItemRepository itemRepository;
    final ItemRequestRepository itemRequestRepository;
    private User user;
    private Item item;
    private Item item1;

    @BeforeEach
    void initialData() {
        testEntityManagerItems.clear();
        user = new User("testName", "testEmail");
        item = new Item("name1", "description1", true);
        item1 = new Item("name2", "description2", true);
    }

    @Test
    void contextLoads() {
        assertNotNull(testEntityManagerItems);
    }

    @Test
    void searchTextTest() {
        testEntityManagerItems.persist(user);
        testEntityManagerItems.persist(item);
        testEntityManagerItems.persist(item1);
        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Item> itemList = itemRepository.findByNameContainingIgnoreCaseAndIsAvailableIsTrueOrDescriptionContainingIgnoreCaseAndIsAvailableIsTrue("description2",
                "description2", pageRequest);
        assertEquals(1, itemList.size());
        assertEquals("name2", itemList.get(0).getName());
    }

    @Test
    void findByOwnerIdTest() {
        item1.setOwner(user);

        testEntityManagerItems.persist(user);
        testEntityManagerItems.persist(item);
        testEntityManagerItems.persist(item1);
        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Item> itemList = itemRepository.findAllByOwnerIdOrderById(1, pageRequest);
        assertEquals(1, itemList.size());
        assertEquals("name2", itemList.get(0).getName());
    }

    @Test
    void findItemsByItemRequestIdIn() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setRequester(user);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setDescription("test");
        testEntityManagerItems.persist(itemRequest);
        item1.setItemRequest(itemRequest);

        testEntityManagerItems.persist(user);
        testEntityManagerItems.persist(item);
        testEntityManagerItems.persist(item1);

        List<Item> itemList = itemRepository.findItemsByItemRequestIdIn(List.of(1L));
        assertEquals(1, itemList.size());
        assertEquals("name2", itemList.get(0).getName());
        testEntityManagerItems.remove(itemRequest);
    }

    @Test
    void findItemsByItemRequestIdTest() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription("test");
        itemRequest.setRequester(user);
        itemRequest.setCreated(LocalDateTime.now());
        testEntityManagerItems.persist(itemRequest);
        item1.setItemRequest(itemRequest);

        testEntityManagerItems.persist(user);
        testEntityManagerItems.persist(item);
        testEntityManagerItems.persist(item1);

        List<Item> itemList = itemRepository.findItemsByItemRequestId(1);
        assertEquals(1, itemList.size());
        assertEquals("name2", itemList.get(0).getName());

        testEntityManagerItems.remove(itemRequest);
    }
}