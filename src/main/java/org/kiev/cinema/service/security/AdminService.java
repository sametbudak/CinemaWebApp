package org.kiev.cinema.service.security;

import org.kiev.cinema.entity.user.Admin;

public interface AdminService {
    Admin getAdminByLogin(String adminLogin);
    void addAdmin(Admin admin);
}
