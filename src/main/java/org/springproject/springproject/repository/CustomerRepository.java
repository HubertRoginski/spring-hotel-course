package org.springproject.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springproject.springproject.model.Customer;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {
}
