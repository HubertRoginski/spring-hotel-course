package org.springproject.springproject.service;

import org.springframework.data.domain.Page;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.User;

import java.util.List;

public interface ReservationService {

    Reservation createReservation(Reservation reservation, User user);

    Boolean deleteReservationById(Long id);

    Page<Reservation> getCurrentReservations(Integer page, Integer size);

    Page<Reservation> getFutureReservations(Integer page, Integer size);

    Page<Reservation> getOldReservations(Integer page, Integer size);

    List<Reservation> getCurrentReservationsOfUser(User user);

    List<Reservation> getOldReservationsOfUser(User user);

    List<Reservation> getFutureReservationsOfUser(User user);

    List<Reservation> getAllCurrentAndFutureReservations();

    boolean isDateValid(Reservation reservation);

}
