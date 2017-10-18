package org.kiev.cinema.repository;

import org.kiev.cinema.entity.Movie;
import org.kiev.cinema.enums.MovieStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE m.movieStatus=:status")
    List<Movie> findAllByStatus(@Param("status") MovieStatus movieStatus);

    @Query("SELECT m FROM Movie m WHERE m.endDate>=:date")
    List<Movie> findAllFromDate(@Param("date")Date date);

    @Query("SELECT m FROM Movie m WHERE m.endDate>=:date_from AND m.startDate<=:date_till")
    List<Movie> findAllFromDateTillDate(@Param("date_from")Date dateFrom, @Param("date_till") Date dateTill);

    @Query("SELECT m FROM Movie m WHERE m.id IN :id_list")
    List<Movie> findAllByIdIn(@Param("id_list") List<Long> movieIdList);

    @Query("SELECT m FROM Movie m INNER JOIN m.screenings s WHERE s.id=:screening_id")
    Movie findOneByScreening(@Param("screening_id") Long screeningId);

    @Query(value =
            "SELECT m.id, m.title, m.minutes, m.movie_status, m.start_date, m.end_date, m.release_year, " +
                    "m.directed_by, m.screenplay, m.cast, m.country, m.description, m.poster, m.trailer " +
                    "FROM movies m " +
                    "INNER JOIN screenings s ON s.movie_id=m.id " +
                    "INNER JOIN tickets t ON t.screening_id=s.id " +
                    "WHERE t.id=:ticket_id", nativeQuery = true)
    Movie findOneByTicket(@Param("ticket_id") Long ticketId);

}
