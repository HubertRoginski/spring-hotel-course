package org.springproject.springproject.service;

import org.springproject.springproject.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRoomsService {

    List<Room> getAllAvailableRooms(LocalDate selectedStartOfBookingDate, LocalDate selectedEndOfBookingDate);
}
