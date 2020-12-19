package org.springproject.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.model.User;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {

    Room findByRoomNumber(Integer roomNumber);

    @Query(value = "SELECT r FROM Room r where r.isOccupied=false ",nativeQuery = false)
    List<Room> findAllWhereIsNotOccupied();
}
