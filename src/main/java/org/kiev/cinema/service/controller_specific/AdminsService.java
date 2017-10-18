package org.kiev.cinema.service.controller_specific;

import org.kiev.cinema.date.WeekOrdinal;
import org.kiev.cinema.dto.admins.*;
import org.kiev.cinema.entity.Movie;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminsService {
    SchedulingDto getSchedulingDto(WeekOrdinal week);
    List<ActiveMovieDto> getActiveMovieDtoList();
    int addScreenings(List<CreateScreeningDto> createScreeningDtoList);
    EditableMovieDto getEditableMovieDto(Long movieId);
    EditableMovieDto updateMovie(Movie editedMovie);
    boolean deleteMovie(Long movieId);
    boolean deleteScreening(Long screeningId);
    List<ListableMovieDto> getListableMovieDtoList(Pageable pageable);
    List<ListableScreeningDto> getListableScreeningDtoList(Pageable pageable);
}
