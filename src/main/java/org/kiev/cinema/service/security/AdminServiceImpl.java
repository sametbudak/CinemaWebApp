package org.kiev.cinema.service.security;

import org.apache.logging.log4j.Logger;
import org.kiev.cinema.entity.user.Admin;
import org.kiev.cinema.repository.user.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private Logger logger;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin getAdminByLogin(String adminLogin) {
        return adminRepository.findByLogin(adminLogin);
    }

    @Override
    @Transactional
    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }
}
