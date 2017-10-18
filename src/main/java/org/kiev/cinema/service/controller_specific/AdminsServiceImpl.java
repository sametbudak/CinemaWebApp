package org.kiev.cinema.service.controller_specific;

import org.kiev.cinema.date.TimeAsHHmm;
import org.kiev.cinema.date.WeekOrdinal;
import org.kiev.cinema.dto.admins.*;
import org.kiev.cinema.entity.*;
import org.kiev.cinema.enums.MovieStatus;
import org.kiev.cinema.enums.TicketStatus;
import org.kiev.cinema.repository.*;
import org.kiev.cinema.repository.DtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class AdminsServiceImpl implements AdminsService {

    @Autowired
    private DtoRepository dtoRepository;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public SchedulingDto getSchedulingDto(WeekOrdinal week) {
        List<Date> dateList = week.getDateListForComingWeek();
        List<TimeRange> timeRangeList = null;
        try {
            timeRangeList = createTimeRangeList(
                    new TimeAsHHmm(9), new TimeAsHHmm(23, 59), new TimeAsHHmm(2, 50), new TimeAsHHmm(0, 10));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        List<AddressAndRoomList> addressAndRoomList = getAddressAndRoomList();

        List<ScreeningDto> screeningDtoList = dtoRepository.findAllScreeningDtoFromDateTillDate(
                        week.getMondayDateForComingWeek(), week.getSundayDateForComingWeek());

        SchedulingDto schedulingDto = new SchedulingDto(dateList, addressAndRoomList, timeRangeList);
        schedulingDto.setScreeningDtoMap(screeningDtoList);
        return schedulingDto;
    }

    private List<TimeRange> createTimeRangeList(TimeAsHHmm start, TimeAsHHmm end, TimeAsHHmm duration, TimeAsHHmm offset) throws CloneNotSupportedException {
        List<TimeRange> timeRangeList = new ArrayList<>();
        while(start.less(end) && start.sumIsWithin24Hours(duration)) {
            TimeRange timeRange = new TimeRange(start, duration);
            timeRangeList.add(timeRange);

            start = start.add(duration);
            if(start.sumIsWithin24Hours(offset)) {
                start = start.add(offset);
            } else {
                break;
            }
            System.out.println(timeRange);
        }
        return timeRangeList;
    }

    private List<AddressAndRoomList> getAddressAndRoomList() {
        List<AddressAndRoomList> addressAndRoomLists = new ArrayList<>();
        List<Address> addressList = addressRepository.findAll();
        for(Address address : addressList) {
            List<Room> roomList = roomRepository.findAllByAddress(address.getId());
            AddressAndRoomList obj = new AddressAndRoomList(address, roomList);
            addressAndRoomLists.add(obj);
        }
        return addressAndRoomLists;
    }

    @Override
    public List<ActiveMovieDto> getActiveMovieDtoList() {
        List<ActiveMovieDto> activeMovieDtoList = new ArrayList<>();
        List<Movie> activeMovieList = movieRepository.findAllByStatus(MovieStatus.ACTIVE);
        for(Movie movie : activeMovieList) {
            activeMovieDtoList.add(new ActiveMovieDto(movie));
        }
        return activeMovieDtoList;
    }

    @Override
    @Transactional
    public int addScreenings(List<CreateScreeningDto> createScreeningDtoList) {
        int count = 0;
        for(CreateScreeningDto createScreeningDto : createScreeningDtoList) {
            Screening screening = new Screening();
            screening.setStartTime((createScreeningDto.getTimestamp()));
            Movie movie = movieRepository.getOne(createScreeningDto.getMovieId());
            setMovieStartEndDate(movie, screening);
            screening.setMovie(movie);
            Room room = roomRepository.getOne(createScreeningDto.getRoomId());
            screening.setRoom(room);
            Timestamp fromTime = screening.getStartTime();
            Timestamp tillTime = new Timestamp(fromTime.getTime() + movie.getMinutes()*60*1000);
            screening.setEndTime(tillTime);
            Long screeningCount = screeningRepository.countAllScreeningsByRoomFromTimeTillTime(createScreeningDto.getRoomId(), fromTime, tillTime);
            if(screeningCount != null && screeningCount > 0L) {
                System.out.println("skip");
                continue;
            }
            screeningRepository.save(screening);
            System.out.println("screening was saved...");
            List<Ticket> ticketList = createTickets(screening, createScreeningDto.getPrice());
            ticketRepository.save(ticketList);
            System.out.println("Tickets were added...");
            count++;
        }
        return count;
    }

    private void setMovieStartEndDate(Movie movie, Screening screening) {
        Date startDate =  movie.getStartDate();
        Date endDate = movie.getEndDate();
        Date screeningDate = new Date(screening.getStartTime().getTime());
        if(startDate == null && endDate == null) {
            startDate = endDate = screeningDate;
        } else if (endDate.before(screeningDate)) {
            endDate = screeningDate;
            if(startDate == null) {
                throw new RuntimeException("Movie startDate has not been set");
            }
        }
        movie.setStartDate(startDate);
        movie.setEndDate(endDate);
    }

    private List<Ticket> createTickets(Screening screening, Double price) {
        List<Ticket> ticketList = new ArrayList<>();
        List<Seat> seats = seatRepository.findAllByScreening(screening.getId());
        for(Seat seat : seats) {
            Ticket ticket = new Ticket();
            ticket.setSeat(seat);
            ticket.setPrice(price);
            ticket.setScreening(screening);
            ticketList.add(ticket);
        }
        return ticketList;
    }

    @Override
    public EditableMovieDto getEditableMovieDto(Long movieId) {
        EditableMovieDto editableMovieDto;
        Movie movie = movieRepository.findOne(movieId);
        //List<Screening> screenings = screeningRepository.findAllByMovie(movieId);
        Long screeningsCount = screeningRepository.countAllByMovie(movieId);
         if(screeningsCount == null || screeningsCount == 0L) {
            editableMovieDto = new EditableMovieDto(movie, false);
        } else {
            editableMovieDto = new EditableMovieDto(movie, true);
        }
        return editableMovieDto;
    }


    @Override
    @Transactional
    public EditableMovieDto updateMovie(Movie editedMovie) {
        EditableMovieDto editableMovieDto = getEditableMovieDto(editedMovie.getId());
        List<Genre> dbGenres = getDbGenres(editedMovie.getGenres());
        Movie updatedMovie = editableMovieDto.getUpdatedMovie(editedMovie, dbGenres);
        movieRepository.save(updatedMovie);
        return editableMovieDto;
    }

    @Override
    public boolean deleteMovie(Long movieId) {
        //List<Screening> screeningList = screeningRepository.findAllByMovie(movieId);
        Long screeningsCount = screeningRepository.countAllByMovie(movieId);
        if(screeningsCount == null || screeningsCount == 0L) {
            movieRepository.delete(movieId);
            Movie movie = movieRepository.findOne(movieId);
            if(movie == null) {
                return true;
            }
        }
        return false;
    }

    private List<Genre> getDbGenres (List<Genre> genreList) {
        List<Genre> dbGenreList = new LinkedList<>();
        for(Genre genre : genreList) {
            Genre entity = genreRepository.findOneByGenreEnum(genre.getGenreEnum());
            dbGenreList.add(entity);
        }
        return dbGenreList;
    }

    @Override
    public List<ListableMovieDto> getListableMovieDtoList(Pageable pageable) {
        List<ListableMovieDto> listableMovieDtoPage = dtoRepository
                .findAllListableMovieDtoByTicketStatusNotEquals(pageable, TicketStatus.AVAILABLE);
        return listableMovieDtoPage;
    }

    @Override
    public boolean deleteScreening(Long screeningId) {
        //List<Ticket> nonAvailableTicketList = ticketRepository.findAllByScreeningByTicketStatusNotEquals(screeningId, TicketStatus.AVAILABLE);
        Long nonAvailableTicketCount = ticketRepository.countAllByScreeningByTicketStatusNotEquals(screeningId, TicketStatus.AVAILABLE);
        if(nonAvailableTicketCount == null || nonAvailableTicketCount == 0L) {
            screeningRepository.delete(screeningId);
            Screening screening = screeningRepository.findOne(screeningId);
            if(screening == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ListableScreeningDto> getListableScreeningDtoList(Pageable pageable) {
        List<ListableScreeningDto> listableScreeningDtoPage = dtoRepository
                .findAllListableScreeningDtoByTicketStatusNotEquals(pageable, TicketStatus.AVAILABLE);
        return listableScreeningDtoPage;
    }

}
