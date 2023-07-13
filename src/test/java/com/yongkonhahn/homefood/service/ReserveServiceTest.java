package com.yongkonhahn.homefood.service;

import com.yongkonhahn.homefood.dto.ReserveDTO;
import com.yongkonhahn.homefood.impl.ReserveServiceImpl;
import com.yongkonhahn.homefood.model.Reserve;
import com.yongkonhahn.homefood.repository.ReserveRepository;
import com.yongkonhahn.homefood.service.ReserveService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReserveServiceTest {

    @Mock
    private ReserveRepository reserveRepository;

    @InjectMocks
    private ReserveService reserveService = new ReserveServiceImpl();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveReservation() {
        // Create test data
        ReserveDTO reserveDTO = new ReserveDTO();
        reserveDTO.setName("John");
        reserveDTO.setEmail("john@example.com");
        reserveDTO.setDate(new Date());
        reserveDTO.setPeople(4);
        reserveDTO.setRequest("Test reservation");

        // Test the saveReservation method
        reserveService.saveReservation(reserveDTO);

        // Verify that the reserveRepository's save method is called once
        verify(reserveRepository, times(1)).save(any(Reserve.class));
    }
}