package org.springproject.springproject.service;

import org.springframework.data.domain.Page;
import org.springproject.springproject.model.Employee;

import java.util.List;

public interface EmployeesService {

    Employee getEmployeeById(Long id);

    Page<Employee> getAllEmployees(Integer page, Integer size);

    Page<Employee> getByKeyword(String keyword, Integer page, Integer size);

    boolean removeEmployeeById(Long id);

    Employee createNewEmployee(Employee employee);

    List<Employee> createBatchOfEmployees(List<Employee> employees);

    Employee updateEmployeeById(Long id, Employee employee);

    List<Employee> getEmployeesBySickLeave(Boolean sickLeave, Integer page, Integer size);

    List<Employee> getEmployeesByPosition(String position, Integer page, Integer size);

    List<Employee> getEmployeesBySpecifiedParameters(Long id, String firstName, String lastName,
                                                     String jobTitle, String hireDate, Double salary,
                                                     String gender, Boolean sickLeave, Integer page,
                                                     Integer size);

    void cureAllEmployees();
}
