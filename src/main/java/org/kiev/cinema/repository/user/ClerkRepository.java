package org.kiev.cinema.repository.user;

import org.kiev.cinema.entity.user.Clerk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClerkRepository extends JpaRepository<Clerk, Long> {
    @Query("SELECT c FROM Clerk c WHERE c.login = :clerk_login")
    Clerk findByLogin(@Param("clerk_login") String clerkLogin);
}
