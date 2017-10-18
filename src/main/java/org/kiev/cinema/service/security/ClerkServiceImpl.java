package org.kiev.cinema.service.security;

import org.apache.logging.log4j.Logger;
import org.kiev.cinema.entity.user.Clerk;
import org.kiev.cinema.repository.user.ClerkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClerkServiceImpl implements ClerkService{

    @Autowired
    private Logger logger;

    @Autowired
    private ClerkRepository clerkRepository;

    @Override
    public Clerk getClerkByLogin(String clerkLogin) {
        return clerkRepository.findByLogin(clerkLogin);
    }

    @Override
    @Transactional
    public void addClerk(Clerk clerk) {
        clerkRepository.save(clerk);
    }
}
