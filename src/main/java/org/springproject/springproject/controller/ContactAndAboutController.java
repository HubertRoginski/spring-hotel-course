package org.springproject.springproject.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class ContactAndAboutController {

    @GetMapping("/contact")
    public String contact(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        boolean isUserLogged = Objects.nonNull(authenticationUser);
        modelMap.addAttribute("isUserLogged", isUserLogged);
        if (isUserLogged) {
            boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
            modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);
        }else {
            modelMap.addAttribute("isAuthorizedUserAdminOrManager", false);
        }
        return "contact";
    }

    @GetMapping("/about")
    public String about(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        boolean isUserLogged = Objects.nonNull(authenticationUser);
        modelMap.addAttribute("isUserLogged", isUserLogged);
        if (isUserLogged) {
            boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
            modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);
        }else {
            modelMap.addAttribute("isAuthorizedUserAdminOrManager", false);
        }
        return "about";
    }
}
