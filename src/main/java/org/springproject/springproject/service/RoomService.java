package org.springproject.springproject.service;

import org.springframework.data.domain.Page;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.model.User;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    Page<Room> getAllRooms(Integer page, Integer size);

    List<Room> getAllRooms();

    Room getRoomById(Long id);

    Room addRoom(Room room);

    List<Room> addBatchOfRooms(List<Room> rooms);

    Room updateRoomById(Long id, Room room);

    Boolean deleteRoomById(Long id);

    Room getByRoomNumber(Integer roomNumber);

}
