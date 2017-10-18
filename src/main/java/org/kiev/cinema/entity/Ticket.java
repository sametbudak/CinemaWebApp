package org.kiev.cinema.entity;

import org.kiev.cinema.enums.TicketStatus;
import org.kiev.cinema.entity.user.Clerk;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus = TicketStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    @Column(nullable = false)
    private Double price;

    @Column(name="soled_at_time")
    private Timestamp soldAtTime;

    @Column(name="booked_at_time")
    private Timestamp bookedAtTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clerk_id")
    private Clerk clerk;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Timestamp getSoldAtTime() {
        return soldAtTime;
    }

    public void setSoldAtTime(Timestamp soldAtTime) {
        this.soldAtTime = soldAtTime;
    }

    public Timestamp getBookedAtTime() {
        return bookedAtTime;
    }

    public void setBookedAtTime(Timestamp bookedAtTime) {
        this.bookedAtTime = bookedAtTime;
    }

    public Clerk getClerk() {
        return clerk;
    }

    public void setClerk(Clerk clerk) {
        this.clerk = clerk;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
        this.bookedAtTime = booking.getTimestamp();
        this.ticketStatus = TicketStatus.BOOKED;
    }

    public boolean isBooked() {
        if( bookedAtTime != null
                && bookedAtTime.getTime() + Booking.BOOKING_PENDING_LIMIT <= System.currentTimeMillis()){
            return false;
        }
        return (this.bookedAtTime == null) ? false : true;
    }

    public boolean isSold() {
        return (this.soldAtTime == null) ? false : true;
    }

    public String bootstrapClass() {
        return this.ticketStatus.getBootstrapClass();
    }

    public String disabled() {
        return (ticketStatus==TicketStatus.AVAILABLE)? "" : "disabled";
    }
}
