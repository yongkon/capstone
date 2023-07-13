package com.yongkonhahn.homefood.impl;

import com.yongkonhahn.homefood.dto.ReserveDTO;
import com.yongkonhahn.homefood.model.Reserve;
import com.yongkonhahn.homefood.repository.ReserveRepository;
import com.yongkonhahn.homefood.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    ReserveRepository reserveRepository;

    @Override
    public void saveReservation(ReserveDTO reserveDTO) {
        Reserve reserve = new Reserve();

        reserve.setName(reserveDTO.getName());
        reserve.setEmail(reserveDTO.getEmail());
        reserve.setDate(reserveDTO.getDate());
        reserve.setPeople(reserveDTO.getPeople());
        reserve.setRequest(reserveDTO.getRequest());

        reserveRepository.save(reserve);
    }

    @Override
    public List<ReserveDTO> findAllReserves() {
        List<Reserve> reserves = reserveRepository.findAll();
        // Find All reservations
        return reserves.stream()
                .map(reserve -> {
                    ReserveDTO reserveDTO = new ReserveDTO();
                    reserveDTO.setName(reserve.getName());
                    reserveDTO.setEmail(reserve.getEmail());
                    reserveDTO.setDate(reserve.getDate());
                    reserveDTO.setRequest(reserve.getRequest());
                    reserveDTO.setPeople(reserve.getPeople());
                    return reserveDTO;
                })
                .collect(Collectors.toList());
    }
}
