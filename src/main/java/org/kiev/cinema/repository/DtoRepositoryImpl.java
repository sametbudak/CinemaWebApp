package org.kiev.cinema.repository;

import org.kiev.cinema.dto.admins.ListableMovieDto;
import org.kiev.cinema.dto.admins.ListableScreeningDto;
import org.kiev.cinema.dto.admins.ScreeningDto;
import org.kiev.cinema.dto.visitors.AllMoviesShowtimesDto;
import org.kiev.cinema.dto.visitors.OneMovieShowtimesDto;
import org.kiev.cinema.enums.TicketStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DtoRepositoryImpl implements DtoRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<AllMoviesShowtimesDto> findShowtimesDtoByDate(Date date) {
        return em.createQuery(
                "SELECT DISTINCT new org.kiev.cinema.dto.visitors.AllMoviesShowtimesDto(m.id, m.title, m.minutes, date(s.startTime)) " +
                        "FROM Screening s " +
                        "INNER JOIN s.movie m " +
                        "WHERE date(s.startTime)=:date", AllMoviesShowtimesDto.class)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    public OneMovieShowtimesDto findOneMovieShowtimesDto(Long movieId) {
        return em.createQuery(
                "SELECT new org.kiev.cinema.dto.visitors.OneMovieShowtimesDto(m.id, m.title, m.minutes) " +
                        "FROM Movie m " +
                        "WHERE m.id=:movie_id", OneMovieShowtimesDto.class)
                .setParameter("movie_id", movieId)
                .getSingleResult();
    }

    @Override
    public List<ListableMovieDto> findAllListableMovieDtoByTicketStatusNotEquals(Pageable pageable, TicketStatus ticketStatus) {
        String order = getOrderForId(pageable);
        return em.createQuery(
                "SELECT new org.kiev.cinema.dto.admins.ListableMovieDto(m.id, m.title, m.movieStatus, m.screenings.size, count(tickets)) " +
                        "FROM Movie m " +
                        "LEFT JOIN m.screenings as screenings " +
                        "LEFT JOIN screenings.tickets as tickets ON tickets.ticketStatus<>:ticket_status " +
                        "GROUP BY m.id " +
                        "ORDER BY m.id " +order, ListableMovieDto.class)
                .setParameter("ticket_status", ticketStatus)
                .setFirstResult(pageable.getPageNumber()*pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public Object[] findScreeningsTimeAndSeatsRowColumnByTicketId(Long ticketId) {
        return em.createQuery(
                "SELECT sc.startTime, seat.rowNumber, seat.columnNumber " +
                        "FROM Ticket t " +
                        "INNER JOIN t.screening sc " +
                        "INNER JOIN t.seat seat WHERE t.id=:ticket_id", Object[].class)
                .setParameter("ticket_id", ticketId)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<Object[]> findAddressIdAndScreeningIdAndTimeByMovieFromDateTillDate
            (Long movieId, Date dateFrom, Date dateTill) {
        return em.createQuery(
                "SELECT DISTINCT r.address.id, s.id, s.startTime FROM Screening s " +
                        "INNER JOIN s.movie m " +
                        "INNER JOIN s.room r " +
                        "WHERE m.id=:movie_id AND date(m.endDate)>=:date_from AND date(m.startDate)<=:date_till " +
                        "ORDER BY s.startTime ASC", Object[].class)
                .setParameter("movie_id", movieId)
                .setParameter("date_from", dateFrom)
                .setParameter("date_till", dateTill)
                .getResultList();
    }

    @Override
    public List<AllMoviesShowtimesDto> findAllMoviesShowtimesDtoByDate(Date date) {
        return em.createQuery(
                "SELECT DISTINCT new org.kiev.cinema.dto.visitors.AllMoviesShowtimesDto(m.id, m.title, m.minutes, date(s.startTime)) " +
                        "FROM Screening s " +
                        "INNER JOIN s.movie m " +
                        "WHERE date(s.startTime)=:date", AllMoviesShowtimesDto.class)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    public List<AllMoviesShowtimesDto> findMoviesShowtimesDtoByMovieByDate(Long movieId, Date date) {
        return em.createQuery(
                "SELECT DISTINCT new org.kiev.cinema.dto.visitors.AllMoviesShowtimesDto(m.id, m.title, m.minutes, date(s.startTime)) " +
                        "FROM Screening s " +
                        "INNER JOIN s.movie m " +
                        "WHERE m.id=:movie_id AND date(s.startTime)=:date", AllMoviesShowtimesDto.class)
                .setParameter("movie_id", movieId)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    public AllMoviesShowtimesDto findMoviesShowtimesDtoByMovieByAddressByDate(Long movieId, Integer addressId, Date date) {
        return em.createQuery(
                "SELECT DISTINCT new org.kiev.cinema.dto.visitors.AllMoviesShowtimesDto(m.id, m.title, m.minutes, date(s.startTime)) " +
                        "FROM Screening s " +
                        "INNER JOIN s.movie m " +
                        "INNER JOIN s.room r " +
                        "WHERE m.id=:movie_id AND r.address.id=:address_id AND date(s.startTime)=:date", AllMoviesShowtimesDto.class)
                .setParameter("movie_id", movieId)
                .setParameter("address_id", addressId)
                .setParameter("date", date)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<AllMoviesShowtimesDto> findAllMoviesShowtimesDtoByAddressByDate(Integer addressId, Date date) {
        return em.createQuery(
                "SELECT DISTINCT new org.kiev.cinema.dto.visitors.AllMoviesShowtimesDto(m.id, m.title, m.minutes, date(s.startTime)) " +
                        "FROM Screening s " +
                        "INNER JOIN s.movie m " +
                        "INNER JOIN s.room r " +
                        "WHERE r.address.id=:address_id AND date(s.startTime)=:date", AllMoviesShowtimesDto.class)
                .setParameter("address_id", addressId)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    public Object[] findScreeningInfoByScreening(Long screeningId) {
        return em.createQuery(
                "SELECT DISTINCT m.title, m.minutes, r.maxRow, r.maxColumn, s.startTime, s.id " +
                        "FROM Screening s " +
                        "INNER JOIN s.movie m " +
                        "INNER JOIN s.room r " +
                        "WHERE s.id=:screening_id", Object[].class)
                .setParameter("screening_id", screeningId)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<ScreeningDto> findAllScreeningDtoFromDateTillDate(Date fromDate, Date tillDate) {
        return em.createQuery(
                "SELECT DISTINCT new org.kiev.cinema.dto.admins.ScreeningDto(s.id, m.title, m.minutes, s.startTime, r.id) " +
                        "FROM Screening s " +
                        "INNER JOIN s.movie m " +
                        "INNER JOIN s.room r " +
                        "WHERE date(s.startTime) BETWEEN date(:from_date) AND date(:till_date)", ScreeningDto.class)
                .setParameter("from_date", fromDate)
                .setParameter("till_date", tillDate)
                .getResultList();
    }

    @Override
    public List<ListableScreeningDto> findAllListableScreeningDtoByTicketStatusNotEquals(Pageable pageable, TicketStatus ticketStatus) {
        String order = getOrderForId(pageable);
        return em.createQuery(
                "SELECT new org.kiev.cinema.dto.admins.ListableScreeningDto(s.id, s.startTime, room.address, room.title, movie.title, count(tickets)) " +
                        "FROM Screening s " +
                        "LEFT JOIN s.movie as movie " +
                        "LEFT JOIN s.room as room " +
                        "LEFT JOIN s.tickets as tickets ON NOT tickets.ticketStatus=:ticket_status " +
                        "GROUP BY s.id " +
                        "ORDER BY s.id "+order, ListableScreeningDto.class)
                .setParameter("ticket_status", ticketStatus)
                .setFirstResult(pageable.getPageNumber()*pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    private String getOrderForId(Pageable pageable) {
        Sort.Order order;
        String orderStr = "ASC";
        if( (order=pageable.getSort().getOrderFor("id")) !=null ) {
            if (order.isDescending()) {
                orderStr="DESC";
            }
        }
        return orderStr;
    }
   }
