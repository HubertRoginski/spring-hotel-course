package org.springproject.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springproject.springproject.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

}
