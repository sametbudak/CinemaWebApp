package org.kiev.cinema.dto.admins;

import java.sql.Timestamp;

public class CreateScreeningDto {
    private Integer roomId;
    private Timestamp timestamp;
    private Long movieId;
    private Double price;

    public CreateScreeningDto(Integer roomId, Timestamp timestamp, Long movieId, Double price) {
        this.roomId = roomId;
        this.timestamp = timestamp;
        this.movieId = movieId;
        this.price = price;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Long getMovieId() {
        return movieId;
    }

    public Double getPrice() {
        return price;
    }
}
