package org.kiev.cinema.service;

import org.kiev.cinema.entity.Seat;

import java.util.List;

public interface SeatService {
    void add(Seat seat);
    void addAll(List<Seat> seats);
    List<Seat> listByScreening(Long screeningId);
    Seat findById(Long seatId);
}
