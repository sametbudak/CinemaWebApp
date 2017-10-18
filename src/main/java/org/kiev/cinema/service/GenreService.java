package org.kiev.cinema.service;

import org.kiev.cinema.entity.Genre;
import org.kiev.cinema.enums.GenreEnum;

import java.util.List;

public interface GenreService {
    void add(Genre genre);
    List<Genre> listAll();
    List<Genre> listByMovie(Long movieId);
    List<Genre> listByNames(List<GenreEnum> genreEnumList);
    Genre findById(Integer id);
    Genre findByName(GenreEnum genreEnum);

}
