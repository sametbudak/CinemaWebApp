package org.kiev.cinema.repository.user;

import org.kiev.cinema.entity.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("SELECT a From Admin a WHERE a.login = :admin_login")
    Admin findByLogin(@Param("admin_login") String adminLogin);
}
