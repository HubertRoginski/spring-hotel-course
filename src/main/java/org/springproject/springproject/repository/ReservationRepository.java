package org.springproject.springproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Employee;
import org.springproject.springproject.model.Reservation;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query(value = "SELECT r FROM Reservation r where r.startOfBooking <= :currentDate and r.endOfBooking >= :currentDate")
    Page<Reservation> findCurrentReservations(@Param("currentDate") LocalDate currentDate, Pageable pageable);

    @Query(value = "SELECT r FROM Reservation r where r.startOfBooking > :currentDate")
    Page<Reservation> findFutureReservations(@Param("currentDate") LocalDate currentDate, Pageable pageable);

    @Query(value = "SELECT r FROM Reservation r where r.endOfBooking < :currentDate")
    Page<Reservation> findOldReservations(@Param("currentDate") LocalDate currentDate, Pageable pageable);

}
