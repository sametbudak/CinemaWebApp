package org.kiev.cinema.service;

import org.kiev.cinema.entity.Booking;

import java.util.List;

public interface BookingService {
    Long add(Booking booking);
    void addAll(List<Booking> bookings);
    void updateStatus(Booking booking);
    List<Booking> cancelBookings(List<Long> bookingIdList);
    List<Booking> getActiveBookingsWithJoinTickets(String email);
    Booking payForBooking(Long bookingId);
    Booking findById(Long bookingId);
}
