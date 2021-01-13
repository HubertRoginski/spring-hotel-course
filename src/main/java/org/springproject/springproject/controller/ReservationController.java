package org.springproject.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.exception.InvalidArgumentsToCreateReservationException;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;
    private final CustomerService customerService;
    private final UserService userService;
    private final ReservationRoomsService reservationRoomsService;
    private final AuthenticationUserService authenticationUserService;

    public ReservationController(ReservationService reservationService, CustomerService customerService, UserService userService, ReservationRoomsService reservationRoomsService, AuthenticationUserService authenticationUserService) {
        this.reservationService = reservationService;
        this.customerService = customerService;
        this.userService = userService;
        this.reservationRoomsService = reservationRoomsService;
        this.authenticationUserService = authenticationUserService;
    }

    @GetMapping("/reservations")
    public String showAddReservations(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser) {
        authenticationUserService.isAuthenticatedUserAuthorizedAsAdminOrManager(modelMap, authenticationUser);

        modelMap.addAttribute("reservation", new Reservation());
        modelMap.addAttribute("customer", new Customer());

        User user = userService.getByUsernameOrEmail(authenticationUser.getUsername());

        boolean isCustomerExists = Objects.nonNull(user.getCustomer());
        modelMap.addAttribute("isCustomerExists", isCustomerExists);

        modelMap.addAttribute("currentReservations", reservationService.getCurrentReservationsOfUser(user));
        modelMap.addAttribute("oldReservations", reservationService.getOldReservationsOfUser(user));
        modelMap.addAttribute("futureReservations", reservationService.getFutureReservationsOfUser(user));

        modelMap.addAttribute("isItFirstStep", true);

        return "reservations";
    }

    @PostMapping("/reservations")
    public String addReservation(@Valid @ModelAttribute("reservation") Reservation reservation, final Errors errors, ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser) {
        authenticationUserService.isAuthenticatedUserAuthorizedAsAdminOrManager(modelMap, authenticationUser);

        modelMap.addAttribute("isCustomerExists", true);
        User user = userService.getByUsernameOrEmail(authenticationUser.getUsername());
        modelMap.addAttribute("currentReservations", reservationService.getCurrentReservationsOfUser(user));
        modelMap.addAttribute("oldReservations", reservationService.getOldReservationsOfUser(user));
        modelMap.addAttribute("futureReservations", reservationService.getFutureReservationsOfUser(user));

        modelMap.addAttribute("roomList", reservationRoomsService.getAllAvailableRooms(reservation.getStartOfBooking(),reservation.getEndOfBooking()));
        if (errors.hasErrors()) {
            return "reservations";
        }
        modelMap.addAttribute("isItFirstStep", true);
        if (reservation.getRooms().size() == 0) {
            if (reservationService.isDateValid(reservation)) {
                modelMap.addAttribute("isItFirstStep", false);
                log.info("First step end with: " + reservation.toString());

            } else {
                modelMap.addAttribute("errorMessage", "Can't create new reservation, because start date of booking can't be after end date.");
            }
            return "reservations";
        } else {

            try {
                reservationService.createReservation(reservation, userService.getByUsernameOrEmail(authenticationUser.getUsername()));
            } catch (InvalidArgumentsToCreateReservationException e) {
                modelMap.addAttribute("errorMessage", e.getMessage());
                modelMap.addAttribute("isItFirstStep", false);
                return "reservations";
            }
            log.info("Second step end with: " + reservation.toString());
        }
        return "redirect:/reservations";

    }

    @PostMapping("/reservations/create-customer")
    public String addCustomer(@Valid @ModelAttribute("customer") Customer customer, final Errors errors, ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser) {
        authenticationUserService.isAuthenticatedUserAuthorizedAsAdminOrManager(modelMap, authenticationUser);
        modelMap.addAttribute("isCustomerExists", false);
        if (errors.hasErrors()) {
            return "reservations";
        }
        customerService.createNewCustomer(customer, userService.getByUsernameOrEmail(authenticationUser.getUsername()));

        return "redirect:/reservations";
    }

    @PostMapping("/reservations/{id}/delete")
    public String deleteFutureReservationById(@PathVariable(name = "id") Long id) {
        reservationService.deleteReservationById(id);
        return "redirect:/reservations";
    }


}
