package org.springproject.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springproject.springproject.exception.InvalidArgumentsToCreateReservationException;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.CustomerService;
import org.springproject.springproject.service.ReservationService;
import org.springproject.springproject.service.RoomService;
import org.springproject.springproject.service.UserService;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;
    private final CustomerService customerService;
    private final UserService userService;
    private final RoomService roomService;

    public ReservationController(ReservationService reservationService, CustomerService customerService, UserService userService, RoomService roomService) {
        this.reservationService = reservationService;
        this.customerService = customerService;
        this.userService = userService;
        this.roomService = roomService;
    }

    @GetMapping("/reservations")
    public String showAddReservations(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser) {
        modelMap.addAttribute("isUserLogged", true);
        boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);

        modelMap.addAttribute("reservation", new Reservation());
        modelMap.addAttribute("customer", new Customer());

        modelMap.addAttribute("roomList", roomService.getAllNotOccupiedRooms());

        User user = userService.getByUsernameOrEmail(authenticationUser.getUsername());

        boolean isCustomerExists = Objects.nonNull(user.getCustomer());
        modelMap.addAttribute("isCustomerExists", isCustomerExists);

        modelMap.addAttribute("currentReservations", reservationService.showCurrentReservations(user));
        modelMap.addAttribute("oldReservations", reservationService.showOldReservations(user));
        modelMap.addAttribute("futureReservations", reservationService.showFutureReservations(user));

        modelMap.addAttribute("isItFirstStep", true);

        return "/reservations";
    }

    @PostMapping("/reservations")
    public String addReservation(@Valid @ModelAttribute("reservation") Reservation reservation, final Errors errors, ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser) {
        modelMap.addAttribute("isUserLogged", true);
        boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);

        modelMap.addAttribute("roomList", roomService.getAllNotOccupiedRooms());
        modelMap.addAttribute("isCustomerExists", true);
        User user = userService.getByUsernameOrEmail(authenticationUser.getUsername());
        modelMap.addAttribute("currentReservations", reservationService.showCurrentReservations(user));
        modelMap.addAttribute("oldReservations", reservationService.showOldReservations(user));
        modelMap.addAttribute("futureReservations", reservationService.showFutureReservations(user));
        if (errors.hasErrors()) {
            return "reservations";
        }
        modelMap.addAttribute("isItFirstStep", true);
        if (reservation.getRooms().size() == 0) {
            if (reservationService.isDataValid(reservation)) {
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
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isCustomerExists", false);
        if (errors.hasErrors()) {
            return "reservations";
        }
        customerService.createNewCustomer(customer, userService.getByUsernameOrEmail(authenticationUser.getUsername()));

        return "redirect:/reservations";
    }

    @PostMapping("/reservations/{id}/delete")
    public String deleteUserById(@PathVariable(name = "id") Long id) {
        reservationService.deleteReservationById(id);
        return "redirect:/reservations";
    }
}
