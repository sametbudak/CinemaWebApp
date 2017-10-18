package org.kiev.cinema.entity.user;

import org.kiev.cinema.entity.Ticket;
import org.kiev.cinema.enums.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clerks")
public class Clerk extends Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "clerk", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public Clerk() {
        super(UserRole.CLERK);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        ticket.setClerk(this);
        this.tickets.add(ticket);
    }
}
