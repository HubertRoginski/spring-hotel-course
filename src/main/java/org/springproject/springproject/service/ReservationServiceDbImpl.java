package org.springproject.springproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springproject.springproject.exception.InvalidArgumentsToCreateReservationException;
import org.springproject.springproject.exception.WrongPageException;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.model.User;
import org.springproject.springproject.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
@Slf4j
public class ReservationServiceDbImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;

    public ReservationServiceDbImpl(ReservationRepository reservationRepository, RoomService roomService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
    }

    @Override
    public Reservation createReservation(Reservation reservation, User user) throws InvalidArgumentsToCreateReservationException {
        if (isRoomsDuplicated(reservation.getRooms())) {
            log.info("CREATE RESERVATION FAIL");
            throw new InvalidArgumentsToCreateReservationException("Can't create new reservation, because the selected rooms are duplicating.");
        } else if (!isDateValid(reservation)) {
            log.info("CREATE RESERVATION FAIL");
            throw new InvalidArgumentsToCreateReservationException("Can't create new reservation, because start date of booking can't be after end date.");
        }

        user.getCustomer().addReservation(reservation);
        reservation.setCustomer(user.getCustomer());

        List<Room> roomListToSet = new ArrayList<>();

        reservation.getRooms().stream()
                .filter(room -> Objects.nonNull(room.getRoomNumber()))
                .forEach(room -> {
                    Room roomFromDatabase = roomService.getByRoomNumber(room.getRoomNumber());
                    roomListToSet.add(roomFromDatabase);
                    roomFromDatabase.addReservation(reservation);
                });
        reservation.setRooms(roomListToSet);
        reservation.setCost(calculateReservationCost(reservation));
        return reservationRepository.save(reservation);
    }

    @Override
    public Boolean deleteReservationById(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<Reservation> getCurrentReservations(Integer page, Integer size) {
        Pageable pageable = getPageable(page, size);
        LocalDate currentDate = LocalDate.now();
        return reservationRepository.findCurrentReservations(currentDate,pageable);
    }

    private Pageable getPageable(Integer page, Integer size) {
        if (!Objects.nonNull(page)) {
            page = 1;
        }
        if (!Objects.nonNull(size)) {
            size = 5;
        }
        if (page < 0) {
            throw new WrongPageException("Page number can't be less than 1");
        }
        Sort sort = Sort.by("startOfBooking").ascending();
        return PageRequest.of(page, size, sort);
    }

    @Override
    public Page<Reservation> getFutureReservations(Integer page, Integer size) {
        Pageable pageable = getPageable(page, size);
        LocalDate currentDate = LocalDate.now();
        return reservationRepository.findFutureReservations(currentDate,pageable);
    }

    @Override
    public Page<Reservation> getOldReservations(Integer page, Integer size) {
        Pageable pageable = getPageable(page, size);
        LocalDate currentDate = LocalDate.now();
        return reservationRepository.findOldReservations(currentDate,pageable);
    }


    @Override
    public List<Reservation> getCurrentReservationsOfUser(User user) {
        LocalDate currentDate = LocalDate.now();
        if (Objects.nonNull(user.getCustomer())) {
            return user.getCustomer().getReservations().stream().filter(reservation ->
                    (reservation.getStartOfBooking().isBefore(currentDate) && reservation.getEndOfBooking().isAfter(currentDate))
                            || reservation.getStartOfBooking().isEqual(currentDate)
                            || reservation.getEndOfBooking().isEqual(currentDate))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Reservation> getOldReservationsOfUser(User user) {
        LocalDate currentDate = LocalDate.now();
        if (Objects.nonNull(user.getCustomer())) {
            return user.getCustomer().getReservations().stream().filter(reservation ->
                    reservation.getEndOfBooking().isBefore(currentDate))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Reservation> getFutureReservationsOfUser(User user) {
        LocalDate currentDate = LocalDate.now();
        if (Objects.nonNull(user.getCustomer())) {
            return user.getCustomer().getReservations().stream().filter(reservation ->
                    reservation.getStartOfBooking().isAfter(currentDate))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Reservation> getAllCurrentAndFutureReservations() {
        LocalDate currentDate = LocalDate.now();
        return reservationRepository.findAll().stream().filter(reservation ->
                reservation.getStartOfBooking().isAfter(currentDate)
                        || reservation.getStartOfBooking().isEqual(currentDate))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isDateValid(Reservation reservation) {
        return !reservation.getStartOfBooking().isAfter(reservation.getEndOfBooking());
    }

    private Long calculateReservationCost(Reservation reservation) {
        // plus one day because start date need to be included to total cost
        long daysOfBooking = ChronoUnit.DAYS.between(reservation.getStartOfBooking(), reservation.getEndOfBooking()) + 1;
        long sumOneDayCost = reservation.getRooms().stream().mapToLong(Room::getOneDayCost).sum();
        return daysOfBooking * sumOneDayCost;
    }

    private boolean isRoomsDuplicated(List<Room> rooms) {
        Set<Room> roomSet = rooms.stream().filter(Objects::nonNull).collect(Collectors.toSet());
        return rooms.size() != roomSet.size();
    }
}
