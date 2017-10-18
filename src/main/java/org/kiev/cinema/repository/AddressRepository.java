package org.kiev.cinema.repository;

import org.kiev.cinema.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(value =
            "SELECT DISTINCT a.id, a.district, a.street, a.street_number, a.picture FROM addresses a " +
            "INNER JOIN rooms r ON r.address_id = a.id " +
            "INNER JOIN screenings s ON s.room_id = r.id " +
            "INNER JOIN movies m ON s.movie_id = m.id AND m.id=:movie_id", nativeQuery = true)
    List<Address> findAllByMovie(@Param("movie_id") Long movieId);

    @Query(value =
            "SELECT DISTINCT a.id, a.district, a.street, a.street_number, a.picture FROM addresses a " +
                    "INNER JOIN rooms r ON r.address_id = a.id " +
                    "INNER JOIN screenings s ON s.room_id = r.id AND date(s.start_time)=:date " +
                    "INNER JOIN movies m ON s.movie_id = m.id AND m.id=:movie_id", nativeQuery = true)
    List<Address> findAllByMovieByDate(@Param("movie_id") Long movieId, @Param("date") Date date);

    @Query("SELECT a FROM Address a " +
            "INNER JOIN a.rooms r " +
            "INNER JOIN r.screenings s WHERE s.id=:screening_id")
    Address findOneByScreening(@Param("screening_id") Long screeningId);

    @Query("SELECT a FROM Address a " +
            "INNER JOIN a.rooms r " +
            "INNER JOIN r.screenings s " +
            "INNER JOIN s.tickets t WHERE t.id=:ticket_id")
    Address findOneByTicket(@Param("ticket_id") Long ticketId);

    @Query("SELECT DISTINCT a FROM Address a WHERE a.id IN :id_list")
    List<Address> findAllByIdIn(@Param("id_list") List<Integer> addressIdList);
}
