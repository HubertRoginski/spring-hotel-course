package org.springproject.springproject.service;


import org.springframework.stereotype.Service;
import org.springproject.springproject.model.EmployeeContact;
import org.springproject.springproject.repository.EmployeeContactRepository;

@Service
public class EmployeeContactServiceImpl implements EmployeeContactService {

    private final EmployeeContactRepository employeeContactRepository;

    public EmployeeContactServiceImpl(EmployeeContactRepository employeeContactRepository) {
        this.employeeContactRepository = employeeContactRepository;
    }

    @Override
    public EmployeeContact addEmployeeContact(EmployeeContact employeeContact) {
        return employeeContactRepository.save(employeeContact);
    }
}
