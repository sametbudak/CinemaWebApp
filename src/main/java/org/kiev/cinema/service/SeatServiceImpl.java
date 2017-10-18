package org.kiev.cinema.service;

import org.kiev.cinema.entity.Seat;
import org.kiev.cinema.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public void add(Seat seat) {
        seatRepository.save(seat);
    }

    @Override
    public void addAll(List<Seat> seats) {
        seatRepository.save(seats);
    }

    @Override
    public List<Seat> listByScreening(Long screeningId) {
        return seatRepository.findAllByScreening(screeningId);
    }

    @Override
    public Seat findById(Long seatId) {
        return seatRepository.findOne(seatId);
    }
}
