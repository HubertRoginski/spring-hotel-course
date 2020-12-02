package org.springproject.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.UserService;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers(ModelMap modelMap,@AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        modelMap.addAttribute("usersList", userService.getAllUsers());
        boolean isAuthorizedUserAdmin = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        modelMap.addAttribute("isAuthorizedUserAdmin",isAuthorizedUserAdmin);
        modelMap.addAttribute("isUserLogged",true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager",true);
        return "users";
    }

    @GetMapping("/users/{id}")
    public String getOneUserById(ModelMap modelMap, @PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        modelMap.addAttribute("user",userService.getUserById(id));
        modelMap.addAttribute("updateUser",new User());
        modelMap.addAttribute("isUserLogged",true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager",true);
        boolean isAuthorizedUserAdmin = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        modelMap.addAttribute("isAuthorizedUserAdmin",isAuthorizedUserAdmin);
        return "one-user";
    }

    @PostMapping("/users/{id}")
    public String updateUserById(@Valid @ModelAttribute("user") User user, @PathVariable Long id, final Errors errors){
        if (errors.hasErrors()){
            return "one-user";
        }
        userService.updateUserById(id,user);
        return "redirect:/users/"+id;
    }

    @GetMapping("/users/{id}/delete")
    public String getUserToDelete(ModelMap modelMap, @PathVariable(name = "id") Long id){
        modelMap.addAttribute("user",userService.getUserById(id));
        modelMap.addAttribute("updateUser",new User());
        modelMap.addAttribute("isUserLogged",true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager",true);
        return "one-user";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUserById(@PathVariable(name = "id") Long id){
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @GetMapping("/users/add")
    public String showAddUser(ModelMap modelMap){
        modelMap.addAttribute("isUserLogged",true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager",true);
        modelMap.addAttribute("user", new User());
        return "user-add";
    }

    @PostMapping("/users/add")
    public String addUser(@Valid @ModelAttribute("user") User user, final Errors errors){
        if (errors.hasErrors()){
            return "user-add";
        }
        userService.createNewUser(user);
        return "redirect:/users";

    }

}
