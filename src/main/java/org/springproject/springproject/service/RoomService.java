package org.springproject.springproject.service;

import org.springproject.springproject.model.Room;

import java.util.List;

public interface RoomService {

    List<Room> getAllRooms();

    Room addRoom(Room room);
}
