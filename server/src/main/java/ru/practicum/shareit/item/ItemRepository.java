package ru.practicum.shareit.item;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOwnerIdOrderById(long ownerId, PageRequest pageRequest);

    List<Item> findByNameContainingIgnoreCaseAndIsAvailableIsTrueOrDescriptionContainingIgnoreCaseAndIsAvailableIsTrue(String name, String description, PageRequest pageRequest);

    List<Item> findItemsByItemRequestId(long requestId);

    List<Item> findItemsByItemRequestIdIn(List<Long> itemRequestIds);
}
