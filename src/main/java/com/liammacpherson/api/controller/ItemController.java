package com.liammacpherson.api.controller;

import com.liammacpherson.api.model.Item;
import com.liammacpherson.api.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(
            value = "/{id}" ,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Item> findById(@PathVariable long id) {
        Item item = itemService.getItemById(id);

        if (item == null) {
            throw new EntityNotFoundException("Item not found { id " + id + " }");
        }

        return ResponseEntity.ok(item);
    }

    @GetMapping(
            value ={ "/", "" },
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Item>> findAll() {
        List<Item> items = itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping(
            value = { "/", "" },
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> add(@RequestBody Item item) {
        Item createdItem = itemService.addItem(item);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdItem.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(
            value = { "/{id}" }
    )
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        itemService.deleteItem(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(
            value = {"/{id}"}
    )
    public ResponseEntity<?> updateById(@PathVariable long id, @RequestBody Item item) {
        Item retrievedItem = itemService.getItemById(id);
        if (retrievedItem == null) {
            return add(item);
        }

        itemService.updateItem(id, item);
        return ResponseEntity.noContent().build();
    }

}
