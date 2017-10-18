package org.kiev.cinema.service;

import org.kiev.cinema.entity.Genre;
import org.kiev.cinema.enums.GenreEnum;
import org.kiev.cinema.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public void add(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public List<Genre> listAll() {
        return genreRepository.findAll();
    }

    @Override
    public List<Genre> listByMovie(Long movieId) {
        return genreRepository.findAllByMovie(movieId);
    }

    @Override
    public List<Genre> listByNames(List<GenreEnum> genreEnumList) {
        return genreRepository.findAllByGenreEnumsIn(genreEnumList);
    }

    @Override
    public Genre findById(Integer id) {
        return genreRepository.findOne(id);
    }

    @Override
    public Genre findByName(GenreEnum genreEnum) {
        return genreRepository.findOneByGenreEnum(genreEnum);
    }
}
