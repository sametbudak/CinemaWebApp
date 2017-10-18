package org.kiev.cinema.dto.admins;

import org.kiev.cinema.entity.Genre;
import org.kiev.cinema.entity.Movie;
import org.kiev.cinema.enums.GenreEnum;

import java.util.*;

public class EditableMovieDto {

    private static final String READONLY = MovieReadonlyFields.READONLY;
    private boolean hasScreening;

    private Movie movie;
    private MovieReadonlyFields readonlyFields = new MovieReadonlyFields();
    private List<CheckedGenre> checkedGenreList;

    public EditableMovieDto(Movie movie, boolean hasScreening) {
        this.hasScreening = hasScreening;
        setMovie(movie);
        /*this.movie = movie;
        this.setDisabledFields(hasScreening);
        List<GenreEnum> movieGenreList = movie.genresListToEnumList();
        this.setCheckedGenreList(movieGenreList);*/
    }

    public EditableMovieDto() {
    }

    private void setCheckedGenreList(List<GenreEnum> movieGenreList) {
        checkedGenreList = new ArrayList<>();
        Set<GenreEnum> genreEnumSet = EnumSet.allOf(GenreEnum.class);
        for(GenreEnum genreEnum: genreEnumSet) {
            boolean isChecked;
            if(movieGenreList.contains(genreEnum)) {
                isChecked = true;
            } else {
                isChecked = false;
            }
            CheckedGenre checkedGenre = new CheckedGenre(genreEnum, isChecked);
            checkedGenreList.add(checkedGenre);
        }
    }

    private void setMovie(Movie movie) {
        if (this.movie != null && !this.movie.getId().equals(movie.getId()) ) {
            throw new RuntimeException("movie's id can not be changed");
        }

        this.movie = movie;
        if(this.hasScreening) {
            this.readonlyFields.disableFieldsIfMovieHasScreening();
        }
        List<GenreEnum> movieGenreList = movie.genresListToEnumList();
        System.out.println(movieGenreList);
        setCheckedGenreList(movieGenreList);
    }

    public Movie getMovie() {
        return movie;
    }

    public MovieReadonlyFields getReadonlyFields() {
        return readonlyFields;
    }

    public List<CheckedGenre> getCheckedGenreList() {
        return checkedGenreList;
    }

    public Movie getUpdatedMovie(Movie editedMovie, List<Genre> dbGenres) {
        if( !readonlyFields.movieStatus.equals(READONLY) && editedMovie.getMovieStatus() != null) {
            movie.setMovieStatus(editedMovie.getMovieStatus());
        }
        if( !readonlyFields.title.equals(READONLY) && editedMovie.getTitle() != null) {
            movie.setTitle(editedMovie.getTitle());
        }
        if( !readonlyFields.minutes.equals(READONLY) && editedMovie.getMinutes() != null) {
            movie.setMinutes(editedMovie.getMinutes());
        }
        if( !readonlyFields.releaseYear.equals(READONLY) && editedMovie.getReleaseYear() != null ) {
            movie.setReleaseYear(editedMovie.getReleaseYear());
        }
        if( !readonlyFields.directedBy.equals(READONLY) && editedMovie.getDirectedBy() != null) {
            movie.setDirectedBy(editedMovie.getDirectedBy());
        }
        if( !readonlyFields.screenplay.equals(READONLY) && editedMovie.getScreenplay() != null) {
            movie.setScreenplay(editedMovie.getScreenplay());
        }
        if( !readonlyFields.cast.equals(READONLY) && editedMovie.getCast() != null) {
            movie.setCast(editedMovie.getCast());
        }
        if( !readonlyFields.country.equals(READONLY) && editedMovie.getCountry() != null) {
            movie.setCountry(editedMovie.getCountry());
        }
        if( !readonlyFields.description.equals(READONLY) && editedMovie.getDescription() != null) {
            movie.setDescription(editedMovie.getDescription());
        }
        if( !readonlyFields.genres.equals(READONLY) && dbGenres != null && !dbGenres.isEmpty()) {
            movie.setGenres(dbGenres);
            List<GenreEnum> movieGenreList = movie.genresListToEnumList();
            setCheckedGenreList(movieGenreList);
        }
        if( !readonlyFields.poster.equals(READONLY) && editedMovie.getPoster() != null) {
            movie.setPoster(editedMovie.getPoster());
        }
        if( !readonlyFields.trailer.equals(READONLY) && editedMovie.getTrailer() != null)  {
            movie.setTrailer(editedMovie.getTrailer());
        }
        return movie;
    }

}
