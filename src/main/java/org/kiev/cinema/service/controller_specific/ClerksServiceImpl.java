package org.kiev.cinema.service.controller_specific;

import org.kiev.cinema.CinemaConstants;
import org.kiev.cinema.TicketImageCreator;
import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.dto.clerks.*;
import org.kiev.cinema.dto.visitors.ScreeningTicketsDto;
import org.kiev.cinema.entity.*;
import org.kiev.cinema.entity.user.Clerk;
import org.kiev.cinema.enums.BookingStatus;
import org.kiev.cinema.enums.TicketStatus;
import org.kiev.cinema.pendings.bookings.PendingBookingsContainer;
import org.kiev.cinema.repository.*;
import org.kiev.cinema.repository.DtoRepository;
import org.kiev.cinema.repository.user.ClerkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClerksServiceImpl implements ClerksService {
    @Autowired
    DtoRepository dtoRepository;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ClerkRepository clerkRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private PendingBookingsContainer pendingBookingsContainer;
    @Autowired
    private TicketImageCreator ticketImageCreator;
    @Autowired
    private VisitorsService visitorsService;

    @Override
    public List<TicketDto> findBookedTicketsByEmail(String email) {
        List<TicketDto> ticketDtoList = new ArrayList<>();
        List<Booking> bookingFetchTicketList = bookingRepository.findAllBookingsFetchTicketByEmailByStatus(email, BookingStatus.ACTIVE);
        Movie movie = null;
        Address address = null;

        for(Booking booking : bookingFetchTicketList) {
            Ticket ticket = booking.getTicket();
            if(ticket == null || ticket.isSold()) return null; // RETURN NULL
            if(movie == null) {movie = movieRepository.findOneByTicket(ticket.getId());}
            if(address == null) {address = addressRepository.findOneByTicket(ticket.getId());}

            TicketDto ticketDto = getTicketDto(booking, ticket, movie, address);
            ticketDtoList.add(ticketDto);
        }
        return ticketDtoList;
    }

    @Override
    @Transactional
    public List<TicketDto> sellBookedTickets(String email, String clerkLogin, List<Long> ticketIdList) {
        List<TicketDto> ticketDtoList = new ArrayList<>();
        List<Booking> bookingFetchTicketList = bookingRepository.findAllBookingsFetchTicketByEmailByStatus(email, BookingStatus.ACTIVE);
        Movie movie = null;
        Address address = null;
        Clerk clerk = clerkRepository.findByLogin(clerkLogin);
        for(Booking booking : bookingFetchTicketList) {
            Ticket ticket = booking.getTicket();

            if(ticket == null || ticket.isSold()) throw new RuntimeException("There is not ticket, or ticket is already sold"); // EXCEPTION

            Long ticketId = ticket.getId();
            if(movie == null) {movie = movieRepository.findOneByTicket(ticketId);}
            if(address == null) {address = addressRepository.findOneByTicket(ticketId);}

            if(ticketIdList.contains(ticketId)) {
                booking.setBookingStatus(BookingStatus.REDEEMED);
                ticket.setTicketStatus(TicketStatus.SOLD);
                ticket.setSoldAtTime(new Timestamp(System.currentTimeMillis()));
                ticket.setClerk(clerk);
                ticketRepository.save(ticket);
                bookingRepository.save(booking);
                System.out.println("booking and ticket saved " + booking.getId() + ", " + ticket.getId());
                TicketDto ticketDto = getTicketDto(booking, ticket, movie, address);
                ticketDtoList.add(ticketDto);
            } else {
                booking.setBookingStatus(BookingStatus.CANCELED);
                ticket.setTicketStatus(TicketStatus.AVAILABLE);
                ticket.setBookedAtTime(null);
                ticketRepository.save(ticket);
                bookingRepository.save(booking);
                System.out.println("booking and ticket cancelled " + booking.getId() + ", " + ticket.getId());
            }
        }
        pendingBookingsContainer.deleteBooking(email);
        return ticketDtoList;
    }

    @Override
    public List<TicketDto> sellTickets(String clerkLogin, List<Long> ticketIdList) {
        List<TicketDto> ticketDtoList = new ArrayList<>();
        Movie movie = null;
        Address address = null;
        Clerk clerk = clerkRepository.findByLogin(clerkLogin);
        for(Long ticketId : ticketIdList) {
            Ticket ticket = ticketRepository.findOne(ticketId);
            if(ticket.isSold() || ticket.isBooked()) throw new RuntimeException("error"); // EXCEPTION
            if(movie == null) {movie = movieRepository.findOneByTicket(ticketId);}
            if(address == null) {address = addressRepository.findOneByTicket(ticketId);}
            ticket.setTicketStatus(TicketStatus.SOLD);
            ticket.setSoldAtTime(new Timestamp(System.currentTimeMillis()));
            ticket.setClerk(clerk);
            ticketRepository.save(ticket);
            System.out.println("ticket saved " + ticket.getId());
            TicketDto ticketDto = getTicketDto(null, ticket, movie, address);
            ticketDtoList.add(ticketDto);
        }
        return ticketDtoList;
    }

    @Override
    public ScreeningTicketsDto getScreeningTicketsDto(Long screeningId) {
        return visitorsService.getScreeningTicketsDto(screeningId);
    }

    @Override
    public List<Path> printTickets(List<TicketDto> ticketDtoList) {
        List<Path> pathList = new ArrayList<>();
        for(TicketDto ticketDto : ticketDtoList) {
            try {
                pathList.add(ticketImageCreator.generate(ticketDto.createFormattedStrings(),
                        ticketDto.getSoldAtTime(), ticketDto.getTicketId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pathList;
    }

    private TicketDto getTicketDto(Booking booking, Ticket ticket, Movie movie, Address address) {
        Object[] objArr = dtoRepository.findScreeningsTimeAndSeatsRowColumnByTicketId(ticket.getId());
        Timestamp screeningTimestamp = (Timestamp) objArr[0];
        Integer rowNumber = (Integer) objArr[1];
        Integer columnNumber = (Integer) objArr[2];
        TicketDto bookedTicketDto = new TicketDto.Builder()
                .setBookingInfo(booking)
                .setSoldAtTime(ticket.getSoldAtTime())
                .setTicketId(ticket.getId())
                .setPrice(ticket.getPrice())
                .setAddress(address)
                .setMovieTitle(movie.getTitle())
                .setDuration(movie.getMinutes())
                .setMovieScreeningDateTime(screeningTimestamp)
                .setPlace(rowNumber, columnNumber)
                .build();
        return bookedTicketDto ;
    }

    @Override
    public List<ScreeningsDto> findScreeningsDto(SearchCriteriaLists searchCriteriaLists) {
        List<Movie> movies;
        List<Address> addresses;
        Date today = DateUtils.getTodayDateWithZeroTime();
        Date lastDate = DateUtils.getLastDateWithZeroTime(today, CinemaConstants.DAYS_INTERVAL);

        List<Long> moviesIds = searchCriteriaLists.getMovieIdList();
        if(  !moviesIds.isEmpty() && !moviesIds.contains(-1L) ) {
            movies = movieRepository.findAllByIdIn(moviesIds);
          } else {
            movies = movieRepository.findAllFromDateTillDate(today, lastDate);
        }
        List<Integer> addressesIds = searchCriteriaLists.getAddressIdList();
        if( !addressesIds.isEmpty() && !addressesIds.contains(-1) ) {
            addresses = addressRepository.findAllByIdIn(addressesIds);
        } else {
            addresses = addressRepository.findAll();
        }
        List<Date> dates = searchCriteriaLists.getDateList();
        ShowtimeRange showtimeRanges = searchCriteriaLists.getShowtimeRange();
        ScreeningsDtoList screeningsDtoList;
        if( dates.isEmpty() ) {
            if (showtimeRanges == null) {
                screeningsDtoList = findBy(movies, addresses, today, lastDate);
            } else {
                screeningsDtoList = findBy(movies, addresses, today, lastDate, showtimeRanges);
            }
        } else {
            if (showtimeRanges == null) {
                screeningsDtoList = findBy(movies, addresses, dates);
            } else {
                screeningsDtoList = findBy(movies, addresses, dates, showtimeRanges);
            }
        }
        return screeningsDtoList.getScreeningsDtoList();
    }

    @Override
    public Double countTotalPrice(List<Long> ticketIdList) {
        return ticketRepository.countPrice(ticketIdList);
    }

    private ScreeningsDtoList findBy(List<Movie> movieList, List<Address> addressList, List<Date> dateList, ShowtimeRange showtimeRange) {
        ScreeningsDtoList screeningsDtoList = new ScreeningsDtoList(movieList, addressList, dateList);
        for(Movie movie : movieList) {
            for (Address address : addressList) {
                List<Screening> screenings = screeningRepository.findAllByMovieByAddressByDateListStartingFromTimeTillTime
                        (movie.getId(), address.getId(), dateList, showtimeRange.startTime(), showtimeRange.endTime());
                screeningsDtoList.addScreeningsIfDatesExist(screenings, movie.getId(), address.getId());
            }
        }
        return screeningsDtoList;
    }

    private ScreeningsDtoList findBy(List<Movie> movieList, List<Address> addressList, Date fromDate, Date tillDate, ShowtimeRange showtimeRange) {
        ScreeningsDtoList screeningsDtoList = new ScreeningsDtoList(movieList);
        for(Movie movie : movieList) {
            for (Address address : addressList) {
                List<Screening> screenings = screeningRepository.findAllByMovieByAddressFromDateTillDateStartingFromTimeTillTime
                        (movie.getId(), address.getId(), fromDate, tillDate, showtimeRange.startTime(), showtimeRange.endTime());
                screeningsDtoList.addAddressShowtime(screenings, movie.getId(), address);
            }
        }
        return screeningsDtoList;
    }

    private ScreeningsDtoList findBy(List<Movie> movieList, List<Address> addressList, List<Date> dateList) {
        ScreeningsDtoList screeningsDtoList = new ScreeningsDtoList(movieList, addressList, dateList);
        for(Movie movie : movieList) {
            for (Address address : addressList) {
                List<Screening> screenings = screeningRepository.findAllByMovieByAddressByDateList(movie.getId(), address.getId(), dateList);
                screeningsDtoList.addScreeningsIfDatesExist(screenings, movie.getId(), address.getId());
            }
        }
        return screeningsDtoList;
    }

    private ScreeningsDtoList findBy(List<Movie> movieList, List<Address> addressList, Date fromDate, Date tillDate) {
        ScreeningsDtoList screeningsDtoList = new ScreeningsDtoList(movieList);
        for(Movie movie : movieList) {
            for (Address address : addressList) {
                List<Screening> screenings = screeningRepository.findAllByMovieByAddressFromDateTillDate
                        (movie.getId(), address.getId(), fromDate, tillDate);
                screeningsDtoList.addAddressShowtime(screenings, movie.getId(), address);
            }
        }
        return screeningsDtoList;
    }

}
