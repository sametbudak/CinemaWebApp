package org.kiev.cinema.pendings.tickets;

import org.kiev.cinema.CinemaConstants;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class PendingTicketsContainerImpl implements PendingTicketsContainer {

    public final int PENDING_LIMIT = CinemaConstants.SELECTED_TICKET_TIME_LIMIT;
    private Set<Long> set = new CopyOnWriteArraySet<>();
    private final int MAX_SIZE = CinemaConstants.PENDING_SELECTED_TICKETS_QUANTITY_LIMIT;
    private Timer timer = new Timer();

    @Override
    public boolean addAll(List<Long> ticketIdList) {
        boolean res = false;
        synchronized (this) {  // SYNCHRONIZED
            if(set.size() + ticketIdList.size() > MAX_SIZE ) {
                return res;
            }
            for(Long ticketId : ticketIdList) {
                if (set.contains(ticketId)) {
                    return res;
                }
            }
            res = set.addAll(ticketIdList);
        }
        if(res == true) {
            for(Long ticketId : ticketIdList) {
                PendingTicket pendingTicket = new PendingTicket(ticketId);
                timer.schedule(pendingTicket, PENDING_LIMIT);
            }
        }
        return res;
    }

    @Override
    public boolean deleteAll(List<Long> ticketIdList) {
        return set.removeAll(ticketIdList);
    }

    @Override
    public boolean containsAny(List<Long> ticketIdList) {
        for(Long ticketId : ticketIdList) {
            if (set.contains(ticketId)) {
                return true;
            }
        }
        return false;
    }

    private boolean deleteExpired(Long ticketId) {
        return set.remove(ticketId);
    }

    private class PendingTicket extends TimerTask {
        private Long ticketId;

        private PendingTicket(Long ticketIdList) {
            this.ticketId = ticketId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PendingTicket that = (PendingTicket) o;

            return ticketId != null ? ticketId.equals(that.ticketId) : that.ticketId == null;
        }

        @Override
        public int hashCode() {
            return ticketId != null ? ticketId.hashCode() : 0;
        }

        @Override
        public void run() {
            PendingTicketsContainerImpl.this.deleteExpired(ticketId);
        }
    }

}
