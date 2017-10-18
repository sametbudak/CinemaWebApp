package org.kiev.cinema.repository;

import org.kiev.cinema.entity.Genre;
import org.kiev.cinema.enums.GenreEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    @Query("SELECT g FROM Genre g WHERE g.movies=:movie_id ")
    List<Genre> findAllByMovie(@Param("movie_id") Long movieId);

    @Query("SELECT g FROM Genre g WHERE g.genreEnum=:genre_enum")
    Genre findOneByGenreEnum(@Param("genre_enum") GenreEnum genreEnum);

    @Query("SELECT g FROM Genre g WHERE g.genreEnum IN :genre_enum_list")
    List<Genre> findAllByGenreEnumsIn(@Param("genre_enum_list") List<GenreEnum> genreEnumList);
}
