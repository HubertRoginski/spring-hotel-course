package org.springproject.springproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.User;
import org.springproject.springproject.repository.ReservationRepository;

import java.time.temporal.ChronoUnit;

@Service
@Scope("prototype")
@Slf4j
public class ReservationDbImpl implements ReservationService{

    private final ReservationRepository reservationRepository;

    public ReservationDbImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation createReservation(Reservation reservation, Customer customer) {
        customer.addReservation(reservation);
        reservation.setCustomer(customer);
        reservation.setCost(calculateReservationCost(reservation));
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation createReservation(Reservation reservation, User user) {
        user.getCustomer().addReservation(reservation);
        reservation.setCustomer(user.getCustomer());
        reservation.setCost(calculateReservationCost(reservation));
        return reservationRepository.save(reservation);
    }

    private Long calculateReservationCost(Reservation reservation){
        // plus one day because start date need to be included to total cost
        long daysOfBooking = ChronoUnit.DAYS.between(reservation.getStartOfBooking(), reservation.getEndOfBooking())+1;
        log.info("DAYS: "+daysOfBooking);
        return daysOfBooking * 100;
    }
}
