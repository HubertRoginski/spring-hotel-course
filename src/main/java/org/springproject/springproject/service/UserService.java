package org.springproject.springproject.service;

import org.springframework.data.domain.Page;
import org.springproject.springproject.model.User;

import java.util.List;

public interface UserService {

    User createNewUser(User user);

    List<User> createBatchOfUsers(List<User> users);

    Page<User> getAllUsers(Integer page, Integer size);

    User getUserById(Long id);

    User updateUserById(Long id, User user);

    Boolean deleteUserById(Long id);

    Page<User> getByKeyword(String keyword,Integer page, Integer size);

    List<User> getUsersWithoutCustomerAccount();

    User getByUsernameOrEmail(String usernameOrEmail);
}
