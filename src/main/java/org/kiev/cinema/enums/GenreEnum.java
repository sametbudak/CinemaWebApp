package org.kiev.cinema.enums;

public enum GenreEnum {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    FANTASY("Fantasy"),
    HISTORICAL("Historical"),
    HISTORICAL_FICTION("Historical Fiction"),
    HORROR("Horror"),
    MYSTERY("Mystery"),
    POLITICAL("Political"),
    ROMANCE("Romance"),
    SAGA("Saga"),
    THRILLER("Thriller"),
    ANIMATION("Animation"),
    SCI_FI("Sci-Fi"),
    FAMILY("Family"),
    BIOGRAPHY("Biography"),
    MUSIC("Music"),
    WAR("War");

    private String genre;

    GenreEnum(String genre) {
        this.genre = genre;
    }

    public String get() {
        return genre;
    }
}
