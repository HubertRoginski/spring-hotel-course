package org.springproject.springproject.service;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceDbImplTest {

    final Room ROOM_TO_ADD = Room.builder().roomNumber(10).roomClass(2).maxPeopleCapacity(3).oneDayCost(300).build();
    final Room ROOM_RETURNED = Room.builder().id(1L).roomNumber(10).roomClass(2).maxPeopleCapacity(3).oneDayCost(300).reservations(new ArrayList<>()).build();

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceDbImpl roomServiceDbImpl;


    @Test
    void addRoomWithUniqueRoomNumber() {
        //given
        when(roomRepository.findByRoomNumber(ROOM_TO_ADD.getRoomNumber())).thenReturn(null);
        when(roomRepository.save(ROOM_TO_ADD)).thenReturn(ROOM_RETURNED);
        //when
        Room addRoom = roomServiceDbImpl.addRoom(ROOM_TO_ADD);
        //then
        assertThat(addRoom).isEqualTo(ROOM_RETURNED);
    }

    @Test
    void addRoomWithNotUniqueRoomNumber() {
        //given
        when(roomRepository.findByRoomNumber(ROOM_TO_ADD.getRoomNumber())).thenReturn(ROOM_RETURNED);
        //when
        Room addRoom = roomServiceDbImpl.addRoom(ROOM_TO_ADD);
        //then
        assertThat(addRoom).isNull();
    }

    @Test
    void shouldFindAllRoomsList(){
        //given
        when(roomRepository.findAll()).thenReturn(prepareRoomsList());
        //when
        List<Room> rooms = roomServiceDbImpl.getAllRooms();
        //then
        assertThat(rooms).hasSize(3).allMatch(room -> prepareRoomsList().contains(room));
    }

    @Test
    void shouldFindAllRoomsPage(){
        //given
        Sort sort = Sort.by("roomNumber").ascending();
        Pageable pageable = PageRequest.of(1, 3, sort);
        when(roomRepository.findAll(pageable)).thenReturn(prepareRoomsPage());
        //when
        Page<Room> rooms = roomServiceDbImpl.getAllRooms(1,3);
        //then
        assertThat(rooms).hasSize(3);
    }

    private List<Room> prepareRoomsList(){
        List<Room> rooms = new ArrayList<>();
        rooms.add(Room.builder().id(2L).roomNumber(10).roomClass(2).maxPeopleCapacity(3).oneDayCost(500).build());
        rooms.add(Room.builder().id(3L).roomNumber(18).roomClass(3).maxPeopleCapacity(2).oneDayCost(100).build());
        rooms.add(Room.builder().id(4L).roomNumber(13).roomClass(1).maxPeopleCapacity(1).oneDayCost(400).build());
        return rooms;
    }
    private Page<Room> prepareRoomsPage(){
        List<Room> rooms = new ArrayList<>();
        rooms.add(Room.builder().id(2L).roomNumber(10).roomClass(2).maxPeopleCapacity(3).oneDayCost(500).build());
        rooms.add(Room.builder().id(3L).roomNumber(18).roomClass(3).maxPeopleCapacity(2).oneDayCost(100).build());
        rooms.add(Room.builder().id(4L).roomNumber(13).roomClass(1).maxPeopleCapacity(1).oneDayCost(400).build());
        return new PageImpl<>(rooms);
    }
}
