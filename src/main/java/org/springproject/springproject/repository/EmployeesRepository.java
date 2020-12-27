package org.springproject.springproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springproject.springproject.model.Employee;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    List<Employee> findEmployeeBySickLeaveEquals(Boolean sickLeave, Pageable pageable);

    @Query(value = "SELECT e FROM employee e where e.jobTitle = :jobTitle",nativeQuery = false)
    List<Employee> selectAllEmployeesWithJobTitleEqualTo(@Param("jobTitle") String jobTitle, Pageable pageable);

    @Query(value = "SELECT e FROM employee e where lower(e.firstName) like %:keyword% or lower(e.lastName) like %:keyword%",nativeQuery = false)
    Page<Employee> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Modifying
    @Query(value = "update employee e set e.sickLeave = false")
    void updateAllEmployeesToBeHealthy();


}
