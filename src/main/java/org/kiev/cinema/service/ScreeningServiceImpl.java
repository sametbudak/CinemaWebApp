package org.kiev.cinema.service;

import org.kiev.cinema.entity.Movie;
import org.kiev.cinema.entity.Screening;
import org.kiev.cinema.repository.MovieRepository;
import org.kiev.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Long countAll() {
        return screeningRepository.count();
    }

    @Override
    @Transactional
    public void add(Screening screening) {
        screeningRepository.save(screening);
        Movie movie = movieRepository.findOneByScreening(screening.getId());
        screening.setEndTime(new Timestamp(screening.getStartTime().getTime()+movie.getMinutes()*60*1000));
        screeningRepository.save(screening);
    }

    @Override
    public void addAll(List<Screening> screenings) {
        screeningRepository.save(screenings);
    }

    @Override
    public void update(Screening screening) {
        if(screening.getId() != null && screening.getId() > 0) {
            Movie movie = movieRepository.findOneByScreening(screening.getId());
            screening.setEndTime(new Timestamp(screening.getStartTime().getTime()+movie.getMinutes()*60*1000));
            screeningRepository.save(screening);
        } else {
            throw new RuntimeException("screening can not be updated because id is not set");
        }
    }

    @Override
    public List<Screening> listAll() {
        return screeningRepository.findAll();
    }

    @Override
    public Screening findById(Long screeningId) {
        return screeningRepository.findOne(screeningId);
    }
}
