package org.springproject.springproject.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springproject.springproject.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationsManagementServiceImpl implements ReservationsManagementService {

    public final ReservationService reservationService;
    public final CustomerService customerService;
    private final UserService userService;

    public ReservationsManagementServiceImpl(ReservationService reservationService, CustomerService customerService, UserService userService) {
        this.reservationService = reservationService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @Override
    public ReservationsManagement getCurrentReservationsManagement(Integer page, Integer size) {
        List<ReservationsManagementContent> reservationsManagementContentList = new ArrayList<>();

        Page<Reservation> currentReservations = reservationService.getCurrentReservations(page, size);
        return getReservationsManagement(reservationsManagementContentList, currentReservations);
    }

    @Override
    public ReservationsManagement getFutureReservationsManagement(Integer page, Integer size) {
        List<ReservationsManagementContent> reservationsManagementContentList = new ArrayList<>();

        Page<Reservation> futureReservations = reservationService.getFutureReservations(page, size);
        return getReservationsManagement(reservationsManagementContentList, futureReservations);
    }

    @Override
    public ReservationsManagement getOldReservationsManagement(Integer page, Integer size) {
        List<ReservationsManagementContent> reservationsManagementContentList = new ArrayList<>();

        Page<Reservation> oldReservations = reservationService.getOldReservations(page, size);
        return getReservationsManagement(reservationsManagementContentList, oldReservations);
    }

    private ReservationsManagement getReservationsManagement(List<ReservationsManagementContent> reservationsManagementContentList, Page<Reservation> reservationPage) {
        List<Reservation> reservationList = reservationPage.getContent();

        for (Reservation reservation : reservationList) {
            Customer customer = customerService.getCustomerByReservation(reservation);
            User user = userService.getUserByCustomerId(customer.getId());
            reservationsManagementContentList.add(ReservationsManagementContent.builder()
                    .reservation(reservation).customer(customer).user(user).build());
        }

        return ReservationsManagement.builder()
                .reservationPage(reservationPage)
                .reservationsManagementContentList(reservationsManagementContentList)
                .build();
    }

}
