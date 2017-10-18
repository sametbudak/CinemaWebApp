package org.kiev.cinema.repository;

import org.kiev.cinema.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT r FROM Room r WHERE r.address.id=:address_id")
    List<Room> findAllByAddress(@Param("address_id") Integer addressId);

    @Query("SELECT r FROM Room r INNER JOIN r.screenings s WHERE s.id=:screening_id")
    Room findOneByScreening(@Param("screening_id") Long screeningId);
}
