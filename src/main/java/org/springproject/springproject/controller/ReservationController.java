package org.springproject.springproject.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.CustomerService;
import org.springproject.springproject.service.ReservationService;
import org.springproject.springproject.service.RoomService;
import org.springproject.springproject.service.UserService;

import javax.validation.Valid;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
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
    public String showAddReservations(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        modelMap.addAttribute("isUserLogged", true);
        boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);


        modelMap.addAttribute("reservation", new Reservation());
        modelMap.addAttribute("customer", new Customer());

        modelMap.addAttribute("roomList",roomService.getAllNotOccupiedRooms());

        User user = userService.getByUsernameOrEmail(authenticationUser.getUsername());

        boolean isCustomerExists = Objects.nonNull(user.getCustomer());
        modelMap.addAttribute("isCustomerExists", isCustomerExists);

        modelMap.addAttribute("currentReservations",reservationService.showCurrentReservations(user));
        modelMap.addAttribute("oldReservations",reservationService.showOldReservations(user));
        modelMap.addAttribute("futureReservations",reservationService.showFutureReservations(user));

        return "/reservations";
    }

    @PostMapping("/reservations")
    public String addReservation(@Valid @ModelAttribute("reservation") Reservation reservation, final Errors errors, ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        modelMap.addAttribute("isUserLogged", true);
        boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);

        modelMap.addAttribute("roomList",roomService.getAllNotOccupiedRooms());
        modelMap.addAttribute("isCustomerExists", true);
        if (errors.hasErrors()){
            return "reservations";
        }
        reservationService.createReservation(reservation,userService.getByUsernameOrEmail(authenticationUser.getUsername()));
        return "redirect:/reservations";

    }
    @PostMapping("/reservations/create-customer")
    public String addCustomer(@Valid @ModelAttribute("customer") Customer customer, final Errors errors, ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isCustomerExists", false);
        if (errors.hasErrors()){
            return "reservations";
        }
        customerService.createNewCustomer(customer,userService.getByUsernameOrEmail(authenticationUser.getUsername()));

        return "redirect:/reservations";

    }
}
