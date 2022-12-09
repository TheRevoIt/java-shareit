package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final HashMap<Long, Item> items = new HashMap<>();
    private long id = 1;

    @Override
    public Item add(Item item) {
        item.setId(id);
        items.put(id++, item);
        return item;
    }

    @Override
    public void update(Item item) {
        items.put(item.getId(), item);
    }

    @Override
    public Optional<Item> getById(long itemId) {
        return Optional.ofNullable(items.get(itemId));
    }

    @Override
    public List<Item> getAll() {
        return new ArrayList<>(items.values());
    }
}
