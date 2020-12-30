package org.springproject.springproject.service;

import org.springproject.springproject.model.User;

import java.util.List;

public interface UserCustomerService {

    List<User> createBatchOfUsersWithCustomerAccount(List<User> users);
}
