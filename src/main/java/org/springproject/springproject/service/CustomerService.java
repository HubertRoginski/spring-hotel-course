package org.springproject.springproject.service;

import org.springframework.data.domain.Page;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Employee;
import org.springproject.springproject.model.User;

import java.util.List;

public interface CustomerService {

    Customer createNewCustomer(Customer customer, User user);

    Customer getCustomerById(Long id);

    Page<Customer> getAllCustomers(Integer page, Integer size);

    Page<Customer> getByKeyword(String keyword, Integer page, Integer size);

    Customer updateCustomerById(Long id,Customer customer);

    boolean removeCustomerById(Long id);

}
