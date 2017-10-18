package org.kiev.cinema.repository;

import org.kiev.cinema.entity.Booking;
import org.kiev.cinema.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Modifying
    @Query("UPDATE Booking b SET b.bookingStatus=:booking_status WHERE b.id=:booking_id")
    Booking updateStatus(@Param("booking_status") BookingStatus bookingStatus, @Param("booking_id") Long bookingId);

    @Query("SELECT b FROM Booking b LEFT JOIN b.ticket WHERE b.id=:booking_id")
    Booking findOneBookingFetchTicket(@Param("booking_id") Long bookingId);

    @Query("SELECT b FROM Booking b LEFT JOIN b.ticket t WHERE b.email=:email AND b.bookingStatus=:booking_status")
    List<Booking> findAllBookingsFetchTicketByEmailByStatus(@Param("email") String email, @Param("booking_status") BookingStatus bookingStatus);

    @Query("SELECT b FROM Booking b LEFT JOIN b.ticket WHERE b.id IN :booking_id_list")
    List<Booking> findAllBookingsFetchTickets(@Param("booking_id_list") List<Long> bookingId);
}
