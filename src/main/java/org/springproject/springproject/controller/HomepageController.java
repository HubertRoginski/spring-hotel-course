package org.springproject.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springproject.springproject.service.AuthenticationUserService;

import java.util.Objects;

@Controller
@Slf4j
public class HomepageController {

    private final AuthenticationUserService authenticationUserService;

    public HomepageController(AuthenticationUserService authenticationUserService) {
        this.authenticationUserService = authenticationUserService;
    }

    @GetMapping("/")
    public String homepage(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        authenticationUserService.isAuthenticatedUserAuthorizedAsAdminOrManager(modelMap, authenticationUser);
        return "homepage";
    }
}
