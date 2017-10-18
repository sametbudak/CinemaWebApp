package org.kiev.cinema.service;

import org.kiev.cinema.entity.Room;
import org.kiev.cinema.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public void add(Room room) {
        roomRepository.save(room);
    }

    @Override
    public List<Room> listAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> listByAddress(Integer addressId) {
        return roomRepository.findAllByAddress(addressId);
    }

    @Override
    public Room findById(Integer roomId) {
        return roomRepository.findOne(roomId);
    }

    @Override
    public Room findByScreening(Long screeningId) {
        return roomRepository.findOneByScreening(screeningId);
    }
}
