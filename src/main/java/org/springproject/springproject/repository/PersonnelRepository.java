package org.springproject.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springproject.springproject.model.Personnel;

public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
}
