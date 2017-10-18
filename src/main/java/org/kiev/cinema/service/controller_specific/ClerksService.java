package org.kiev.cinema.service.controller_specific;

import org.kiev.cinema.dto.clerks.ScreeningsDto;
import org.kiev.cinema.dto.clerks.SearchCriteriaLists;
import org.kiev.cinema.dto.clerks.TicketDto;
import org.kiev.cinema.dto.visitors.ScreeningTicketsDto;

import java.nio.file.Path;
import java.util.List;

public interface ClerksService {

    List<TicketDto> findBookedTicketsByEmail(String email);
    List<TicketDto> sellBookedTickets(String email, String clerkLogin, List<Long> ticketIdList);
    List<TicketDto> sellTickets(String clerkLogin, List<Long> ticketIdList);
    ScreeningTicketsDto getScreeningTicketsDto(Long screeningId);
    List<Path> printTickets(List<TicketDto> ticketDtoList);
    List<ScreeningsDto> findScreeningsDto(SearchCriteriaLists searchCriteriaLists);
    Double countTotalPrice(List<Long> ticketIdList);
}
