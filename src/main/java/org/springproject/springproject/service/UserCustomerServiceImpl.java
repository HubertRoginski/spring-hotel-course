package org.springproject.springproject.service;

import org.springframework.stereotype.Service;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.User;

import java.util.List;
@Service
public class UserCustomerServiceImpl implements UserCustomerService{

    private final UserService userService;
    private final CustomerService customerService;

    public UserCustomerServiceImpl(UserService userService, CustomerService customerService) {
        this.userService = userService;
        this.customerService = customerService;
    }

    @Override
    public List<User> createBatchOfUsersWithCustomerAccount(List<User> users) {
        users.forEach(user -> {
            Customer customer = user.getCustomer();
            user.setCustomer(null);
            User createdUser = userService.createNewUser(user);
            customerService.createNewCustomer(customer, createdUser);
            });
        return users;
    }
}
