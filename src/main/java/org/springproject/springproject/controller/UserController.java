package org.springproject.springproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers(ModelMap modelMap){
        modelMap.addAttribute("usersList", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String getOneUserById(ModelMap modelMap, @PathVariable Long id){
        modelMap.addAttribute("user",userService.getUserById(id));
        modelMap.addAttribute("updateUser",new User());
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

    @GetMapping("/users/add")
    public String showAddUser(ModelMap modelMap){
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
