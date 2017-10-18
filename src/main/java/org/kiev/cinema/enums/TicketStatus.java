package org.kiev.cinema.enums;

public enum TicketStatus {
    AVAILABLE("btn-success"),
    BOOKED("btn-warning"),
    SOLD("btn-danger"),
    PENDING("btn-default");

    private String bootstrapClass;

    TicketStatus(String bootstrapClass) {
        this.bootstrapClass = bootstrapClass;
    }

    public String getBootstrapClass() {
        return bootstrapClass;
    }
}
