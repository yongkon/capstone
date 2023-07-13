package com.yongkonhahn.homefood.repository;
import com.yongkonhahn.homefood.model.Item;
import com.yongkonhahn.homefood.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

@DataJpaTest // Configures the test for Data JPA
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testFindByIdIn() {
        // Create test item data
        Item item1 = new Item();
        item1.setId(1);
        item1.setName("Item 1");
        item1.setPrice(10.0);

        Item item2 = new Item();
        item2.setId(2);
        item2.setName("Item 2");
        item2.setPrice(20.0);

        Item item3 = new Item();
        item3.setId(3);
        item3.setName("Item 3");
        item3.setPrice(30.0);

        // Save items
        itemRepository.saveAll(Arrays.asList(item1, item2, item3));

        // List of item IDs to search
        List<Integer> itemIds = Arrays.asList(1, 3);

        // Test the findByIdIn method
        List<Item> foundItems = itemRepository.findByIdIn(itemIds);

        // Verify the results
        Assertions.assertEquals(2, foundItems.size());
        Assertions.assertEquals(item1, foundItems.get(0));
        Assertions.assertEquals(item3, foundItems.get(1));
    }
}