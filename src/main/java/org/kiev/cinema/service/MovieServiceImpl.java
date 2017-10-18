package org.kiev.cinema.service;

import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.entity.Genre;
import org.kiev.cinema.entity.Movie;
import org.kiev.cinema.enums.GenreEnum;
import org.kiev.cinema.enums.MovieStatus;
import org.kiev.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreService genreService;

    @Override
    @Transactional
    public void add(Movie movie) {
        List<GenreEnum> genreEnums = movie.genresListToEnumList();
        List<Genre> genresEntity = genreService.listByNames(genreEnums);
        movie.setGenres(genresEntity);
        movieRepository.save(movie);
    }

    @Override
    public void update(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public Long countAll() {
        return movieRepository.count();
    }

    @Override
    public List<Movie> listAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> listAllActive() {
        return movieRepository.findAllByStatus(MovieStatus.ACTIVE);
    }

    @Override
    public List<Movie> listAllFromToday() {
        Date today = DateUtils.getTodayDateWithZeroTime();
        return movieRepository.findAllFromDate(today);
    }

    @Override
    public List<Movie> listAllFromToday(Integer daysInterval) {
        Date today = DateUtils.getTodayDateWithZeroTime();
        Date lastDate = DateUtils.getLastDateWithZeroTime(today, daysInterval);
        return movieRepository.findAllFromDateTillDate(today, lastDate);
    }

    @Override
    public List<Movie> listByIds(List<Long> movieIdList) {
        return movieRepository.findAllByIdIn(movieIdList);
    }

    @Override
    public Movie findById(Long movieId) {
        return movieRepository.findOne(movieId);
    }

    @Override
    public Movie findByScreening(Long screeningId) {
        return movieRepository.findOneByScreening(screeningId);
    }

    @Override
    public Movie findByTicket(Long ticketId) {
        return movieRepository.findOneByTicket(ticketId);
    }
}
