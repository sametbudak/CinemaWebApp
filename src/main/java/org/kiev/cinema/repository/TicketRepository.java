package org.kiev.cinema.repository;

import org.kiev.cinema.entity.Ticket;
import org.kiev.cinema.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t INNER JOIN t.screening s WHERE s.movie=:movie_id") // ???
    List<Ticket> findAllByMovie(@Param("movie_id") Long movieId);

    @Query("SELECT t FROM Ticket t WHERE t.screening.id=:screening_id")
    List<Ticket> findAllByScreening(@Param("screening_id") Long screeningId);

    @Query("SELECT sum(t.price) FROM Ticket t WHERE t.id IN :ticket_id_list")
    Double countPrice(@Param("ticket_id_list") List<Long> ticketId);

    @Query("SELECT t FROM Ticket t WHERE t.screening.id=:screening_id AND NOT t.ticketStatus=:ticket_status")
    List<Ticket> findAllByScreeningByTicketStatusNotEquals(@Param("screening_id") Long screeningId, @Param("ticket_status") TicketStatus status);

    @Query("SELECT count(t) FROM Ticket t WHERE t.screening.id=:screening_id AND NOT t.ticketStatus=:ticket_status")
    long countAllByScreeningByTicketStatusNotEquals(@Param("screening_id") Long screeningId, @Param("ticket_status") TicketStatus status);

}
