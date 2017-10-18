package org.kiev.cinema.dto.admins;

import org.kiev.cinema.entity.Movie;

public class ActiveMovieDto {

    private Long movieId;
    private String movieTitle;
    private Integer minutes;

    public ActiveMovieDto(Long movieId, String movieTitle, Integer minutes) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.minutes = minutes;
    }

    public ActiveMovieDto(Movie movie) {
        this.movieId = movie.getId();
        this.movieTitle = movie.getTitle();
        this.minutes = movie.getMinutes();
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public Integer getMinutes() {
        return minutes;
    }
}
