package org.kiev.cinema.service;

import org.kiev.cinema.entity.Ticket;
import org.kiev.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void add(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public void addAll(List<Ticket> tickets) {
        ticketRepository.save(tickets);
    }

    @Override
    public void update(Ticket ticket) {
        if(ticket.getId() != null && ticket.getId() > 0) {
            ticketRepository.save(ticket);
        } else {
            throw new RuntimeException("ticket can not be updated because id is not set");
        }
    }

    @Override
    public List<Ticket> listByMovie(Long movieId) {
        return ticketRepository.findAllByMovie(movieId);
    }

    @Override
    public List<Ticket> listByScreening(Long screeningId) {
        return ticketRepository.findAllByScreening(screeningId);
    }

    @Override
    public Ticket findById(Long ticketId) {
        return ticketRepository.findOne(ticketId);
    }
}
