package org.springproject.springproject.service;

import org.springframework.stereotype.Service;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.repository.RoomRepository;

import java.util.List;

@Service
public class RoomDbImpl implements RoomService{

    private final RoomRepository roomRepository;

    public RoomDbImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }
}
