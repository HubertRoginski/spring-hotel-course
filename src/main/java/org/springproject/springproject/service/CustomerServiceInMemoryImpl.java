package org.springproject.springproject.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Personnel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("singleton")
public class CustomerServiceInMemoryImpl implements CustomerService{

    private final Map<Long, Customer> customerMap = new HashMap<>();
    private Long nextId = 1L;


    @Override
    public Customer createNewCustomer(Customer customer) {
        customer.setCustomerId(getNextId());
        customerMap.put(customer.getCustomerId(),customer);
        return customer;
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerMap.getOrDefault(id,null);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer updateCustomerById(Long id, Customer customer) {
        if (customerMap.containsKey(id)){
            customer.setCustomerId(id);
            return customerMap.replace(id, customer);
        }
        return null;
    }

    @Override
    public boolean removeCustomerById(Long id) {
        if (customerMap.containsKey(id)){
            customerMap.remove(id);
            return true;
        }
        return false;
    }

    private Long getNextId(){
        return nextId++;
    }
}
