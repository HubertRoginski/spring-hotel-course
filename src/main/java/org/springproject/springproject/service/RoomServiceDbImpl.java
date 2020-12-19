package org.springproject.springproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springproject.springproject.exception.WrongPageException;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.model.User;
import org.springproject.springproject.repository.RoomRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class RoomServiceDbImpl implements RoomService{

    private final RoomRepository roomRepository;

    public RoomServiceDbImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }



    @Override
    public Page<Room> getAllRooms(Integer page, Integer size) {
        if (Objects.isNull(page)) {
            page = 1;
        }
        if (Objects.isNull(size)) {
            size = 5;
        }
        if (page < 0) {
            throw new WrongPageException("Page number can't be less than 1");
        }
        Sort sort = Sort.by("roomNumber").ascending();
        Pageable pageable = PageRequest.of(page , size, sort);
        return roomRepository.findAll(pageable);
    }

    @Override
    public List<Room> getAllNotOccupiedRooms() {
        return roomRepository.findAllWhereIsNotOccupied();
    }

    @Override
    public Room getRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.orElse(null);
    }

    @Override
    public Room addRoom(Room room) {
        if (Objects.isNull(roomRepository.findByRoomNumber(room.getRoomNumber()))){
            return roomRepository.save(room);
        }
        log.info("ADD ROOM FAIL");
        return null;
    }

    @Override
    public List<Room> addBatchOfRooms(List<Room> rooms) {
        return roomRepository.saveAll(rooms);
    }

    @Override
    public Room updateRoomById(Long id, Room room) {
        if (roomRepository.existsById(id) && Objects.isNull(roomRepository.findByRoomNumber(room.getRoomNumber()))){
            room.setId(id);
            return roomRepository.save(room);
        }
        return null;
    }

    @Override
    public Boolean deleteRoomById(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Room getByRoomNumber(Integer roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

}
