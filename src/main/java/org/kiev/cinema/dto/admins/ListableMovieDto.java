package org.kiev.cinema.dto.admins;

import org.kiev.cinema.enums.MovieStatus;

public class ListableMovieDto {

    private Long id;
    private String title;
    private MovieStatus status;
    private Boolean deleteIsDisabled = false;
    private String screeningMessage = "";
    private String ticketMessage = "";

    public ListableMovieDto(Long id, String title, MovieStatus status, Integer screeningsCount, Long ticketsCount) {
        this.id = id;
        this.title = title;
        this.status = status;
        setMessages(screeningsCount, ticketsCount);
    }

    private void setMessages(Integer screeningsCount, Long ticketsCount) {
        if(screeningsCount != null && screeningsCount > 0) {
            this.deleteIsDisabled = true;
            this.screeningMessage = "Movie cannot be deleted. There are " + screeningsCount + " movie screening(s).";
            if(ticketsCount != null && ticketsCount > 0) {
                this.ticketMessage = "There are " + ticketsCount + " ticket(s) sold/booked.";
            }
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public Boolean getDeleteIsDisabled() {
        return deleteIsDisabled;
    }

    public String getScreeningMessage() {
        return screeningMessage;
    }

    public String getTicketMessage() {
        return ticketMessage;
    }

}
