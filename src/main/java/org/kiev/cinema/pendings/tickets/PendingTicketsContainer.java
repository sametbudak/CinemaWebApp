package org.kiev.cinema.pendings.tickets;

import java.util.List;

public interface PendingTicketsContainer {
    boolean addAll(List<Long> ticketIdList);
    boolean containsAny(List<Long> ticketIdList);
    boolean deleteAll(List<Long> ticketIdList);
}
