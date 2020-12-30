package org.springproject.springproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.Room;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class ReservationRoomsServiceImpl implements ReservationRoomsService {

    private final RoomService roomService;
    private final ReservationService reservationService;

    public ReservationRoomsServiceImpl(RoomService roomService, ReservationService reservationService) {
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    @Override
    public List<Room> getAllAvailableRooms(LocalDate selectedStartOfBookingDate, LocalDate selectedEndOfBookingDate) {

        List<Room> allRooms = roomService.getAllRooms();

        reservationService.getAllCurrentAndFutureReservations().stream()
                .filter(reservation ->
                        reservation.getStartOfBooking().isEqual(selectedEndOfBookingDate)
                                || reservation.getStartOfBooking().isEqual(selectedStartOfBookingDate)
                                || reservation.getEndOfBooking().isEqual(selectedEndOfBookingDate)
                                || reservation.getEndOfBooking().isEqual(selectedStartOfBookingDate)
                                || (reservation.getStartOfBooking().isAfter(selectedStartOfBookingDate) && reservation.getEndOfBooking().isBefore(selectedEndOfBookingDate))
                                || (reservation.getStartOfBooking().isBefore(selectedStartOfBookingDate) && reservation.getEndOfBooking().isAfter(selectedStartOfBookingDate))
                                || (reservation.getStartOfBooking().isBefore(selectedEndOfBookingDate) && reservation.getEndOfBooking().isAfter(selectedEndOfBookingDate))
                )
                .map(Reservation::getRooms)
                .flatMap(Collection::stream)
                .forEach(allRooms::remove);

        allRooms.sort(Comparator.comparing(Room::getRoomNumber));
        return allRooms;
    }
}
