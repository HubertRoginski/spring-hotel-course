package org.springproject.springproject.service;

import org.springproject.springproject.model.EmployeeContact;

public interface EmployeeContactService {
    EmployeeContact addEmployeeContact(EmployeeContact employeeContact);

    Boolean deleteContactById(Long id);

    EmployeeContact updateEmployeeContactById(Long id, EmployeeContact employeeContact);
}
