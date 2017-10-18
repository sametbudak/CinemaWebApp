package org.kiev.cinema.dto.visitors;

import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.entity.Address;
import org.kiev.cinema.entity.Ticket;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ScreeningTicketsDto {
    private Long screeningId;
    private List<Ticket> ticketList = new ArrayList<>();
    private Integer maxRow;
    private Integer maxColumn;
    private String movieTitle;
    private Integer hours;
    private Integer minutes;
    private String address;
    private String date;
    private String time;
    private String price;

    public ScreeningTicketsDto(Builder builder) {
        this.screeningId = builder.screeningId;
        this.ticketList = builder.ticketList;
        this.maxRow = builder.maxRow;
        this.maxColumn = builder.maxColumn;
        this.movieTitle = builder.movieTitle;
        this.hours = builder.hours;
        this.minutes = builder.minutes;
        this.address = builder.address;
        this.date = builder.date;
        this.time = builder.time;
        this.price = builder.price;
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public Integer getMaxRow() {
        return maxRow;
    }

    public Integer getMaxColumn() {
        return maxColumn;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public Integer getHours() {
        return hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPrice() {
        return price;
    }

    public static class Builder {
        private Long screeningId;
        private List<Ticket> ticketList = new ArrayList<>();
        private Integer maxRow;
        private Integer maxColumn;
        private String movieTitle;
        private Integer hours;
        private Integer minutes;
        private String address;
        private String date;
        private String time;
        private String price;

        public Builder() {
        }

        public Builder setScreeningId(Long screeningId) {
            this.screeningId = screeningId;
            return this;
        }

        public Builder setTicketsList(List<Ticket> ticketsList) {
            this.ticketList = ticketsList;
            if(this.ticketList== null || this.ticketList.isEmpty()) {
                return this; // RETURN
            }
            double minPrice = ticketsList.get(0).getPrice();
            double maxPrice = minPrice;
            for(Ticket ticket : this.ticketList) {
                double temp = ticket.getPrice();
                if(temp > maxPrice) {
                    maxPrice = temp;
                } else if (temp < minPrice) {
                    minPrice = temp;
                }
            }
            if(minPrice==maxPrice) {
                this.price = String.format("%.02f", maxPrice);
            } else {
                this.price = String.format("%.02f-%.02f", minPrice, maxPrice);;
            }
            return this;
        }

        public Builder setAddress(Address address) {
            this.address = address.getAddress();
            return this;
        }

        public Builder setMovieTitle(String movieTitle) {
            this.movieTitle = movieTitle;
            return this;
        }
        public Builder setMaxRow(Integer maxRow) {
            this.maxRow = maxRow;
            return this;
        }

        public Builder setMaxColumn(Integer maxColumn) {
            this.maxColumn = maxColumn;
            return this;
        }

        public Builder setHoursAndMinutes(Integer durationInMinutes) {
            this.hours = durationInMinutes/60;
            this.minutes = durationInMinutes%60;
            return this;
        }

        public Builder setDateAndTime(Timestamp timestamp) {
            this.date = DateUtils.formatAsdMMMyyyyEEEE(timestamp);
            this.time = DateUtils.formatAsHHmm(timestamp);
            return this;
        }

        public ScreeningTicketsDto build() {
            return new ScreeningTicketsDto(this);
        }
    }

}
