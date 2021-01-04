package org.springproject.springproject.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.ReservationsManagement;
import org.springproject.springproject.service.ReservationService;
import org.springproject.springproject.service.ReservationsManagementService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ReservationsManagementController {

    private final ReservationsManagementService reservationsManagementService;
    private final ReservationService reservationService;

    public ReservationsManagementController(ReservationsManagementService reservationsManagementService, ReservationService reservationService) {
        this.reservationsManagementService = reservationsManagementService;
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations/management/current-reservations")
    public String getAllCurrentReservations(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser,
                                            @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "5") Integer size){
        modelMap.addAttribute("isUserLogged", true);
        boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);
        ReservationsManagement currentReservationsManagement = reservationsManagementService.getCurrentReservationsManagement(page - 1, size);
        modelMap.addAttribute("currentReservationsManagement", currentReservationsManagement.getReservationsManagementContentList());
        Page<Reservation> currentReservationsPage = currentReservationsManagement.getReservationPage();
        modelMap.addAttribute("currentReservationsPage", currentReservationsPage);

        int totalPages = currentReservationsPage.getTotalPages();
        getPageNumbers(modelMap, page, totalPages);

        return "reservations-management-current";
    }

    @GetMapping("/reservations/management/future-reservations")
    public String getAllFutureReservations(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser,
                                           @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "5") Integer size){
        modelMap.addAttribute("isUserLogged", true);
        boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);
        ReservationsManagement futureReservationsManagement = reservationsManagementService.getFutureReservationsManagement(page - 1, size);
        modelMap.addAttribute("futureReservationsManagement", futureReservationsManagement.getReservationsManagementContentList());
        Page<Reservation> futureReservationsPage = futureReservationsManagement.getReservationPage();
        modelMap.addAttribute("futureReservationsPage", futureReservationsPage);

        int totalPages = futureReservationsPage.getTotalPages();
        getPageNumbers(modelMap, page, totalPages);

        return "reservations-management-future";
    }

    @GetMapping("/reservations/management/old-reservations")
    public String getAllOldReservations(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser,
                                        @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "5") Integer size){
        modelMap.addAttribute("isUserLogged", true);
        boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);

        ReservationsManagement oldReservationsManagement = reservationsManagementService.getOldReservationsManagement(page - 1, size);
        modelMap.addAttribute("oldReservationsManagement", oldReservationsManagement.getReservationsManagementContentList());
        Page<Reservation> oldReservationsPage = oldReservationsManagement.getReservationPage();
        modelMap.addAttribute("oldReservationsPage", oldReservationsPage);

        int totalPages = oldReservationsPage.getTotalPages();
        getPageNumbers(modelMap, page, totalPages);

        return "reservations-management-old";
    }

    @PostMapping("/reservations/management/current-reservations/{id}/delete")
    public String deleteCurrentReservationById(@PathVariable(name = "id") Long id) {
        reservationService.deleteReservationById(id);
        return "redirect:/reservations/management/current-reservations";
    }

    @PostMapping("/reservations/management/future-reservations/{id}/delete")
    public String deleteFutureReservationById(@PathVariable(name = "id") Long id) {
        reservationService.deleteReservationById(id);
        return "redirect:/reservations/management/future-reservations";
    }

    @PostMapping("/reservations/management/old-reservations/{id}/delete")
    public String deleteOldReservationById(@PathVariable(name = "id") Long id) {
        reservationService.deleteReservationById(id);
        return "redirect:/reservations/management/old-reservations";
    }

    private void getPageNumbers(ModelMap modelMap, Integer page, int totalPages) {
        if (totalPages > 0) {
            int pageOffset = 2;
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .filter(integer -> (integer == 1) || ((integer >= page - pageOffset) && (integer <= page + pageOffset)) || (integer == totalPages))
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
    }


}
