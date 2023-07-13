package com.yongkonhahn.homefood.service;

import com.yongkonhahn.homefood.dto.ReserveDTO;

import java.util.List;

public interface ReserveService {
    void saveReservation(ReserveDTO reserveDTO);

    List<ReserveDTO> findAllReserves();
}
