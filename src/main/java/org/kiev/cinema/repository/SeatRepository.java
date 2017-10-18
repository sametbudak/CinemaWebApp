package org.kiev.cinema.repository;

import org.kiev.cinema.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query(value =
            "SELECT s.id, s.room_id, s.row_number, s.column_number " +
                    "FROM seats s " +
                    "INNER JOIN rooms r ON r.id=s.room_id " +
                    "INNER JOIN screenings sc ON sc.room_id=r.id WHERE sc.id=:screening_id",
            nativeQuery = true)
    List<Seat> findAllByScreening(@Param("screening_id")Long screeningId);

}
