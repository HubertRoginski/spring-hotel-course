package org.springproject.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springproject.springproject.service.AuthenticationUserService;

@Controller
@Slf4j
public class ContactAndAboutController {

    private final AuthenticationUserService authenticationUserService;

    public ContactAndAboutController(AuthenticationUserService authenticationUserService) {
        this.authenticationUserService = authenticationUserService;
    }

    @GetMapping("/contact")
    public String contact(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        authenticationUserService.isAuthenticatedUserAuthorizedAsAdminOrManager(modelMap, authenticationUser);
        return "contact";
    }

    @GetMapping("/about")
    public String about(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        authenticationUserService.isAuthenticatedUserAuthorizedAsAdminOrManager(modelMap, authenticationUser);
        return "about";
    }
}
