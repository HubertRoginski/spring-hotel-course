package org.springproject.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser,
                           @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "5") Integer size,
                           String keyword) {
        Page<User> userPage;
        if (Objects.nonNull(keyword)) {
            modelMap.addAttribute("usersList", userService.getByKeyword(keyword, page - 1, size).getContent());
            userPage = userService.getByKeyword(keyword, page - 1, size);
            modelMap.addAttribute("userPage", userPage);
            modelMap.addAttribute("addedKeyword", keyword);
        } else {
            modelMap.addAttribute("usersList", userService.getAllUsers(page - 1, size).getContent());
            userPage = userService.getAllUsers(page - 1, size);
            modelMap.addAttribute("userPage", userPage);
            modelMap.addAttribute("addedKeyword", null);
        }
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            int pageOffset = 2;
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .filter(integer -> (integer == 1) || ((integer >= page - pageOffset) && (integer <= page + pageOffset)) || (integer == totalPages))
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }

        boolean isAuthorizedUserAdmin = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        modelMap.addAttribute("isAuthorizedUserAdmin", isAuthorizedUserAdmin);
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        return "users";
    }

    @GetMapping("/users/{id}")
    public String getOneUserById(ModelMap modelMap, @PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser) {
        modelMap.addAttribute("user", userService.getUserById(id));
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        boolean isAuthorizedUserAdmin = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        modelMap.addAttribute("isAuthorizedUserAdmin", isAuthorizedUserAdmin);
        return "one-user";
    }

    @PostMapping("/users/{id}")
    public String updateUserById(@Valid @ModelAttribute("user") User user, @PathVariable Long id, final Errors errors, ModelMap modelMap) {
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        if (errors.hasErrors()) {
            return "one-user";
        }
        User updatedUser = userService.updateUserById(id, user);
        if (Objects.isNull(updatedUser)) {
            modelMap.addAttribute("userExistsError","Can't create new user, because that username or email already exist.");
            return "one-user";
        }
        return "redirect:/users/" + id;
    }

    @GetMapping("/users/{id}/delete")
    public String getUserToDelete(ModelMap modelMap, @PathVariable(name = "id") Long id) {
        modelMap.addAttribute("user", userService.getUserById(id));
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        return "one-user";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUserById(@PathVariable(name = "id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @GetMapping("/users/add")
    public String showAddUser(ModelMap modelMap) {
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        modelMap.addAttribute("user", new User());
        return "user-add";
    }

    @PostMapping("/users/add")
    public String addUser(@Valid @ModelAttribute("user") User user, final Errors errors, ModelMap modelMap) {
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        if (errors.hasErrors()) {
            return "user-add";
        }
        User newUser = userService.createNewUser(user);
        if (Objects.isNull(newUser)) {
            modelMap.addAttribute("userExistsError","Can't create new user, because that username or email already exist.");
            return "user-add";
        }
        return "redirect:/users";

    }


}
