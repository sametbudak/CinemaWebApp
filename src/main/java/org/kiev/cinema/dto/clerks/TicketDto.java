package org.kiev.cinema.dto.clerks;

import org.apache.commons.lang.StringUtils;
import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.entity.Address;
import org.kiev.cinema.entity.Booking;

import java.sql.Timestamp;
import java.util.LinkedList;

public class TicketDto {

    private Long ticketId;
    private String bookingId; // applicable if booked
    private String email; // applicable if booked
    private String code; // applicable if booked
    private Timestamp bookedAtTime; // applicable if booked
    private Timestamp soldAtTime;
    private String theaterName;
    private String cityAndDistrict;
    private String streetAndNumber;
    private String movieTitle;
    private String movieDate;
    private String movieTime;
    private String duration;
    private String place;
    private Double price;

    private TicketDto(Builder builder) {
        this.ticketId = builder.ticketId;
        this.bookingId = builder.bookingId;
        this.email = builder.email;
        this.code = builder.code;
        this.bookedAtTime = builder.bookedAtTime;
        this.soldAtTime = builder.soldAtTime;
        this.theaterName = builder.theaterName;
        this.cityAndDistrict = builder.cityAndDistrict;
        this.streetAndNumber = builder.streetAndNumber;
        this.movieTitle = builder.movieTitle;
        this.movieDate = builder.movieDate;
        this.movieTime = builder.movieTime;
        this.duration = builder.duration;
        this.place = builder.place;
        this.price = builder.price;
    }

    public LinkedList<String> createFormattedStrings() {
        LinkedList<String> list = new LinkedList<>();
        int width = 50;
        list.add(centerString(theaterName, width));
        list.add(centerString(cityAndDistrict, width));
        list.add(centerString(streetAndNumber, width));
        list.add(StringUtils.repeat("-", width));
        list.add(String.format("|%-8.8s: %-38.38s|", "Movie", movieTitle));
        list.add(String.format("|%-8.8s: %-38.38s|", "Date", movieDate));
        list.add(String.format("|%-8.8s: %-38.38s|", "Time", movieTime));
        list.add(String.format("|%-8.8s: %-38.38s|", "Duration", duration));
        list.add(String.format("|%-8.8s: %-38.38s|", "Place", place));
        list.add(String.format("|%-8.8s: %-38.38s|", "Price", price));
        return list;
    }

    private String centerString(String stringToCenter, int width) {
        int length = stringToCenter.length();
        String centeredStr;
        if (length < width) {
            int rightOffset = width / 2 + length / 2;
            centeredStr = String.format("%" + rightOffset + "s", stringToCenter);
            centeredStr = String.format("%-" + width + "." + width + "s", centeredStr);
        } else {
            throw new RuntimeException("String length exceeds " + width + " characters");
        }
        return centeredStr;
    }

    public Long getTicketId() {
        return ticketId;
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

    public Timestamp getBookedAtTime() {
        return bookedAtTime;
    }

    public Timestamp getSoldAtTime() {
        return soldAtTime;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public String getCityAndDistrict() {
        return cityAndDistrict;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
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
        private Long ticketId;
        private String bookingId; // applicable if booked
        private String email; // applicable if booked
        private String code; // applicable if booked
        private Timestamp bookedAtTime; // applicable if booked
        private Timestamp soldAtTime;
        private String theaterName;
        private String cityAndDistrict;
        private String streetAndNumber;
        private String movieTitle;
        private String movieDate;
        private String movieTime;
        private String duration;
        private String place;
        private Double price;

        public Builder() {
        }

        public Builder setBookingInfo(Booking booking) {
            if(booking != null) {
                this.bookingId = String.format("%05d", booking.getId());
                this.email = booking.getEmail();
                this.code = booking.getCode();
                this.bookedAtTime = booking.getTimestamp();
            }
            return this;
        }

        public Builder setTicketId(Long ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder setSoldAtTime(Timestamp timestamp) {
            this.soldAtTime = timestamp;
            return this;
        }

        public Builder setAddress(Address address) {
            this.theaterName = Address.getName();
            this.cityAndDistrict = String.format("%s, %s,", address.getCity(), address.getDistrict());
            this.streetAndNumber = String.format("No.%d %s", address.getStreetNumber(), address.getStreet());
            return this;
        }

        public Builder setMovieTitle(String movieTitle) {
            this.movieTitle = movieTitle;
            return this;
        }

        public Builder setMovieScreeningDateTime(Timestamp timestamp) {
            this.movieDate = DateUtils.formatAsEEEMMMdyyyy(timestamp);
            this.movieTime = DateUtils.formatAsHHmm(timestamp);
            return this;
        }

        public Builder setDuration(Integer minutes) {
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

        public TicketDto build() {
            return new TicketDto(this);
        }
    }
}
