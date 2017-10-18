package org.kiev.cinema.pendings.confirmations;

import org.kiev.cinema.CinemaConstants;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class PendingConfirmationsContainerImpl implements PendingConfirmationsContainer {

    private ConcurrentMap<String, PendingConfirmation> map = new ConcurrentHashMap<>();
    private final int MAX_SIZE = CinemaConstants.PENDING_CONFIRMATIONS_QUANTITY_LIMIT;
    private Timer timer = new Timer();

    @Override
    public boolean add(PendingConfirmation pendingConfirmation) {
        String email = pendingConfirmation.getEmail();
        if(map.containsKey(email) || map.size()>=MAX_SIZE) {
            return false;
        } else {
            map.put(pendingConfirmation.getEmail(), pendingConfirmation);
            pendingConfirmation.setPendingConfirmationsContainer(this);
            timer.schedule(pendingConfirmation, PendingConfirmation.PENDING_LIMIT);
            return true;
        }
    }

    @Override
    public PendingConfirmation getConfirmed(String email, String code) {
        PendingConfirmation pendingConfirmation = map.remove(email);
        if(pendingConfirmation != null
                && pendingConfirmation.isConfirmed(code) == true) {
            pendingConfirmation.cancel();
            timer.purge();
            return pendingConfirmation;
        } else {
            return null;
        }
    }

    @Override
    public boolean isEmailUsed(String email) {
        return (map.get(email) == null) ? false : true;
    }

    @Override
    public PendingConfirmation delete(String email) {
        return map.remove(email);
    }

}
