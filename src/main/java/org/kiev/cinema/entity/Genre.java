package org.kiev.cinema.entity;

import org.kiev.cinema.enums.GenreEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false, unique = true, name = "genre_enum")
    @Enumerated(EnumType.STRING)
    private GenreEnum genreEnum;
    @ManyToMany(mappedBy = "genres", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Movie> movies = new ArrayList<>();

    public Genre() {
    }

    public Genre(GenreEnum genreEnum) {
        this.genreEnum = genreEnum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GenreEnum getGenreEnum() {
        return genreEnum;
    }

    public void setGenreEnum(GenreEnum genreEnum) {
        this.genreEnum = genreEnum;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    // add
    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        if (!id.equals(genre.id)) return false;
        return genreEnum == genre.genreEnum;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + genreEnum.hashCode();
        return result;
    }

    public String getGenreString() {
        return genreEnum.get();
    }
}
