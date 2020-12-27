package org.springproject.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springproject.springproject.model.EmployeeContact;

public interface EmployeeContactRepository extends JpaRepository<EmployeeContact, Long> {
}
