package org.springproject.springproject.service;

import org.springframework.data.domain.Page;
import org.springproject.springproject.model.User;

import java.util.List;

public interface UserService {

    User createNewUser(User user);

    Page<User> getAllUsers(Integer page, Integer size);

    User getUserById(Long id);

    User updateUserById(Long id, User user);

    Boolean deleteUserById(Long id);
}
