package org.springproject.springproject.service;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springproject.springproject.config.HotelCustomerConfig;
import org.springproject.springproject.model.Customer;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Profile("old")
@Service
@Scope("singleton")
public class CustomerServiceInMemoryImpl{
//public class CustomerServiceInMemoryImpl implements CustomerService{
//
//    private final Map<Long, Customer> customerMap = new HashMap<>();
//    private Long nextId = 1L;
//
//
//    @Override
//    public Customer createNewCustomer(Customer customer) {
//        customer.setCustomerId(getNextId());
//        customerMap.put(customer.getCustomerId(),customer);
//        return customer;
//    }
//
//    @Override
//    public Customer getCustomerById(Long id) {
//        return customerMap.getOrDefault(id,null);
//    }
//
//    @Override
//    public List<Customer> getAllCustomers() {
//        return new ArrayList<>(customerMap.values());
//    }
//
//    @Override
//    public Customer updateCustomerById(Long id, Customer customer) {
//        if (customerMap.containsKey(id)){
//            customer.setCustomerId(id);
//            return customerMap.replace(id, customer);
//        }
//        return null;
//    }
//
//    @Override
//    public List<Customer> createBatchOfPersonnel(List<Customer> customers) {
//        return addCustomer(customers);
//    }
//
//    @Override
//    public boolean removeCustomerById(Long id) {
//        if (customerMap.containsKey(id)){
//            customerMap.remove(id);
//            return true;
//        }
//        return false;
//    }
//
//    private List<Customer> addCustomer(List<Customer> customers){
//        customers.forEach(personnel -> {
//            personnel.setCustomerId(getNextId());
//            customerMap.put(personnel.getCustomerId(), personnel);
//        });
//        return customers;
//
//    }
//
//    private Long getNextId(){
//        return nextId++;
//    }
}
