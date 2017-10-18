package org.kiev.cinema.controller;

import org.kiev.cinema.CinemaConstants;
import org.kiev.cinema.DataContainer;
import org.kiev.cinema.date.DateUtils;
import org.kiev.cinema.dto.clerks.ScreeningsDto;
import org.kiev.cinema.dto.clerks.SearchCriteriaLists;
import org.kiev.cinema.dto.clerks.ShowtimeRange;
import org.kiev.cinema.dto.clerks.TicketDto;
import org.kiev.cinema.dto.visitors.ScreeningTicketsDto;
import org.kiev.cinema.pendings.tickets.PendingTicketsContainer;
import org.kiev.cinema.service.controller_specific.ClerksService;
import org.kiev.cinema.service.controller_specific.VisitorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clerk")
public class ClerksController {

    @Autowired
    private DataContainer dataContainer;
    @Autowired
    private ClerksService clerksService;
    @Autowired
    private PendingTicketsContainer pendingTicketsContainer;

    @ModelAttribute("login")
    private String addLoginToRequestScope() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        return login;
    }

    @ExceptionHandler(Exception.class)
    public String handleError(Model model, HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        return "staff/clerk/clerk_error";
    }

    @RequestMapping("/403")
    public String accessDeniedPage(){
        return "403";
    }

    @RequestMapping("/search_page")
    public String home(Model model) {
        Map<Integer, String> addressesMap = dataContainer.getAddressesIdStringMap();
        List<Date> dateList = DateUtils.getDatesFromToday(CinemaConstants.DAYS_INTERVAL);
        Map<Long, String> moviesMap = dataContainer.getMoviesIdTitleMap();
        model.addAttribute("addressMap", addressesMap);
        model.addAttribute("movieMap", moviesMap);
        model.addAttribute("dateList", dateList);
        model.addAttribute("clerk_showtimeRangeMap", ShowtimeRange.SHOWTIME_RANGE_MAP);
        return "staff/clerk/clerk_start";
    }

    @RequestMapping("/get_screenings")
    public String screenings(Model model,
                             @RequestParam("showtime")Integer showtimeKey,
                             @RequestParam("movie")Long[] movieIds,
                             @RequestParam("address") Integer[] addressIds,
                             @RequestParam("date") String[] dateStrings) {
        SearchCriteriaLists searchCriteriaLists = new SearchCriteriaLists();
        searchCriteriaLists.setAddressIds(Arrays.asList(addressIds));
        searchCriteriaLists.setMovieIds(Arrays.asList(movieIds));
        searchCriteriaLists.setShowtimeRange(ShowtimeRange.SHOWTIME_RANGE_MAP.get(showtimeKey));
        searchCriteriaLists.setDates(convertDatesFromStringYyyyMMdd(dateStrings));
        List<ScreeningsDto> screeningsDtoList = clerksService.findScreeningsDto(searchCriteriaLists);
        model.addAttribute("screeningsDtoList", screeningsDtoList);
        return "staff/clerk/clerk_screenings";
    }

    private List<Date> convertDatesFromStringYyyyMMdd(String[] dateStrings) {
        List<Date> dateList = new ArrayList<>();
        for(String str : dateStrings) {
            System.out.println("dateStrings " + str);
            if(str.equals("-1")) break;
            try {
                dateList.add(DateUtils.parseDateFromyyyyMMdd(str));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dateList;
    }

    @RequestMapping("/list_tickets")
    public String listTickets(Model model,
                              @RequestParam("screening_id") Long screeningId) {
        ScreeningTicketsDto screeningTicketsDto = clerksService.getScreeningTicketsDto(screeningId);
        model.addAttribute("screeningTicketsInfo", screeningTicketsDto);
        return "staff/clerk/clerk_tickets_selection";
    }

    @RequestMapping("/select_tickets")
    public String selectTickets(Model model,
                                @RequestParam("ticket") Long[] ticketIds,
                                @RequestParam("screening_id") Long screeningId) {
        List<Long> ticketIdList = Arrays.asList(ticketIds);
        if(pendingTicketsContainer.addAll(ticketIdList) == true) {
            Double amount = clerksService.countTotalPrice(ticketIdList);
            model.addAttribute("amount", amount);
            model.addAttribute("ticketIdList", ticketIdList);
            model.addAttribute("screening_id", screeningId);
            return "staff/clerk/clerk_tickets_confirmation";
        } else {
            model.addAttribute("message", "Tickets are not available!");
            return "redirect:/clerk/list_tickets";
        }
    }

    @RequestMapping("/booking_page")
    public String bookingPage(Model model) {
        model.addAttribute("isFound", null);
        return "staff/clerk/clerk_booking";
    }

    @RequestMapping("/find_booked_tickets")
    public String findBookedTickets(Model model, @RequestParam("email") String email) {
        List<TicketDto> ticketDtoList = clerksService.findBookedTicketsByEmail(email);
        if(ticketDtoList == null || ticketDtoList.isEmpty()) {
            model.addAttribute("isFound", false);
            model.addAttribute("email", email);
        } else {
            model.addAttribute("isFound", true);
            Double amount = 0.;
            for(TicketDto ticketDto : ticketDtoList) {
                amount += ticketDto.getPrice();
            }
            model.addAttribute("amount", amount);
            model.addAttribute("ticketDtoList", ticketDtoList);
        }
        return "staff/clerk/clerk_booking";
    }

    @RequestMapping(value = {"/pay_booked_tickets"})
    public String payForBookedTickets(Model model,
                                      @RequestParam("email") String email,
                                      @RequestParam("ticket")Long[] ticketIds) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String clerkLogin = user.getUsername();
        List<Long> ticketIdList = Arrays.asList(ticketIds);
        List<TicketDto> ticketDtoList = clerksService.sellBookedTickets(email, clerkLogin, ticketIdList);
        printTickets(model, ticketDtoList);
        return "staff/clerk/clerk_sold_ticket";
    }

    @RequestMapping(value = {"/pay_tickets"})
    public String payForBookedTickets(Model model,
                                      @RequestParam("ticket")Long[] ticketIds) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String clerkLogin = user.getUsername();
        List<Long> ticketIdList = Arrays.asList(ticketIds);
        List<TicketDto> ticketDtoList = clerksService.sellTickets(clerkLogin, ticketIdList);
        printTickets(model, ticketDtoList);
        pendingTicketsContainer.deleteAll(ticketIdList);
        return "staff/clerk/clerk_sold_ticket";
    }

    @RequestMapping(value = {"/cancel_tickets"})
    public String payForBookedTickets(Model model,
                                      @RequestParam("ticket")Long[] ticketIds,
                                      @RequestParam("screening_id") Long screeningId) {
        List<Long> ticketIdList = Arrays.asList(ticketIds);
        pendingTicketsContainer.deleteAll(ticketIdList);
        model.addAttribute("message", "Tickets are cancelled!");
        return "forward:/clerk/list_tickets";
    }

    private void printTickets(Model model, List<TicketDto> ticketDtoList) {
        if(ticketDtoList == null || ticketDtoList.isEmpty()) {
            model.addAttribute("error", "Error");
        } else {
            List<Path> ticketPathList = clerksService.printTickets(ticketDtoList);
            List<String> fileNameList = new ArrayList<>();
            for(Path path : ticketPathList) {
                fileNameList.add(path.getFileName().toString());
                System.out.println(path.getFileName().toString());
            }
            model.addAttribute("fileNameList", fileNameList);
        }
    }

}
