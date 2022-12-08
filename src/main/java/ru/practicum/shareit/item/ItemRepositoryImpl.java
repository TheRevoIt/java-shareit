package ru.practicum.shareit.item;

import com.sun.source.tree.OpensTree;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    HashMap<Long, Item> items = new HashMap<>();
    private long id = 1;

    @Override
    public Item addItem(Item item) {
        item.setId(id);
        items.put(id++, item);
        return item;
    }

    @Override
    public Item updateItem(long itemId, Item item) {
        items.put(id, item);
        return item;
    }

    @Override
    public Optional<Item> getItemById(long itemId) {
       return Optional.ofNullable(items.get(itemId));
    }

    @Override
    public List<Item> getItems() {
        return null;
    }
}
