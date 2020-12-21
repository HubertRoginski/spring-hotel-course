package org.springproject.springproject.service;

import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.User;

import java.util.List;

public interface ReservationService {

    Reservation createReservation(Reservation reservation, User user);

    Boolean deleteReservationById(Long id);

    List<Reservation> getCurrentReservations(User user);

    List<Reservation> getOldReservations(User user);

    List<Reservation> getFutureReservations(User user);

    List<Reservation> getAllCurrentAndFutureReservations();

    boolean isDataValid(Reservation reservation);

}
