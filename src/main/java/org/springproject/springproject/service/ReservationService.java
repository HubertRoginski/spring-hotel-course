package org.springproject.springproject.service;

import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.User;

public interface ReservationService {

    Reservation createReservation(Reservation reservation, User user);

}
