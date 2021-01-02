package org.springproject.springproject.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Employee;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.CustomerService;
import org.springproject.springproject.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final UserService userService;

    public CustomerController(CustomerService customerService, UserService userService) {
        this.customerService = customerService;
        this.userService = userService;
    }

    @GetMapping("/customers")
    public String getCustomers(ModelMap modelMap, @RequestParam(required = false, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "5") Integer size, String keyword) {
        Page<Customer> customersPage;
        if (Objects.nonNull(keyword)) {
            modelMap.addAttribute("customersList", customerService.getByKeyword(keyword, page - 1, size).getContent());
            customersPage = customerService.getByKeyword(keyword, page - 1, size);
            modelMap.addAttribute("customersPage", customersPage);
            modelMap.addAttribute("addedKeyword", keyword);
        } else {
            modelMap.addAttribute("customersList", customerService.getAllCustomers(page - 1, size).getContent());
            customersPage = customerService.getAllCustomers(page - 1, size);
            modelMap.addAttribute("customersPage", customersPage);
            modelMap.addAttribute("addedKeyword", null);
        }
        int totalPages = customersPage.getTotalPages();
        if (totalPages > 0) {
            int pageOffset = 2;
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .filter(integer -> (integer == 1) || ((integer >= page - pageOffset) && (integer <= page + pageOffset)) || (integer == totalPages))
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }

        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        return "customers";
    }
    @GetMapping("/customers/{id}")
    public String customerWithId(ModelMap modelMap, @PathVariable Long id){
        modelMap.addAttribute("customer", customerService.getCustomerById(id));
        modelMap.addAttribute("customerTable", customerService.getCustomerById(id));
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        return "one-customer";
    }

    @PostMapping("/customers/{id}")
    public String updateCustomer(@Valid @ModelAttribute("customer") Customer customer, final Errors errors, @PathVariable Long id, ModelMap modelMap){
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        modelMap.addAttribute("customerTable", customerService.getCustomerById(id));
        if (errors.hasErrors()){
            return "one-customer";
        }
        Customer updatedCustomer = customerService.updateCustomerById(id, customer);
        if (Objects.isNull(updatedCustomer)) {
            modelMap.addAttribute("customerExistsError","Can't update customer, because that customer don't exist.");
            return "one-customer";
        }
        return "redirect:/customers/"+id;
    }

    @GetMapping("/customers/add")
    public String showCustomerAdd(ModelMap modelMap){
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        modelMap.addAttribute("customer", new Customer());
        List<User> usersWithoutCustomersAccountList = userService.getUsersWithoutCustomerAccount();
        modelMap.addAttribute("usersWithoutCustomersAccountList",usersWithoutCustomersAccountList);
        return "customer-add";
    }

    @PostMapping("/customers/add")
    public String addCustomer(@Valid @ModelAttribute("customer") Customer customer, final Errors errors, @RequestParam Long selectedUserId, ModelMap modelMap){
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        modelMap.addAttribute("usersWithoutCustomersAccountList",userService.getUsersWithoutCustomerAccount());
        if (errors.hasErrors()){
            return "customer-add";
        }

        customerService.createNewCustomer(customer,userService.getUserById(selectedUserId));
        return "redirect:/customers";
    }
}
