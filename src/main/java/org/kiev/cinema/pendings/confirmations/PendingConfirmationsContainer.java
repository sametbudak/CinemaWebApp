package org.kiev.cinema.pendings.confirmations;

public interface PendingConfirmationsContainer {
    boolean add(PendingConfirmation pendingConfirmation);

    PendingConfirmation getConfirmed(String email, String code);

    boolean isEmailUsed(String email);

    PendingConfirmation delete(String email);
}
