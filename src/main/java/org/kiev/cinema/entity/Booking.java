package org.kiev.cinema.entity;

import org.kiev.cinema.enums.BookingStatus;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bookings")
public class Booking {
    @Transient
    static final Long BOOKING_PENDING_LIMIT = 24 * 60 * 60 * 1000L; // ms

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name="booking_status")
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ticket_id")
    private Ticket ticket;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Timestamp timestamp;

    public Booking() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Booking(Ticket ticket, String email, String code) {
        this();
        this.ticket = ticket;
        this.email = email;
        this.code = code;
        ticket.addBooking(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        ticket.addBooking(this);
        this.ticket = ticket;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
