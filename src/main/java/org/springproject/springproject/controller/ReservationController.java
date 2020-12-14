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
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.CustomerService;
import org.springproject.springproject.service.ReservationService;
import org.springproject.springproject.service.UserService;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final CustomerService customerService;
    private final UserService userService;

    public ReservationController(ReservationService reservationService, CustomerService customerService, UserService userService) {
        this.reservationService = reservationService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @GetMapping("/reservations")
    public String showAddReservations(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        modelMap.addAttribute("isUserLogged", true);
        boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);


        modelMap.addAttribute("reservation", new Reservation());

        boolean isCustomerExists = Objects.nonNull(userService.getByUsernameOrEmail(authenticationUser.getUsername()).getCustomer());
        modelMap.addAttribute("isCustomerExists", isCustomerExists);


        return "/reservations";
    }

    @PostMapping("/reservations/customer")
    public String addReservation(@Valid @ModelAttribute("reservation") Reservation reservation, final Errors errors, ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        modelMap.addAttribute("isUserLogged", true);
        if (errors.hasErrors()){
            return "reservations";
        }

//        boolean isCustomerExists = Objects.nonNull(userService.getByUsernameOrEmail(authenticationUser.getUsername()));
//        modelMap.addAttribute("isCustomerExists", isCustomerExists);


        reservationService.createReservation(reservation,userService.getByUsernameOrEmail(authenticationUser.getUsername()));

//            Customer newCustomer = customerService.createNewCustomer(customer, userService.getByUsernameOrEmail(authenticationUser.getUsername()));
//            reservationService.createReservation(reservation,newCustomer);



        return "redirect:/user/profile";

    }
}
