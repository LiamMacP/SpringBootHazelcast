package com.liammacpherson.api.service;

import com.liammacpherson.api.model.Item;
import com.liammacpherson.api.repository.ItemRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService extends CachingService<Item> {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Cacheable(value="items-cache", key="#id", unless = "#result==null")
    public Item getItemById(long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            return null;
        }
        return optionalItem.get();
    }

    @Cacheable(value="items-cache", key="#root.target.getAllCacheName()")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Caching(evict = {
            @CacheEvict(value = "items-cache", key="#root.target.getAllCacheName()")
    }, put = {
            @CachePut(value = "items-cache", key = "#item.id")
    })
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    @Caching(evict = {
            @CacheEvict(value = "items-cache", key="#root.target.getAllCacheName()"),
            @CacheEvict(value = "items-cache", key="#id")
    })
    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "items-cache", key="#root.target.getAllCacheName()")
    }, put = {
            @CachePut(value = "items-cache", key = "#id")
    })
    public void updateItem(long id, Item item) {
        item.setId(id);
        itemRepository.save(item);
    }

}
