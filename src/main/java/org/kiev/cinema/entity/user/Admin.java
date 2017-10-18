package org.kiev.cinema.entity.user;

import org.kiev.cinema.enums.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Admin() {
        super(UserRole.ADMIN);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
