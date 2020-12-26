package org.springproject.springproject.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.Room;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.RoomService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public String showRooms(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser,
                            @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "5") Integer size){
        modelMap.addAttribute("isUserLogged", true);
        boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);

        modelMap.addAttribute("roomsList", roomService.getAllRooms(page - 1, size).getContent());
        Page<Room> roomPage = roomService.getAllRooms(page - 1, size);
        modelMap.addAttribute("roomPage", roomPage);

        int totalPages = roomPage.getTotalPages();
        if (totalPages > 0) {
            int pageOffset = 2;
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .filter(integer -> (integer == 1) || ((integer >= page - pageOffset) && (integer <= page + pageOffset)) || (integer == totalPages))
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }

        return "rooms";
    }

    @GetMapping("/rooms/{id}")
    public String getOneRoomById(ModelMap modelMap, @PathVariable Long id) {
        modelMap.addAttribute("room", roomService.getRoomById(id));
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        return "one-room";
    }

    @PostMapping("/rooms/{id}")
    public String updateRoomById(@Valid @ModelAttribute("room") Room room, @PathVariable Long id, final Errors errors, ModelMap modelMap) {
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        modelMap.addAttribute("room", roomService.getRoomById(id));
        if (errors.hasErrors()) {
            return "one-room";
        }
        Room updatedRoom = roomService.updateRoomById(id, room);
        if (Objects.isNull(updatedRoom)) {
            modelMap.addAttribute("roomExistsError","Can't create new room, because that room number already exist.");
            return "one-room";
        }
        return "redirect:/rooms/" + id;
    }

    @PostMapping("/rooms/{id}/delete")
    public String deleteRoomById(@PathVariable(name = "id") Long id) {
        roomService.deleteRoomById(id);
        return "redirect:/rooms";
    }

    @GetMapping("/rooms/add")
    public String showAddRooms(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        modelMap.addAttribute("room", new Room());

        return "room-add";
    }

    @PostMapping("/rooms/add")
    public String addRoom(@Valid @ModelAttribute("room") Room room, final Errors errors, ModelMap modelMap){
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        if (errors.hasErrors()){
            return "room-add";
        }

        Room addedRoom = roomService.addRoom(room);
        if (Objects.isNull(addedRoom)) {
            modelMap.addAttribute("roomExistsError","Can't create new room, because that room number already exist.");
            return "room-add";
        }

        return "redirect:/rooms";

    }
}
