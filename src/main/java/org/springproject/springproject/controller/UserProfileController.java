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
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.UserService;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@Slf4j
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
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
//        User user = userService.getByUsernameOrEmail(authenticationUser.getUsername());
        modelMap.addAttribute("user",user);
        log.info("USER: "+user.toString());

        return "user-profile";
    }

    @GetMapping("/user/{id}/delete")
    public String getUserToDelete(ModelMap modelMap, @PathVariable(name = "id") Long id) {
        modelMap.addAttribute("user", userService.getUserById(id));
        modelMap.addAttribute("updateUser", new User());
        modelMap.addAttribute("updateUserPassword", new User());
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", false);
        return "user-profile";
    }

    @PostMapping("/user/{id}/delete")
    public String deleteUserById(@PathVariable(name = "id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/logout";
    }

    @GetMapping("/user/{id}")
    public String getOneUserById(ModelMap modelMap, @PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser) {
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", false);
        return "user-profile";
    }

    @PostMapping("/user/{id}")
    public String updateUserById(@Valid @ModelAttribute("user") User user, @PathVariable Long id, final Errors errors, ModelMap modelMap) {
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", false);
        if (errors.hasErrors()) {
            return "user-profile";
        }
        log.info("ID: "+id);
        log.info("USER-p: "+user.toString());
        userService.updateUserById(id, user);
        return "redirect:/user/profile";
    }
}
