package org.kiev.cinema.repository;

import org.kiev.cinema.dto.admins.ListableMovieDto;
import org.kiev.cinema.dto.admins.ListableScreeningDto;
import org.kiev.cinema.dto.admins.ScreeningDto;
import org.kiev.cinema.dto.visitors.AllMoviesShowtimesDto;
import org.kiev.cinema.dto.visitors.OneMovieShowtimesDto;
import org.kiev.cinema.enums.TicketStatus;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface DtoRepository {
    List<AllMoviesShowtimesDto> findShowtimesDtoByDate(Date date);
    OneMovieShowtimesDto findOneMovieShowtimesDto(Long movieId);
    List<ListableMovieDto> findAllListableMovieDtoByTicketStatusNotEquals(Pageable pageable, TicketStatus ticketStatus);
    Object[] findScreeningsTimeAndSeatsRowColumnByTicketId(Long ticketId);
    List<Object[]> findAddressIdAndScreeningIdAndTimeByMovieFromDateTillDate(Long movieId, Date dateFrom, Date dateTill);
    List<AllMoviesShowtimesDto> findAllMoviesShowtimesDtoByDate(Date date);
    List<AllMoviesShowtimesDto> findMoviesShowtimesDtoByMovieByDate(Long movieId, Date date);
    AllMoviesShowtimesDto findMoviesShowtimesDtoByMovieByAddressByDate(Long movieId, Integer addressId, Date date);
    List<AllMoviesShowtimesDto> findAllMoviesShowtimesDtoByAddressByDate(Integer addressId, Date date);
    Object[] findScreeningInfoByScreening(Long screeningId);
    List<ScreeningDto> findAllScreeningDtoFromDateTillDate(Date fromDate, Date tillDate);
    List<ListableScreeningDto> findAllListableScreeningDtoByTicketStatusNotEquals(Pageable pageable, TicketStatus ticketStatus);
}
