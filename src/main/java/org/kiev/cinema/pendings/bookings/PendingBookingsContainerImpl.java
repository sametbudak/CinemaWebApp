package org.kiev.cinema.pendings.bookings;

import org.kiev.cinema.CinemaConstants;
import org.kiev.cinema.entity.Booking;
import org.kiev.cinema.service.BookingService;
import org.kiev.cinema.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class PendingBookingsContainerImpl implements PendingBookingsContainer {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private MailSender mailSender;

    private ConcurrentMap<String, PendingBooking> map = new ConcurrentHashMap<>();
    private final int MAX_SIZE = CinemaConstants.PENDING_BOOKINGS_QUANTITY_LIMIT;
    private Timer timer = new Timer();

    @Override
    public boolean addBooking(PendingBooking pendingBooking) {
        synchronized (this) {
            if(map.containsKey(pendingBooking.getEmail()) || map.size()>=MAX_SIZE) {
                return false;
            } else {
                map.put(pendingBooking.getEmail(), pendingBooking);
                pendingBooking.setPendingBookingsContainer(this);
                timer.schedule(pendingBooking, PendingBooking.PENDING_LIMIT);
                return true;
            }
        }
    }

    @Override
    public PendingBooking deleteBooking(String email) {
        PendingBooking pendingBooking = map.remove(email);
        if(pendingBooking == null) {
            return null;
        }
        pendingBooking.cancel();
        int i = timer.purge();
        System.out.println("cancelled " + i + " task(s)");
        return pendingBooking;
    }

    @Override
    public boolean isEmailUsed(String email) {
        return (map.get(email) == null) ? false : true;
    }

    @Override
    public PendingBooking cancelBooking(String email) {
        PendingBooking pendingBooking = map.remove(email);
        if(pendingBooking == null) {
            return null;
        }
        List<Booking> bookingList = bookingService.cancelBookings(pendingBooking.getBookingIds());
        if(bookingList.size() != pendingBooking.getBookingIds().size()) {
            System.out.println("error cancel bookings " + pendingBooking.getBookingIds());
        }
        mailSender.sendCancelBookingsMessage(email);
        return pendingBooking;
    }
}
