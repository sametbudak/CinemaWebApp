package org.kiev.cinema.service;

import org.kiev.cinema.entity.Room;

import java.util.List;

public interface RoomService {
    void add(Room room);
    List<Room> listAll();
    List<Room> listByAddress(Integer addressId);
    Room findById(Integer roomId);
    Room findByScreening(Long screeningId);
}
