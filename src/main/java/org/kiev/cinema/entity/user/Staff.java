package org.kiev.cinema.entity.user;

import org.kiev.cinema.enums.UserRole;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Staff {

    @Column(nullable = false, unique = true)
    protected String login;

    @Column(nullable = false)
    protected String password;

    @Column(nullable = false, name="user_role")
    @Enumerated(EnumType.STRING)
    protected UserRole userRole;

    public Staff(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return this.userRole;
    }
}
