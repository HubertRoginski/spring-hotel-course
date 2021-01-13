package org.springproject.springproject.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Objects;

@Service
public class AuthenticationUserServiceImpl implements AuthenticationUserService{
    @Override
    public boolean isAuthenticatedUser(User authenticationUser) {
        return Objects.nonNull(authenticationUser);
    }

    @Override
    public boolean isAuthorizedUserAdminOrManager(User authenticationUser) {
        return authenticationUser.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
    }

    @Override
    public void isAuthenticatedUserAuthorizedAsAdminOrManager(ModelMap modelMap, User authenticationUser) {
        boolean isUserLogged = isAuthenticatedUser(authenticationUser);
        modelMap.addAttribute("isUserLogged", isUserLogged);
        if (isUserLogged) {
            modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager(authenticationUser));
        }
    }

    @Override
    public void isAuthenticatedUserAuthorizedAsAdmin(ModelMap modelMap, User authenticationUser) {
        boolean isAdmin = authenticationUser.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        modelMap.addAttribute("isAuthorizedUserAdmin", isAdmin);
    }

    @Override
    public void authenticatedUserAuthorizedAsAdminOrManager(ModelMap modelMap) {
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
    }
}
