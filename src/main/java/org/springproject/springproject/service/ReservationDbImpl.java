package org.springproject.springproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.model.User;
import org.springproject.springproject.repository.ReservationRepository;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Scope("prototype")
@Slf4j
public class ReservationDbImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;

    public ReservationDbImpl(ReservationRepository reservationRepository, RoomService roomService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
    }

    @Override
    public Reservation createReservation(Reservation reservation, User user) {
        user.getCustomer().addReservation(reservation);
        reservation.setCustomer(user.getCustomer());

        List<Room> roomListToSet = new ArrayList<>();

        reservation.getRooms().stream()
                .filter(room -> Objects.nonNull(room.getRoomNumber()))
                .forEach(room ->    {
                                        Room roomFromDatabase = roomService.getByRoomNumber(room.getRoomNumber());
                                        roomListToSet.add(roomFromDatabase);
                                        roomFromDatabase.addReservation(reservation);
                                    });
        reservation.setRooms(roomListToSet);
        reservation.setCost(calculateReservationCost(reservation));
        return reservationRepository.save(reservation);
    }

    private Long calculateReservationCost(Reservation reservation) {
        // plus one day because start date need to be included to total cost
        long daysOfBooking = ChronoUnit.DAYS.between(reservation.getStartOfBooking(), reservation.getEndOfBooking()) + 1;
        log.info("DAYS: " + daysOfBooking);
        long sumOneDayCost = reservation.getRooms().stream().mapToLong(Room::getOneDayCost).sum();
        return daysOfBooking * sumOneDayCost;
    }
}
