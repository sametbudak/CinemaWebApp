package org.kiev.cinema.repository;

import org.kiev.cinema.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT DISTINCT s FROM Screening s " +
            "INNER JOIN s.movie m " +
            "INNER JOIN s.room r " +
            "WHERE date(s.startTime)=:date AND m.id=:movie_id AND r.address.id=:address_id " +
            "ORDER BY s.startTime ASC")
    List<Screening> findAllByMovieByAddressByDate
            (@Param("movie_id") Long movieId, @Param("address_id") Integer addressId, @Param("date") Date date);

    @Query("SELECT DISTINCT s FROM Screening s " +
            "INNER JOIN s.movie m " +
            "INNER JOIN s.room r " +
            "WHERE date(s.startTime) IN :date_list AND m.id=:movie_id AND r.address.id=:address_id " +
            "ORDER BY s.startTime ASC")
    List<Screening> findAllByMovieByAddressByDateList
            (@Param("movie_id") Long movieId, @Param("address_id") Integer addressId, @Param("date_list") List<Date> date);

    @Query("SELECT DISTINCT s FROM Screening s " +
            "INNER JOIN s.movie m " +
            "INNER JOIN s.room r " +
            "WHERE date(s.startTime) IN :date_list AND m.id=:movie_id AND r.address.id=:address_id " +
            "AND time(s.startTime) BETWEEN :from_time AND :till_time " +
            "ORDER BY s.startTime ASC")
    List<Screening> findAllByMovieByAddressByDateListStartingFromTimeTillTime
            (@Param("movie_id") Long movieId, @Param("address_id") Integer addressId, @Param("date_list") List<Date> date,
             @Param("from_time") Time fromTime, @Param("till_time") Time tillTime );

    @Query("SELECT DISTINCT s FROM Screening s " +
            "WHERE s.room.id=:room_id " +
            "AND (" +
            "(s.endTime >= :from_time AND s.endTime <= :till_time) " +
            "OR (s.startTime >= :from_time AND s.startTime <= :till_time) " +
            "OR (s.startTime <= :from_time AND s.endTime >= :till_time)" +
            ")")
    List<Screening> findAllScreeningsByRoomFromTimeTillTime
            (@Param("room_id") Integer roomId, @Param("from_time") Timestamp fromTime, @Param("till_time") Timestamp tillTime );

    @Query("SELECT count(s) FROM Screening s " +
            "WHERE s.room.id=:room_id " +
            "AND (" +
            ":from_time BETWEEN s.startTime AND s.endTime " +
            "OR :till_time BETWEEN s.startTime AND s.endTime" +
            ")")
    Long countAllScreeningsByRoomFromTimeTillTime
            (@Param("room_id") Integer roomId, @Param("from_time") Timestamp fromTime, @Param("till_time") Timestamp tillTime );

    @Query("SELECT DISTINCT s FROM Screening s " +
            "INNER JOIN s.movie m " +
            "INNER JOIN s.room r " +
            "WHERE m.id=:movie_id AND r.address.id=:address_id " +
            "AND date(s.startTime) BETWEEN date(:from_date) AND date(:till_date) " +
            "AND time(s.startTime) BETWEEN :from_time AND :till_time " +
            "ORDER BY s.startTime ASC")
    List<Screening> findAllByMovieByAddressFromDateTillDateStartingFromTimeTillTime
            (@Param("movie_id") Long movieId, @Param("address_id") Integer addressId,
             @Param("from_date") Date fromDate, @Param("till_date") Date tillDate,
             @Param("from_time") Time fromTime, @Param("till_time") Time tillTime );

    @Query("SELECT DISTINCT s FROM Screening s " +
            "INNER JOIN s.movie m " +
            "INNER JOIN s.room r " +
            "WHERE m.id=:movie_id AND r.address.id=:address_id " +
            "AND date(s.startTime) BETWEEN date(:from_date) AND date(:till_date) " +
            "ORDER BY s.startTime ASC")
    List<Screening> findAllByMovieByAddressFromDateTillDate
            (@Param("movie_id") Long movieId, @Param("address_id") Integer addressId,
             @Param("from_date") Date fromDate, @Param("till_date") Date tillDate);

    @Query("SELECT DISTINCT s FROM Screening s " +
            "INNER JOIN s.movie m " +
            "WHERE m.id=:movie_id " +
            "ORDER BY s.startTime ASC")
    List<Screening> findAllByMovie(@Param("movie_id") Long movieId);

    @Query("SELECT count(s) FROM Screening s " +
            "WHERE s.movie.id=:movie_id")
    long countAllByMovie(@Param("movie_id") Long movieId);

}
