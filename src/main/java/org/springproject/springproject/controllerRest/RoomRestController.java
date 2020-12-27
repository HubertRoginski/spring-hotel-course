package org.springproject.springproject.controllerRest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.service.RoomService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/hotel/rooms")
public class RoomRestController {

    private final RoomService roomService;

    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(roomService.getAllRooms(page, size).getContent());
    }

    @PostMapping("/add/batch")
    public ResponseEntity<?> addBatchOfRooms(@Valid @RequestBody List<Room> rooms) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.addBatchOfRooms(rooms));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRoom(@Valid @RequestBody Room room) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.addRoom(room));
    }
}
