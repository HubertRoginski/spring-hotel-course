package org.springproject.springproject.service;

import org.springframework.stereotype.Service;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Personnel;

import java.util.List;

public interface CustomerService {

    Customer createNewCustomer(Customer customer);

    Customer getCustomerById(Long id);

    List<Customer> getAllCustomers();

    Customer updateCustomerById(Long id,Customer customer);

    List<Customer> createBatchOfPersonnel(List<Customer> customers);

    boolean removeCustomerById(Long id);

}
