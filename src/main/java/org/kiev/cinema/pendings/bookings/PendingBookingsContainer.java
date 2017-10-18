package org.kiev.cinema.pendings.bookings;

public interface PendingBookingsContainer {
    boolean addBooking(PendingBooking pendingBooking);

    PendingBooking deleteBooking(String email);

    boolean isEmailUsed(String email);

    PendingBooking cancelBooking(String email);
}
