package org.springproject.springproject.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springproject.springproject.service.AuthenticationUserService;

import java.util.Objects;

@Controller
public class LoginController {

    private final AuthenticationUserService authenticationUserService;

    public LoginController(AuthenticationUserService authenticationUserService) {
        this.authenticationUserService = authenticationUserService;
    }

    @GetMapping("/login")
    public String login(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        authenticationUserService.isAuthenticatedUserAuthorizedAsAdminOrManager(modelMap, authenticationUser);
        return "login";
    }

}
