package org.kiev.cinema.service.controller_specific;

import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.dto.visitors.AllMoviesShowtimesDto;
import org.kiev.cinema.dto.visitors.BookedTicketDto;
import org.kiev.cinema.dto.visitors.ScreeningTicketsDto;
import org.kiev.cinema.dto.visitors.OneMovieShowtimesDto;
import org.kiev.cinema.entity.*;
import org.kiev.cinema.pendings.bookings.PendingBookingsContainer;
import org.kiev.cinema.pendings.tickets.PendingTicketsContainer;
import org.kiev.cinema.repository.*;
import org.kiev.cinema.pendings.bookings.PendingBooking;
import org.kiev.cinema.repository.DtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VisitorsServiceImpl implements VisitorsService {

    @Autowired
    private DtoRepository dtoRepository;

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private PendingBookingsContainer pendingBookingsContainer;
    @Autowired
    private PendingTicketsContainer pendingTicketsContainer;

    @Override
    public List<AllMoviesShowtimesDto> getShowtimesDtoOnDate(Date date) {
        List<AllMoviesShowtimesDto> allMoviesShowtimesDtoList = dtoRepository.findShowtimesDtoByDate(date);
        for(AllMoviesShowtimesDto allMoviesShowtimesDto : allMoviesShowtimesDtoList) {
            Long movieId = allMoviesShowtimesDto.getMovieId();
            List<Address> addressList = addressRepository.findAllByMovieByDate(movieId, date);
            for(Address address : addressList) {
                Integer addressId = address.getId();
                String addressHtmlId = allMoviesShowtimesDto.new AddressIdValueMapper(address).getAddressHtmlId();
                List<Screening> screeningList = screeningRepository.findAllByMovieByAddressByDate(movieId, addressId, date);
                for(Screening screening : screeningList) {
                    allMoviesShowtimesDto.new ScreeningIdKeyValueMapper(addressHtmlId, screening.getId(), screening.getStartTime());
                }
            }
        }
        return allMoviesShowtimesDtoList;
    }

    @Override
    public List<AllMoviesShowtimesDto> getShowtimesDtoByAddressOnDate(Integer addressId, Date date) {
        List<AllMoviesShowtimesDto> allMoviesShowtimesDtoList = dtoRepository.findAllMoviesShowtimesDtoByAddressByDate(addressId, date);
        for(AllMoviesShowtimesDto allMoviesShowtimesDto : allMoviesShowtimesDtoList) {
            Long movieId = allMoviesShowtimesDto.getMovieId();
            Address address = addressRepository.findOne(addressId);
            String addressHtmlId = allMoviesShowtimesDto.new AddressIdValueMapper(address).getAddressHtmlId();
            List<Screening> screeningList = screeningRepository.findAllByMovieByAddressByDate(movieId, addressId, date);
            for(Screening screening : screeningList) {
                allMoviesShowtimesDto.new ScreeningIdKeyValueMapper(addressHtmlId, screening.getId(), screening.getStartTime());
            }
        }
        return allMoviesShowtimesDtoList;
    }

    @Override
    public List<AllMoviesShowtimesDto> getShowtimesDtoByMovieOnDate(Long movieId, Date date) {
        List<AllMoviesShowtimesDto> allMoviesShowtimesDtoList = dtoRepository.findMoviesShowtimesDtoByMovieByDate(movieId, date);
        AllMoviesShowtimesDto allMoviesShowtimesDto;
        if(allMoviesShowtimesDtoList == null || allMoviesShowtimesDtoList.isEmpty()) {
            System.out.println("allMoviesShowtimesDto is null");
            return new ArrayList<>();
        } else {
            allMoviesShowtimesDto = allMoviesShowtimesDtoList.get(0);
        }
        List<Address> addressList = addressRepository.findAllByMovieByDate(movieId, date);
        for(Address address : addressList) {
            Integer addressId = address.getId();
            String addressHtmlId = allMoviesShowtimesDto.new AddressIdValueMapper(address).getAddressHtmlId();
            List<Screening> screeningList = screeningRepository.findAllByMovieByAddressByDate(movieId, addressId, date);
            for(Screening screening : screeningList) {
                allMoviesShowtimesDto.new ScreeningIdKeyValueMapper(addressHtmlId, screening.getId(), screening.getStartTime());
            }
        }
        return Arrays.asList(allMoviesShowtimesDto);
    }

    @Override
    public List<AllMoviesShowtimesDto> getShowtimesDtoByMovieByAddressOnDate(Long movieId, Integer addressId, Date date) {
        List<AllMoviesShowtimesDto> allMoviesShowtimesDtoList = dtoRepository.findMoviesShowtimesDtoByMovieByDate(movieId, date);
        AllMoviesShowtimesDto allMoviesShowtimesDto;
        if(allMoviesShowtimesDtoList == null || allMoviesShowtimesDtoList.isEmpty()) {
            System.out.println("allMoviesShowtimesDto is null");
            return new ArrayList<>();
        } else {
            allMoviesShowtimesDto = allMoviesShowtimesDtoList.get(0);
        }
        Address address = addressRepository.findOne(addressId);
        String addressHtmlId = allMoviesShowtimesDto.new AddressIdValueMapper(address).getAddressHtmlId();
        List<Screening> screeningList = screeningRepository.findAllByMovieByAddressByDate(movieId, addressId, date);
        for(Screening screening : screeningList) {
            allMoviesShowtimesDto.new ScreeningIdKeyValueMapper(addressHtmlId, screening.getId(), screening.getStartTime());
        }
        return Arrays.asList(allMoviesShowtimesDto);
    }

    @Override
    public ScreeningTicketsDto getScreeningTicketsDto(Long screeningId) {
        Address address = addressRepository.findOneByScreening(screeningId);
        List<Ticket> ticketList = ticketRepository.findAllByScreening(screeningId);
        Object[] objArray = dtoRepository.findScreeningInfoByScreening(screeningId);
        String movieTitle = (String)objArray[0];
        Integer durationInMinutes = (Integer)objArray[1];
        Integer maxRow = (Integer)objArray[2];
        Integer maxColumn = (Integer)objArray[3];
        Timestamp timestamp = (Timestamp) objArray[4];
        ScreeningTicketsDto screeningTicketsDto = new ScreeningTicketsDto.Builder()
                .setScreeningId(screeningId)
                .setTicketsList(ticketList)
                .setAddress(address)
                .setMovieTitle(movieTitle)
                .setHoursAndMinutes(durationInMinutes)
                .setDateAndTime(timestamp)
                .setMaxRow(maxRow)
                .setMaxColumn(maxColumn)
                .build();
        return screeningTicketsDto;
    }

    @Override
    public OneMovieShowtimesDto getForMovieFromToday(Long movieId, Integer daysInterval) {
        Date today = DateUtils.getTodayDateWithZeroTime();
        Date lastDate = DateUtils.getLastDateWithZeroTime(today, daysInterval);
        OneMovieShowtimesDto oneMovieShowtimesDto = dtoRepository.findOneMovieShowtimesDto(movieId);
        List<Address> addressList = addressRepository.findAllByMovie(movieId);
        oneMovieShowtimesDto.setAddressesInfo(addressList);
        List<Object[]> addressIdScreeningIdTimeList = dtoRepository
                .findAddressIdAndScreeningIdAndTimeByMovieFromDateTillDate(movieId, today, lastDate);
        oneMovieShowtimesDto.setShowtimesInfo(addressIdScreeningIdTimeList);
        return oneMovieShowtimesDto;
    }

    @Override
    @Transactional
    public List<BookedTicketDto> addConfirmedBookingsToDB(List<Long> ticketIdList,  Long screeningId, String email, String code) {
        List<BookedTicketDto> bookedTicketDtoList = new ArrayList<>();
        List<Ticket> ticketList = ticketRepository.findAll(ticketIdList);
        for(Ticket ticket : ticketList) {
            if(ticket.isBooked()) {
                return null;
            }
        }
        Address address = addressRepository.findOneByScreening(screeningId);
        Movie movie = movieRepository.findOneByScreening(screeningId);
        for(Ticket ticket: ticketList) {
            Booking booking = new Booking(ticket, email, code);
            bookingRepository.save(booking);
            Object[] objArr = dtoRepository.findScreeningsTimeAndSeatsRowColumnByTicketId(ticket.getId());
            Timestamp screeningTimestamp = (Timestamp) objArr[0];
            Integer rowNumber = (Integer) objArr[1];
            Integer columnNumber = (Integer) objArr[2];
            BookedTicketDto bookedTicketDto = new BookedTicketDto.Builder()
                    .setBookingInfo(booking)
                    .setPrice(ticket.getPrice())
                    .setAddress(address)
                    .setMovieTitle(movie.getTitle())
                    .setMovieDuration(movie.getMinutes())
                    .setScreeningDateTime(screeningTimestamp)
                    .setPlace(rowNumber, columnNumber)
                    .build();
            bookedTicketDtoList.add(bookedTicketDto);
        }
        return bookedTicketDtoList;
    }

    @Override
    public List<BookedTicketDto> addConfirmedBookings(List<Long> ticketIdList, Long screeningId, String email, String code) {
        List<BookedTicketDto> bookedTicketDtoList  = addConfirmedBookingsToDB(ticketIdList,  screeningId,email, code);
        List<Long> bookingIdList = new ArrayList<>();
        for(BookedTicketDto bookedTicketDto : bookedTicketDtoList) {
            bookingIdList.add(Long.decode(bookedTicketDto.getBookingId()));
        }
        PendingBooking pendingBooking = new PendingBooking(email, bookingIdList);
        pendingBookingsContainer.addBooking(pendingBooking);
        pendingTicketsContainer.deleteAll(ticketIdList);
        return bookedTicketDtoList;
    }
}
