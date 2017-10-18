package org.kiev.cinema.dto.admins;

import java.sql.Timestamp;

public class ScreeningDto {
    private Long screeningId;
    private String movieTitle;
    private Integer minutes;
    private Timestamp startTime;
    private Integer roomId;

    // Spring Jpa gets Timestamp as java.util.Date
    public ScreeningDto(Long screeningId, String movieTitle, Integer minutes, java.util.Date startTime, Integer roomId) {
        this.screeningId = screeningId;
        this.movieTitle = movieTitle;
        this.minutes = minutes;
        this.startTime = new Timestamp(startTime.getTime());
        this.roomId = roomId;
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Integer getRoomId() {
        return roomId;
    }
}

