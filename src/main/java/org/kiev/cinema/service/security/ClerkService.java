package org.kiev.cinema.service.security;

import org.kiev.cinema.entity.user.Clerk;

public interface ClerkService {
    Clerk getClerkByLogin(String clerkLogin);
    void addClerk(Clerk clerk);
}
