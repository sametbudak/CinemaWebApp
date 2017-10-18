package org.kiev.cinema.dto.visitors;

import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.entity.Address;
import org.kiev.cinema.entity.Booking;

import java.sql.Timestamp;

public class BookedTicketDto {
    private String bookingId;
    private String email;
    private String code;
    private String bookingTime;
    private String address;
    private String movieTitle;
    private String movieDate;
    private String movieTime;
    private String duration;
    private String place;
    private Double price;

    public BookedTicketDto(Builder builder) {
        this.bookingId = builder.bookingId;
        this.email = builder.email;
        this.code = builder.code;
        this.bookingTime = builder.bookingTime;
        this.address = builder.address;
        this.movieTitle = builder.movieTitle;
        this.movieDate = builder.movieDate;
        this.movieTime = builder.movieTime;
        this.duration = builder.duration;
        this.place = builder.place;
        this.price = builder.price;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public String getAddress() {
        return address;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public String getMovieTime() {
        return movieTime;
    }

    public String getDuration() {
        return duration;
    }

    public String getPlace() {
        return place;
    }

    public Double getPrice() {
        return price;
    }

    public static class Builder {
        private String bookingId;
        private String email;
        private String code;
        private String bookingTime;
        private String address;
        private String movieTitle;
        private String movieDate;
        private String movieTime;
        private String duration;
        private String place;
        private Double price;

        public Builder() {
        }

        public Builder setBookingInfo(Booking booking) {
            this.bookingId = String.format("%05d", booking.getId());
            this.email = booking.getEmail();
            this.code = booking.getCode();
            this.bookingTime = DateUtils.formatAsyyyyMMddHHmmss(booking.getTimestamp());
            return this;
        }

        public Builder setAddress(Address address) {
            this.address = address.getFullAddress();
            return this;
        }

        public Builder setMovieTitle(String movieTitle) {
            this.movieTitle = movieTitle;
            return this;
        }

        public Builder setScreeningDateTime(Timestamp timestamp) {
            this.movieDate = DateUtils.formatAsEEEMMMdyyyy(timestamp);
            this.movieTime = DateUtils.formatAsHHmm(timestamp);
            return this;
        }

        public Builder setMovieDuration(Integer minutes) {
            this.duration = String.format("%d HR %d MIN", minutes/60, minutes%60 );
            return this;
        }

        public Builder setPlace(Integer row, Integer column) {
            this.place = String.format("%d-%d", row, column);
            return this;
        }

        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public BookedTicketDto build() {
            return new BookedTicketDto(this);
        }
    }
}
