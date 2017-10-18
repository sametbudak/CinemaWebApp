package org.kiev.cinema.entity;

import net.coobird.thumbnailator.Thumbnails;
import org.hibernate.annotations.Type;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.kiev.cinema.CinemaConstants;
import org.kiev.cinema.enums.GenreEnum;
import org.kiev.cinema.enums.MovieStatus;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Integer minutes;
    @Column(nullable = false, name = "movie_status")
    @Enumerated(EnumType.STRING)
    private MovieStatus movieStatus = MovieStatus.ACTIVE;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    @Column(nullable = false, length = 4, name="release_year")
    @Min(1900)
    @Max(2025)
    private Integer releaseYear;

    @Column(nullable = false, length = 255, name="directed_by")
    private String directedBy;

    @Column(nullable = false, length = 255)
    private String screenplay;

    @Column(nullable = false, length = 255)
    private String cast;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    @Type(type="text")
    private String description;

    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
                name = "movie_genre",
                joinColumns = {@JoinColumn(name = "movie_id", referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "genre_id", referencedColumnName = "id")}  )
    private List<Genre> genres = new ArrayList<>();

    @Column(nullable = false)
    private String poster;

    @Column(nullable = false)
    private String trailer;

    @OneToMany(mappedBy = "movie", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Screening> screenings = new ArrayList<>();

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public MovieStatus getMovieStatus() {
        return movieStatus;
    }

    public void setMovieStatus(MovieStatus movieStatus) {
        this.movieStatus = movieStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirectedBy() {
        return directedBy;
    }

    public void setDirectedBy(String directedBy) {
        this.directedBy = directedBy;
    }

    public String getScreenplay() {
        return screenplay;
    }

    public void setScreenplay(String screenplay) {
        this.screenplay = screenplay;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        for(Genre genre : genres) {
            genre.addMovie(this);
        }
        this.genres = genres;
    }

    public String getPoster() {
        return poster;
    }

    public String reducedPosterPath() {
        return poster.replace(CinemaConstants.MOVIES_PATH, "").replace(File.separator, "/");
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTrailer() {
        return trailer;
    }

    public String reducedTrailerPath() {
        return trailer.replace(CinemaConstants.MOVIES_PATH, "").replace("\\", "/");
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    // add
    public void addScreening(Screening screening) {
        screening.setMovie(this);
        this.screenings.add(screening);
    }

    public static Movie fromJSON(Path json) throws IOException, ParseException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, java.text.ParseException {
        Movie movie = new Movie();
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(json.toString());
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        Class<?> clazz = Movie.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            Object obj = jsonObject.get(name);
            if(name.equals("title")
                    || name.equals("directedBy")
                    || name.equals("screenplay")
                    || name.equals("cast")
                    || name.equals("country")
                    || name.equals("description")){
                field.set(movie, (String)obj);
            } else if(name.equals("trailer")
                    || name.equals("poster")) {
                Path filePath = Paths.get(json.getParent().toString() + File.separator + (String)obj);
                if(name.equals("poster")) {
                    Path newFilePath = Paths.get(json.getParent().toString() + File.separator + "thumb_" + (String)obj);
                    Thumbnails.of(filePath.toFile())
                            .forceSize(670, 1000)
                            .outputFormat("jpg")
                            .toFile(newFilePath.toFile());
                    filePath = newFilePath;
                }
                field.set(movie, filePath.toString());

            } else if(name.equals("minutes")){
                field.set(movie, ((Long) obj).intValue());
            } else if(name.equals("genres")) {
                JSONArray jsonArray = (JSONArray) jsonObject.get("genres");
                Iterator<String> iterator = jsonArray.iterator();
                List<Genre> genres = new ArrayList<>();
                while (iterator.hasNext()) {
                    String enumStr = iterator.next();
                    Genre genre = new Genre();
                    genre.setGenreEnum(Enum.valueOf(GenreEnum.class, enumStr.toUpperCase()));
                    genres.add(genre);
                }
                field.set(movie, genres);
            } else if (name.equals("startDate")
                    || name.equals("endDate")) {
                field.set(movie, dateFromString((String)obj));
            } if (name.equals("releaseYear")) {
                field.set(movie, (Integer.parseInt( (String)obj) ));
            }
        }
        return movie;
    }

    private static Date dateFromString(String date) throws java.text.ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = dateFormat.parse(date);
        Date sql = new Date(parsed.getTime());
        return sql;
    }

    public List<String> genresListToStringList() {
        List<String> list = new LinkedList<>();
        for(Genre genre : this.genres) {
            list.add(genre.getGenreString());
        }
        return list;
    }

    public List<GenreEnum> genresListToEnumList() {
        List<GenreEnum> list = new ArrayList<>();
        for(Genre genre : this.genres) {
            list.add(genre.getGenreEnum());
        }
        return list;
    }

    public String durationToHourMinutes() {
        return String.format("%d HR % MIN ", minutes/60, minutes%60);
    }

}
