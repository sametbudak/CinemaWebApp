package org.kiev.cinema.service;

import org.kiev.cinema.entity.Screening;

import java.util.List;

public interface ScreeningService {
    void add(Screening screening);
    void addAll(List<Screening> screenings);
    void update(Screening screening);
    Long countAll();
    List<Screening> listAll();
    Screening findById(Long screeningId);
}
