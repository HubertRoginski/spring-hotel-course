package org.springproject.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springproject.springproject.model.Customer;

public interface CustomerRepository  extends JpaRepository<Customer, Long> {
}
