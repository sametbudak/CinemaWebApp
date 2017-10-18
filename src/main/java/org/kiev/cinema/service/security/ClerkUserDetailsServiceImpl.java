package org.kiev.cinema.service.security;

import org.kiev.cinema.entity.user.Clerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ClerkUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClerkService clerkService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Clerk clerk = clerkService.getClerkByLogin(login);
        if(clerk == null) {
            throw new UsernameNotFoundException("clerk with login " + login + " not found");
        }
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(clerk.getRole().name()));
        User user = new User(clerk.getLogin(), clerk.getPassword(), roles);
        return user;
    }
}
