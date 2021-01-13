package org.springproject.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.exception.NoSuchEmployeeId;
import org.springproject.springproject.model.Employee;
import org.springproject.springproject.service.AuthenticationUserService;
import org.springproject.springproject.service.EmployeesService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
public class EmployeesController {

    private final EmployeesService employeesService;
    private final AuthenticationUserService authenticationUserService;

    public EmployeesController(EmployeesService employeesService, AuthenticationUserService authenticationUserService) {
        this.employeesService = employeesService;
        this.authenticationUserService = authenticationUserService;
    }


    @GetMapping("/employees")
    public String getEmployees(ModelMap modelMap, @RequestParam(required = false, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "5") Integer size, String keyword) {
        Page<Employee> employeesPage;
        if (Objects.nonNull(keyword)) {
            modelMap.addAttribute("employeesList", employeesService.getByKeyword(keyword, page - 1, size).getContent());
            employeesPage = employeesService.getByKeyword(keyword, page - 1, size);
            modelMap.addAttribute("employeesPage", employeesPage);
            modelMap.addAttribute("addedKeyword", keyword);
        } else {
            modelMap.addAttribute("employeesList", employeesService.getAllEmployees(page - 1, size).getContent());
            employeesPage = employeesService.getAllEmployees(page - 1, size);
            modelMap.addAttribute("employeesPage", employeesPage);
            modelMap.addAttribute("addedKeyword", null);
        }
        int totalPages = employeesPage.getTotalPages();
        if (totalPages > 0) {
            int pageOffset = 2;
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .filter(integer -> (integer == 1) || ((integer >= page - pageOffset) && (integer <= page + pageOffset)) || (integer == totalPages))
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }

        authenticationUserService.authenticatedUserAuthorizedAsAdminOrManager(modelMap);
        return "employees";
    }

    @GetMapping("/employees/{id}")
    public String getOneEmployeeById(ModelMap modelMap, @PathVariable Long id) {
        modelMap.addAttribute("employee", employeesService.getEmployeeById(id));
        modelMap.addAttribute("employeeTable", employeesService.getEmployeeById(id));
        authenticationUserService.authenticatedUserAuthorizedAsAdminOrManager(modelMap);
        return "one-employee";
    }

    @PostMapping("/employees/{id}")
    public String updateEmployeeById(@Valid @ModelAttribute("employee") Employee employee, final Errors errors, @PathVariable Long id, ModelMap modelMap) {
        authenticationUserService.authenticatedUserAuthorizedAsAdminOrManager(modelMap);
        modelMap.addAttribute("employeeTable", employeesService.getEmployeeById(id));
        if (errors.hasErrors()) {
            return "one-employee";
        }

        try {
            employeesService.updateEmployeeById(id, employee);
        } catch (NoSuchEmployeeId e) {
            modelMap.addAttribute("employeesExistsError", "Can't update employee.");
            return "one-employee";
        }

        return "redirect:/employees/" + id;
    }

    @GetMapping("/employees/{id}/contact")
    public String getOneEmployeeContact(ModelMap modelMap, @PathVariable Long id) {
        modelMap.addAttribute("employee", employeesService.getEmployeeById(id));
        modelMap.addAttribute("employeeTable", employeesService.getEmployeeById(id));
        authenticationUserService.authenticatedUserAuthorizedAsAdminOrManager(modelMap);
        return "one-employee-contact";
    }

    @PostMapping("/employees/{id}/contact")
    public String updateEmployeeContactById(@Valid @ModelAttribute("employee") Employee employee, final Errors errors, @PathVariable Long id, ModelMap modelMap) {
        authenticationUserService.authenticatedUserAuthorizedAsAdminOrManager(modelMap);
        modelMap.addAttribute("employeeTable", employeesService.getEmployeeById(id));
        if (errors.hasErrors()) {
            return "one-employee-contact";
        }
        try {
            employeesService.updateEmployeeById(id, employee);
        } catch (NoSuchEmployeeId e) {
            modelMap.addAttribute("employeesExistsError", "Can't update employee contact.");
            return "one-employee-contact";
        }

        return "redirect:/employees/" + id + "/contact";
    }

    @PostMapping("/employees/{id}/delete")
    public String deleteEmployeeById(@PathVariable(name = "id") Long id) {
        employeesService.removeEmployeeById(id);
        return "redirect:/employees";
    }

    @GetMapping("/employees/add")
    public String showEmployeeAdd(ModelMap modelMap) {
        authenticationUserService.authenticatedUserAuthorizedAsAdminOrManager(modelMap);
        modelMap.addAttribute("employee", new Employee());
        return "employee-add";
    }

    @PostMapping("/employees/add")
    public String addEmployee(@Valid @ModelAttribute("employee") Employee employee, final Errors errors, ModelMap modelMap) {
        authenticationUserService.authenticatedUserAuthorizedAsAdminOrManager(modelMap);
        if (errors.hasErrors()) {
            return "employee-add";
        }
        employeesService.createNewEmployee(employee);
        return "redirect:/employees";
    }
}
