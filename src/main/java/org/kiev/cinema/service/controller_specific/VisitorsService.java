package org.kiev.cinema.service.controller_specific;

import org.kiev.cinema.dto.visitors.AllMoviesShowtimesDto;
import org.kiev.cinema.dto.visitors.BookedTicketDto;
import org.kiev.cinema.dto.visitors.ScreeningTicketsDto;
import org.kiev.cinema.dto.visitors.OneMovieShowtimesDto;

import java.sql.Date;
import java.util.List;

public interface VisitorsService {
    List<AllMoviesShowtimesDto> getShowtimesDtoOnDate(Date date);
    List<AllMoviesShowtimesDto> getShowtimesDtoByAddressOnDate(Integer addressId, Date date);
    List<AllMoviesShowtimesDto> getShowtimesDtoByMovieOnDate(Long movieId, Date date);
    List<AllMoviesShowtimesDto> getShowtimesDtoByMovieByAddressOnDate(Long movieId, Integer addressId, Date date);
    ScreeningTicketsDto getScreeningTicketsDto(Long screeningId);
    OneMovieShowtimesDto getForMovieFromToday(Long movieId, Integer daysInterval);
    List<BookedTicketDto> addConfirmedBookingsToDB(List<Long> ticketIdList,  Long screeningId, String email, String code);
    List<BookedTicketDto> addConfirmedBookings(List<Long> ticketIdList,  Long screeningId, String email, String code);
}
