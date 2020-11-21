package org.springproject.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Personnel;
import org.springproject.springproject.service.CustomerService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/hotel/customer")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createNewCustomer(@RequestBody Customer customer){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createNewCustomer(customer));
    }

    @PostMapping("/batch")
    public ResponseEntity<?> createNewBatchOfPersonnel(@RequestBody List<Customer> customers) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createBatchOfPersonnel(customers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        Customer customer = customerService.getCustomerById(id);
        if (Objects.nonNull(customer)){
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping()
    public ResponseEntity<?> getCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomerById(@PathVariable Long id, @RequestBody Customer customer){
        Customer updatedCustomer = customerService.updateCustomerById(id, customer);
        if (Objects.nonNull(updatedCustomer)){
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePersonnelById(@PathVariable Long id){
        if (customerService.removeCustomerById(id)){
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
