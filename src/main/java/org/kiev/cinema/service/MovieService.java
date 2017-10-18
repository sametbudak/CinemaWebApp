package org.kiev.cinema.service;

import org.kiev.cinema.entity.Movie;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface MovieService {
    void add(Movie movie);
    void update(Movie movie);
    Long countAll();
    List<Movie> listAll();
    List<Movie> listAllActive();
    List<Movie> listAllFromToday();
    List<Movie> listAllFromToday(Integer daysInterval);
    List<Movie> listByIds(List<Long> movieIdList);
    Movie findById(Long movieId);
    Movie findByScreening(Long screeningId);
    Movie findByTicket(Long ticketId);
}
