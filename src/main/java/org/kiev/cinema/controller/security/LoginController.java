package org.kiev.cinema.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/admin/login")
    public String adminLoginPage(){
        return "staff/admin/admin_login";
    }

    @RequestMapping("/clerk/login")
    public String clerkLoginPage(){
        return "staff/clerk/clerk_login";
    }
}
