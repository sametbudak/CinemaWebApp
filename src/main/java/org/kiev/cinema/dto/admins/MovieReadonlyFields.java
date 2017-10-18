package org.kiev.cinema.dto.admins;

public class MovieReadonlyFields {

    protected static final String READONLY = "readonly";

    protected String movieStatus = "";
    protected String title = "";
    protected String minutes = "";
    protected String releaseYear = "";
    protected String directedBy = "";
    protected String screenplay = "";
    protected String cast= "";
    protected String country = "";
    protected String description = "";
    protected String genres = "";
    protected String poster = "";
    protected String trailer = "";

    protected void disableFieldsIfMovieHasScreening() {
        this.title = READONLY;
        this.minutes = READONLY;
    }

    public String getMovieStatus() {
        return movieStatus;
    }

    public String getDisabled() {
        return READONLY;
    }

    public String getTitle() {
        return title;
    }

    public String getMinutes() {
        return minutes;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getDirectedBy() {
        return directedBy;
    }

    public String getScreenplay() {
        return screenplay;
    }

    public String getCast() {
        return cast;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getGenres() {
        return genres;
    }

    public String getPoster() {
        return poster;
    }

    public String getTrailer() {
        return trailer;
    }
}
