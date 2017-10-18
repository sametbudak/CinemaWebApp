package org.kiev.cinema.controller;

import org.kiev.cinema.CinemaConstants;
import org.kiev.cinema.DataContainer;
import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.dto.visitors.*;
import org.kiev.cinema.entity.Movie;
import org.kiev.cinema.pendings.bookings.PendingBookingsContainer;
import org.kiev.cinema.pendings.tickets.PendingTicketsContainerImpl;
import org.kiev.cinema.service.MovieService;
import org.kiev.cinema.MailSender;
import org.kiev.cinema.service.controller_specific.VisitorsService;
import org.kiev.cinema.pendings.confirmations.PendingConfirmation;
import org.kiev.cinema.pendings.confirmations.PendingConfirmationsContainerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.*;

@Controller
public class VisitorsController {

    @Autowired
    private DataContainer dataContainer;
    @Autowired
    private PendingConfirmationsContainerImpl pendingConfirmationsImpl;
    @Autowired
    private PendingBookingsContainer pendingBookingsContainer;
    @Autowired
    private PendingTicketsContainerImpl pendingTicketsContainerImpl;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private MovieService movieService;
    @Autowired
    private VisitorsService visitorsService;

    @ModelAttribute("email")
    private String addEmailToRequestScope() {
        return dataContainer.getEmail();
    }
    @ModelAttribute("phone")
    private String addPhoneToRequestScope() {
        return dataContainer.getPhone();
    }
    @ModelAttribute("skype")
    private String addSkypeToRequestScope() {
        return dataContainer.getSkype();
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Model model, HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        model.addAttribute("email", dataContainer.getEmail());
        model.addAttribute("phone", dataContainer.getPhone());
        model.addAttribute("skype", dataContainer.getSkype());
        return "visitor/error";
    }

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("movies", dataContainer.getMoviesFromToday());
        model.addAttribute("addresses", dataContainer.getAddresses());
        return "visitor/home";
    }

    @RequestMapping("/movie/{id}")
    public String movieData(Model model, @PathVariable("id") Long movieId) {
        Movie movie = movieService.findById(movieId);
        List<String> genresStrList = movie.genresListToStringList();
        model.addAttribute("movie", movie);
        model.addAttribute("genresStrList", genresStrList);
        return "visitor/movie_data";
    }
    @RequestMapping("/showtimes_movie/{id}")
    public String showtimesMovie(Model model, @PathVariable("id") Long movieId) {
        OneMovieShowtimesDto oneMovieShowtimesDto = visitorsService.getForMovieFromToday(movieId, CinemaConstants.DAYS_INTERVAL);
        if(oneMovieShowtimesDto.getScreeningIdKeyValueMapperList() == null
                || oneMovieShowtimesDto.getScreeningIdKeyValueMapperList().isEmpty()) {
            model.addAttribute("nonAvailable", true);
        }
        model.addAttribute("showtimes", oneMovieShowtimesDto);
        return "visitor/one_movie_screenings";
    }

    @RequestMapping("/showtimes")
    public String showtimes(Model model) {
        setFiltersData(model);
        Date today = DateUtils.getTodayDateWithZeroTime();
        model.addAttribute("selected_date", today);
        model.addAttribute("selected_address", null);
        model.addAttribute("selected_movie", null);
        List<AllMoviesShowtimesDto> allMoviesShowtimesDtoList = visitorsService.getShowtimesDtoOnDate(today);
        model.addAttribute("showtimesList", allMoviesShowtimesDtoList);
        return "visitor/all_movies_screenings";
    }

    @RequestMapping(value="/filter_showtimes", method = RequestMethod.POST)
    public String filterShowtimes(Model model,
                                  @RequestParam("address_id") Integer addressId,
                                  @RequestParam("movie_id") Long movieId,
                                  @RequestParam("date") Date date
    ) {
        setFiltersData(model);
        model.addAttribute("selected_date", date);
        model.addAttribute("selected_address", dataContainer.getAddressById(addressId));
        model.addAttribute("selected_movie", dataContainer.getMovieById(movieId));

        List<AllMoviesShowtimesDto> allMoviesShowtimesDtoList = null;
        if(movieId > -1 && addressId > -1) {
            allMoviesShowtimesDtoList = visitorsService.getShowtimesDtoByMovieByAddressOnDate(movieId, addressId, date);
        } else if(addressId > -1) {
            allMoviesShowtimesDtoList = visitorsService.getShowtimesDtoByAddressOnDate(addressId, date);
        } else if(movieId > -1) {
            allMoviesShowtimesDtoList = visitorsService.getShowtimesDtoByMovieOnDate(movieId, date);
        } else {
            allMoviesShowtimesDtoList = visitorsService.getShowtimesDtoOnDate(date);
        }

        model.addAttribute("showtimesList", allMoviesShowtimesDtoList);
        return "visitor/all_movies_screenings";
    }


    @RequestMapping(value= "/list_tickets")
    public String listTickets(Model model, @RequestParam("screening_id") Long screeningId) {
        ScreeningTicketsDto screeningTicketsDto = visitorsService.getScreeningTicketsDto(screeningId);
        model.addAttribute("screeningTicketsInfo", screeningTicketsDto);
        return "visitor/tickets";
    }

    @RequestMapping(value = "/book_tickets")
    public String confirmBookingCode(Model model,
                                     @RequestParam("email")String email,
                                     @RequestParam("screening_id")Long screeningId,
                                     @RequestParam("ticket")Long[] ticketIds) {
        List<Long> ticketIdList = Arrays.asList(ticketIds);
        String errorMsg = "";
        if(ticketIds.length == 0 || ticketIds.length>3) {
            errorMsg = "You have selected %d ticket(s). Only 1-3 tickets are allowed to be selected for booking!";
        } else if(pendingConfirmationsImpl.isEmailUsed(email)) {
            errorMsg = "Your email has been recently used for booking. Try again in a few minutes!";
        } else if(pendingBookingsContainer.isEmailUsed(email)) {
            errorMsg = "Please redeem already booked ticket(s) at the ticket office before book the new ones.";
        } else{
            if(pendingTicketsContainerImpl.addAll(ticketIdList)==true) {
                PendingConfirmation pendingConfirmation = new PendingConfirmation(email, screeningId, ticketIdList, ticketIdList.size());
                pendingConfirmationsImpl.add(pendingConfirmation);
                mailSender.sendVerificationCode(email, pendingConfirmation.getTokens());
                model.addAttribute("email", email);
                return "visitor/booking_code_confirmation"; // SUCCESS
            } else if(pendingTicketsContainerImpl.containsAny(ticketIdList)) {
                errorMsg = "Tickets are non-available!";
            } else {
                errorMsg = "Try later on!";
            }
        }
        model.addAttribute("ticketsErrorMsg", errorMsg);
        return "forward:/list_tickets"; // ERROR
    }

    @RequestMapping("/confirm_booking")
    public String bookingConfirmed(Model model,
                                   @RequestParam("email")String email,
                                   @RequestParam("code")String code) {
        PendingConfirmation confirmation = pendingConfirmationsImpl.getConfirmed(email, code);
        if (confirmation == null) {
            model.addAttribute("codeConfirmationErrorMsg", "Error confirming booking...");
            return "visitor/booking_confirmed";
        }

        Long screeningId = confirmation.getScreeningId();
        List<Long> ticketIdList = confirmation.getTicketIds();
        List<BookedTicketDto> bookedTicketDtoList = visitorsService.addConfirmedBookings(ticketIdList, screeningId, email, code);
        if(bookedTicketDtoList==null) {
            model.addAttribute("codeConfirmationErrorMsg", "Error confirming booking...");
            return "visitor/booking_confirmed";
        }
        Double amount = 0d;
        for(BookedTicketDto bookedTicketDto : bookedTicketDtoList) {
            amount += bookedTicketDto.getPrice();
        }
        model.addAttribute("bookedTicketInfoList", bookedTicketDtoList);
        model.addAttribute("amount", amount);
        return "visitor/booking_confirmed";
    }

    private void setFiltersData(Model model) {
        Map<Integer, String> addressesMap = dataContainer.getAddressesIdStringMap();
        List<Date> dateList = DateUtils.getDatesFromToday(CinemaConstants.DAYS_INTERVAL);
        Map<Long, String> moviesMap = dataContainer.getMoviesIdTitleMap();
        model.addAttribute("addressesMap", addressesMap);
        model.addAttribute("moviesMap", moviesMap);
        model.addAttribute("dateList", dateList);
    }

}
