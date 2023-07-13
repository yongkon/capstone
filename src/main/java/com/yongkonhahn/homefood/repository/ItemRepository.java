package com.yongkonhahn.homefood.repository;

import com.yongkonhahn.homefood.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByIdIn(List<Integer> ids);
}
