package org.kiev.cinema.pendings.bookings;

import org.kiev.cinema.CinemaConstants;

import java.util.List;
import java.util.TimerTask;

public class PendingBooking extends TimerTask {

    private PendingBookingsContainer pendingBookingsContainer;

    public static final Integer PENDING_LIMIT = CinemaConstants.WAITING_FOR_BOOKING_REDEEM_TIME_LIMIT;
    //public static final Integer PENDING_LIMIT = 5 * 60 * 1000; // 5 minutes

    private final Long timeMillis = System.currentTimeMillis();
    private final String email;
    private final List<Long> bookingIds;

    public PendingBooking(String email, List<Long> bookingIdList) {
            this.email = email;
            this.bookingIds = bookingIdList;
    }

    public void setPendingBookingsContainer(PendingBookingsContainer pendingBookingsContainerMap) {
        this.pendingBookingsContainer = pendingBookingsContainerMap;
    }

    public List<Long> getBookingIds() {
        return bookingIds;
    }

    public String getEmail() {
        return email;
    }

    public Boolean isExpired() {
        return timeMillis + PENDING_LIMIT < System.currentTimeMillis();
    }

    @Override
    public void run() {
        pendingBookingsContainer.cancelBooking(this.email);
        System.out.println("expired booking pending is deleted...");
    }
}
