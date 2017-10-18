package org.kiev.cinema.dto.admins;

import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.entity.Address;

import java.sql.Timestamp;

public class ListableScreeningDto {
    private Long id;
    private String address;
    private String room;
    private String date;
    private String time;
    private String movieTitle;
    private boolean deleteIsDisabled;
    private String ticketMessage;

    public ListableScreeningDto(Long screeningId, java.util.Date date, Address address,
                                String roomTitle, String movieTitle, Long ticketsCount) {
        this.id = screeningId;
        this.address = address.getAddress();
        this.room = roomTitle;
        Timestamp timestamp = new Timestamp(date.getTime());
        this.date = DateUtils.formatAsyyyyMMdd(timestamp);
        this.time = DateUtils.formatAsHHmm(timestamp);
        this.movieTitle = movieTitle;
        setTicketMessage(ticketsCount);
    }

    private void setTicketMessage(Long ticketsCount) {
        if(ticketsCount != null && ticketsCount > 0) {
            this.deleteIsDisabled = true;
            this.ticketMessage = "Screening cannot be deleted. There are " + ticketsCount + " ticket(s) sold/booked.";
        }
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getRoom() {
        return room;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public boolean isDeleteIsDisabled() {
        return deleteIsDisabled;
    }

    public String getTicketMessage() {
        return ticketMessage;
    }
}
