package org.kiev.cinema.pendings.confirmations;

import org.kiev.cinema.CinemaConstants;

import java.util.List;
import java.util.TimerTask;
import java.util.UUID;

public class PendingConfirmation extends TimerTask {

    public static final Integer PENDING_LIMIT = CinemaConstants.WAITING_FOR_VISITOR_CONFIRMATION_TIME_LIMIT;

    private PendingConfirmationsContainer pendingConfirmationsContainer;

    private String email;
    private Long timeMillis = System.currentTimeMillis();
    private Long screeningId;
    private List<Long> ticketIds;
    private Integer ticketQuantity;
    private String tokens = UUID.randomUUID().toString().substring(0,5);

    public PendingConfirmation(String email, Long screeningId, List<Long> ticketIds, Integer ticketQuantity) {
        this.email = email;
        this.screeningId = screeningId;
        this.ticketIds = ticketIds;
        this.ticketQuantity = ticketQuantity;
    }

    public void setPendingConfirmationsContainer(PendingConfirmationsContainerImpl pendingConfirmationsImpl) {
        this.pendingConfirmationsContainer = pendingConfirmationsImpl;
    }

    public String getEmail() {
        return email;
    }

    public boolean isConfirmed(String code) {
        if(timeMillis + PENDING_LIMIT >= System.currentTimeMillis()
                && code.equals(tokens)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isExpired() {
        return timeMillis + PENDING_LIMIT < System.currentTimeMillis();
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public List<Long> getTicketIds() {
        return ticketIds;
    }

    public Integer getTicketQuantity() {
        return ticketQuantity;
    }

    public String getTokens() {
        return tokens;
    }

    @Override
    public void run() {
        pendingConfirmationsContainer.delete(this.email);
        System.out.println("expired confirmation pending is deleted...");
    }
}
