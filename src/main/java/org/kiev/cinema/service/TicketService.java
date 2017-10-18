package org.kiev.cinema.service;

import org.kiev.cinema.entity.Ticket;

import java.util.List;

public interface TicketService {
    void add(Ticket ticket);
    void addAll(List<Ticket> tickets);
    void update(Ticket ticket);
    List<Ticket> listByMovie(Long movieId);
    List<Ticket> listByScreening(Long screeningId);
    Ticket findById(Long ticketId);
}
