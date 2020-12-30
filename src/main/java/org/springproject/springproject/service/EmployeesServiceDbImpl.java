package org.springproject.springproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springproject.springproject.exception.NoSuchEmployeeId;
import org.springproject.springproject.exception.WrongPageException;
import org.springproject.springproject.model.Employee;
import org.springproject.springproject.model.EmployeeContact;
import org.springproject.springproject.repository.EmployeesRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
@Scope("prototype")
@Slf4j
public class EmployeesServiceDbImpl implements EmployeesService {


    private final EmployeesRepository employeesRepository;
    private final EmployeeContactService employeeContactService;


    public EmployeesServiceDbImpl(EmployeesRepository employeesRepository, EmployeeContactService employeeContactService) {
        this.employeesRepository = employeesRepository;
        this.employeeContactService = employeeContactService;
    }

    @Override
    public Employee getEmployeeById(Long id) throws NoSuchEmployeeId {
        Employee employee = employeesRepository.findById(id).orElse(null);
        if (Objects.isNull(employee)){
            throw new NoSuchEmployeeId(id.toString());
        }
        return employee;
    }

    @Override
    public Page<Employee> getAllEmployees(Integer page, Integer size) throws WrongPageException {
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
        return employeesRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> getByKeyword(String keyword, Integer page, Integer size) {
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
        return employeesRepository.findByKeyword(keyword,pageable);
    }


    @Override
    public boolean removeEmployeeById(Long id) throws NoSuchEmployeeId {
        if (employeesRepository.existsById(id)) {
            Long employeeContactId = getEmployeeById(id).getEmployeeContact().getId();
            employeesRepository.deleteById(id);
            employeeContactService.deleteContactById(employeeContactId);
            return true;
        }
        throw new NoSuchEmployeeId(id.toString());
    }

    @Override
    public Employee createNewEmployee(Employee employee) {
        EmployeeContact employeeContact = employeeContactService.addEmployeeContact(employee.getEmployeeContact());
        employee.setEmployeeContact(employeeContact);
        return employeesRepository.save(employee);
    }

    @Override
    public List<Employee> createBatchOfEmployees(List<Employee> employees) {
        employees.forEach((employee) -> {
            log.info("Created EMPLOYEE: "+employee.toString());
                EmployeeContact employeeContact = employeeContactService.addEmployeeContact(employee.getEmployeeContact());
                employee.setEmployeeContact(employeeContact);
                employeesRepository.save(employee);
        });
        return employees;
    }

    @Override
    public Employee updateEmployeeById(Long id, Employee employee) throws NoSuchEmployeeId {
        if (employeesRepository.existsById(id)) {
            employee.setId(id);
            if (!getEmployeeById(id).getEmployeeContact().equals(employee.getEmployeeContact())) {
                EmployeeContact updatedEmployeeContact = employeeContactService.updateEmployeeContactById(getEmployeeById(id).getEmployeeContact().getId(), employee.getEmployeeContact());
                employee.setEmployeeContact(updatedEmployeeContact);
            }
            return employeesRepository.save(employee);
        }
        throw new NoSuchEmployeeId(id.toString());
    }

    @Override
    public void cureAllEmployees() {
        employeesRepository.updateAllEmployeesToBeHealthy();
    }

    @Override
    public List<Employee> getEmployeesBySickLeave(Boolean sickLeave, Integer page, Integer size) throws WrongPageException {
        Pageable pageable = createPagination(page, size);
        return employeesRepository.findEmployeeBySickLeaveEquals(sickLeave, pageable);

    }

    @Override
    public List<Employee> getEmployeesByPosition(String position, Integer page, Integer size) throws WrongPageException {
        Pageable pageable = createPagination(page, size);
        return employeesRepository.selectAllEmployeesWithJobTitleEqualTo(position, pageable);

    }

    @Override
    public List<Employee> getEmployeesBySpecifiedParameters(Long id, String firstName, String lastName, String position, String hireDate, Double salary, String gender, Boolean sickLeave, Integer page, Integer size) {
        Pageable pageable = createPagination(page, size);

        LocalDate localDate = null;
        if (Objects.nonNull(hireDate)) {
            localDate = LocalDate.parse(hireDate);
        }

        Employee employee = Employee.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .jobTitle(position)
                .hireDate(localDate)
                .salary(salary)
                .gender(gender)
                .sickLeave(sickLeave)
                .build();

        return employeesRepository.findAll(Example.of(employee), pageable).getContent();

    }

    private Pageable createPagination(Integer page, Integer size) throws WrongPageException {
        if (!Objects.nonNull(page)) {
            page = 1;
        }
        if (!Objects.nonNull(size)) {
            size = 5;
        }
        if (page < 1) {
            throw new WrongPageException("Page number can't be less than 1");
        }
        return PageRequest.of(page - 1, size);
    }


}
