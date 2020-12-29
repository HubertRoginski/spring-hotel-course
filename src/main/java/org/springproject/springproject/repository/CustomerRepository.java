package org.springproject.springproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Employee;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT c FROM customer c where lower(c.firstName) like %:keyword% or lower(c.lastName) like %:keyword%",nativeQuery = false)
    Page<Customer> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
