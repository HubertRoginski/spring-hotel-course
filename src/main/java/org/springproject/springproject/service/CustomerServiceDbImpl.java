package org.springproject.springproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springproject.springproject.exception.WrongPageException;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Reservation;
import org.springproject.springproject.model.User;
import org.springproject.springproject.repository.CustomerRepository;

import java.util.List;
import java.util.Objects;


@Service
@Scope("prototype")
@Slf4j
public class CustomerServiceDbImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final UserService userService;

    public CustomerServiceDbImpl(CustomerRepository customerRepository, UserService userService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
    }

    @Override
    public Customer createNewCustomer(Customer customer, User user) {
        Customer savedCustomer = customerRepository.save(customer);
        user.setCustomer(savedCustomer);
        userService.updateUserById(user.getId(),user);
        return savedCustomer;
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.getOne(id);
    }

    @Override
    public Page<Customer> getAllCustomers(Integer page, Integer size) throws WrongPageException{
        if (!Objects.nonNull(page)) {
            page = 1;
        }
        if (!Objects.nonNull(size)) {
            size = 5;
        }
        if (page < 0) {
            throw new WrongPageException("Page number can't be less than 1");
        }
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerRepository.findAll(pageable);
    }

    @Override
    public List<Customer> getAllCustomersList() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerByReservation(Reservation reservation) {
        if (Objects.nonNull(reservation)) {
            return customerRepository.findCustomerByReservationsContains(reservation);
        }
        return null;
    }

    @Override
    public Page<Customer> getByKeyword(String keyword, Integer page, Integer size) throws WrongPageException {
        if (!Objects.nonNull(page)) {
            page = 1;
        }
        if (!Objects.nonNull(size)) {
            size = 5;
        }
        if (page < 0) {
            throw new WrongPageException("Page number can't be less than 1");
        }
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerRepository.findByKeyword(keyword,pageable);
    }


    @Override
    public Customer updateCustomerById(Long id, Customer customer) {
        if (customerRepository.existsById(id)){
            customer.setId(id);
            return customerRepository.save(customer);
        }
        return null;
    }


    @Override
    public boolean removeCustomerById(Long id) {
        customerRepository.deleteById(id);
        return true;
    }
}
