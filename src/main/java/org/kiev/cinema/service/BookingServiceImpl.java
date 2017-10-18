package org.kiev.cinema.service;

import org.kiev.cinema.entity.Booking;
import org.kiev.cinema.enums.BookingStatus;
import org.kiev.cinema.enums.TicketStatus;
import org.kiev.cinema.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Long add(Booking booking) {
        bookingRepository.save(booking);
        return booking.getId();
    }

    @Override
    public void addAll(List<Booking> bookings) {
        bookingRepository.save(bookings);
    }

    @Override
    public void updateStatus(Booking booking) {
        bookingRepository.updateStatus(booking.getBookingStatus(), booking.getId());
    }

    @Transactional
    @Override
    public List<Booking> cancelBookings(List<Long> bookingIdList) {
        List<Booking> bookingList = bookingRepository.findAllBookingsFetchTickets(bookingIdList);
        for(Booking booking : bookingList) {
            booking.setBookingStatus(BookingStatus.CANCELED);
            booking.getTicket().setBookedAtTime(null);
            booking.getTicket().setTicketStatus(TicketStatus.AVAILABLE);
        }
        bookingRepository.save(bookingList);
        return bookingList;
    }

    @Override
    public List<Booking> getActiveBookingsWithJoinTickets(String email) {
        List<Booking> bookingList = bookingRepository.findAllBookingsFetchTicketByEmailByStatus(email, BookingStatus.ACTIVE);
        return bookingList;
    }

    @Transactional
    @Override
    public Booking payForBooking(Long bookingId) {
        Booking booking = bookingRepository.findOneBookingFetchTicket(bookingId);
        if(booking.getBookingStatus()!= BookingStatus.ACTIVE) {
            throw new RuntimeException("booking with id " + booking.getId() + " has wrong booking status: " + booking.getBookingStatus() + ". The booking, to be payed, must have BookingStatus.ACTIVE.");
        }
        booking.setBookingStatus(BookingStatus.REDEEMED);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        booking.getTicket().setTicketStatus(TicketStatus.SOLD);
        booking.getTicket().setSoldAtTime(timestamp);
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public Booking findById(Long bookingId) {
        return bookingRepository.findOne(bookingId);
    }
}
