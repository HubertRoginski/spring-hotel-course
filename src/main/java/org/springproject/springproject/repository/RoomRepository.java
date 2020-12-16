package org.springproject.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springproject.springproject.model.Room;

public interface RoomRepository extends JpaRepository<Room,Long> {

    Room findByRoomNumber(Integer roomNumber);
}
