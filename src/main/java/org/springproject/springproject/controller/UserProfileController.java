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
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.CustomerService;
import org.springproject.springproject.service.ReservationService;
import org.springproject.springproject.service.UserService;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Controller
@Slf4j
public class UserProfileController {

    private final UserService userService;
    private final ReservationService reservationService;
    private final CustomerService customerService;

    public UserProfileController(UserService userService, ReservationService reservationService, CustomerService customerService) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.customerService = customerService;
    }

    @GetMapping("/user/profile")
    public String getUserProfile(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        modelMap.addAttribute("isUserLogged", true);
        boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);

        modelMap.addAttribute("updateUser", new User());
        modelMap.addAttribute("updateUserPassword", new User());

        User user = new User();
        log.info("AUSER: "+authenticationUser.toString());
        if (Objects.nonNull(userService.getByUsernameOrEmail(authenticationUser.getUsername()))){
            user = userService.getByUsernameOrEmail(authenticationUser.getUsername());
        }
        modelMap.addAttribute("user",user);
        log.info("USER: "+user.toString());

        modelMap.addAttribute("currentReservations", reservationService.getCurrentReservations(user));


        return "user-profile";
    }

    @PostMapping("/user/{id}/delete")
    public String deleteUserById(@PathVariable(name = "id") Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser) {
        Optional<User> userById = Optional.ofNullable(userService.getUserById(id));
        Optional<User> userByUsernameOrEmail = Optional.ofNullable(userService.getByUsernameOrEmail(authenticationUser.getUsername()));
        if (userById.equals(userByUsernameOrEmail) && userById.isPresent() && userByUsernameOrEmail.isPresent()) {
            userService.deleteUserById(id);
            return "redirect:/logout";
        }
        return "error";
    }

    @PostMapping("/user/{id}")
    public String updateUserById(@Valid @ModelAttribute("user") User user, final Errors errors, @PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser, ModelMap modelMap) {
        Optional<User> userById = Optional.ofNullable(userService.getUserById(id));
        Optional<User> userByUsernameOrEmail = Optional.ofNullable(userService.getByUsernameOrEmail(authenticationUser.getUsername()));
        if (userById.equals(userByUsernameOrEmail) && userById.isPresent() && userByUsernameOrEmail.isPresent()) {
            modelMap.addAttribute("isUserLogged", true);
            boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
            modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);
            if (errors.hasErrors()) {
                return "user-profile";
            }
            log.info("ID: " + id);
            log.info("USER-p: " + user.toString());
            userService.updateUserById(id, user);
            return "redirect:/user/profile";
        }
        return "error";
    }

    @GetMapping("/user/profile/customer/create")
    public String getCustomerCreate(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser) {
        modelMap.addAttribute("isUserLogged", true);
        boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);
        modelMap.addAttribute("createdCustomer",new Customer());
        return "user-profile-customer-create";
    }

    @PostMapping("/user/profile/customer/create")
    public String addCustomer(@Valid @ModelAttribute("createdCustomer") Customer customer, final Errors errors, ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser) {
        modelMap.addAttribute("isUserLogged", true);
        boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);
        if (errors.hasErrors()) {
            return "user-profile-customer-create";
        }
        customerService.createNewCustomer(customer, userService.getByUsernameOrEmail(authenticationUser.getUsername()));

        return "redirect:/user/profile";
    }

    @GetMapping("/user/profile/customer/{id}")
    public String customerWithId(ModelMap modelMap, @PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        Optional<User> userByCustomerId = Optional.ofNullable(userService.getUserByCustomerId(id));
        if (userByCustomerId.isPresent()) {
            Optional<User> userById = Optional.ofNullable(userService.getUserById(userByCustomerId.get().getId()));
            Optional<User> userByUsernameOrEmail = Optional.ofNullable(userService.getByUsernameOrEmail(authenticationUser.getUsername()));
            if (userById.equals(userByUsernameOrEmail) && userById.isPresent() && userByUsernameOrEmail.isPresent()) {
                modelMap.addAttribute("customer", customerService.getCustomerById(id));
                modelMap.addAttribute("customerTable", customerService.getCustomerById(id));
                modelMap.addAttribute("isUserLogged", true);
                boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
                modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);
                return "user-profile-customer";
            }
        }
        return "error";
    }

    @PostMapping("/user/profile/customer/{id}")
    public String updateCustomer(@Valid @ModelAttribute("customer") Customer customer, final Errors errors, @PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser, ModelMap modelMap){
        Optional<User> userByCustomerId = Optional.ofNullable(userService.getUserByCustomerId(id));
        if (userByCustomerId.isPresent()) {
            Optional<User> userById = Optional.ofNullable(userService.getUserById(userByCustomerId.get().getId()));
            Optional<User> userByUsernameOrEmail = Optional.ofNullable(userService.getByUsernameOrEmail(authenticationUser.getUsername()));
            if (userById.equals(userByUsernameOrEmail) && userById.isPresent() && userByUsernameOrEmail.isPresent()) {
                modelMap.addAttribute("isUserLogged", true);
                boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
                modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);
                modelMap.addAttribute("customerTable", customerService.getCustomerById(id));
                if (errors.hasErrors()) {
                    return "user-profile-customer";
                }
                Customer updatedCustomer = customerService.updateCustomerById(id, customer);
                if (Objects.isNull(updatedCustomer)) {
                    modelMap.addAttribute("customerExistsError", "Can't update customer, because that customer don't exist.");
                    return "user-profile-customer";
                }
                return "redirect:/user/profile";
            }
        }
        return "error";
    }

}
