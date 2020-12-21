package org.springproject.springproject.service;

import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.User;

import java.util.List;

public interface ReservationService {

    Reservation createReservation(Reservation reservation, User user);

    Boolean deleteReservationById(Long id);

    List<Reservation> showCurrentReservations(User user);

    List<Reservation> showOldReservations(User user);

    List<Reservation> showFutureReservations(User user);

    boolean isDataValid(Reservation reservation);

}
