package org.springproject.springproject.service;

import org.springproject.springproject.model.User;

import java.util.List;

public interface UserService {

    User createNewUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUserById(Long id, User user);

    Boolean deleteUserById(Long id);
}
