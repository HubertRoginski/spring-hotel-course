package org.springproject.springproject.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.ui.ModelMap;

public interface AuthenticationUserService {

    boolean isAuthenticatedUser(User authenticationUser);

    boolean isAuthorizedUserAdminOrManager(User authenticationUser);

    void isAuthenticatedUserAuthorizedAsAdminOrManager(ModelMap modelMap, User authenticationUser);

    void isAuthenticatedUserAuthorizedAsAdmin(ModelMap modelMap, User authenticationUser);

    void authenticatedUserAuthorizedAsAdminOrManager(ModelMap modelMap);

}
