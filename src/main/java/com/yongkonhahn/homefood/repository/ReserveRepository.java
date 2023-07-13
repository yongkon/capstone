package com.yongkonhahn.homefood.repository;

import com.yongkonhahn.homefood.dto.ReserveDTO;
import com.yongkonhahn.homefood.model.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
    List<Reserve> findAll();
}
