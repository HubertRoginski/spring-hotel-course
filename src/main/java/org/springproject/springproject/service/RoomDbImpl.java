package org.springproject.springproject.service;

import org.springframework.stereotype.Service;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.model.User;
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

    @Override
    public Room updateRoomById(Long id, Room room) {
        if (roomRepository.existsById(id)){
            room.setId(id);
            return roomRepository.save(room);
        }
        return null;
    }

    @Override
    public Room getByRoomNumber(Integer roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

}
