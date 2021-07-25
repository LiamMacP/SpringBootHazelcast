package com.liammacpherson.api.service;

import com.liammacpherson.api.model.Item;
import com.liammacpherson.api.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item getItemById(long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            return null;
        }
        return optionalItem.get();
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }

    public void updateItem(long id, Item item) {
        item.setId(id);
        itemRepository.save(item);
    }

}
