package org.springproject.springproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.service.CustomerService;

import javax.validation.Valid;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String customer(ModelMap modelMap){
        modelMap.addAttribute("customerList", customerService.getAllCustomers());
        return "customers";
    }
    @GetMapping("/customers/{id}")
    public String customerWithId(ModelMap modelMap, @PathVariable Long id){
        modelMap.addAttribute("customer", customerService.getCustomerById(id));
        return "one-customer";
    }

    @PostMapping("/customers/{id}")
    public String updateCustomer(@Valid @ModelAttribute("customer") Customer customer, @PathVariable Long id, final Errors errors){
        if (errors.hasErrors()){
            return "one-customer";
        }
        customerService.updateCustomerById(id,customer);
        return "redirect:/customers/"+id;
    }

    @GetMapping("/customers/add")
    public String showCustomerAdd(ModelMap modelMap){
        modelMap.addAttribute("customer", new Customer());
        return "customer-add";
    }

    @PostMapping("/customers/add")
    public String addCustomer(@Valid @ModelAttribute("customer") Customer customer, final Errors errors){
        if (errors.hasErrors()){
            return "customer-add";
        }
        if (customer.getFirstName().equals("Antonio")){
            throw new RuntimeException("Blad!");
        }
        customerService.createNewCustomer(customer);
        return "redirect:/";
    }
}
