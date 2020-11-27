package org.springproject.springproject.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springproject.springproject.model.Personnel;

import java.util.List;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {

    List<Personnel> findPersonnelsBySickLeaveEquals(Boolean sickLeave,Pageable pageable);




    @Query(value = "SELECT p FROM personnel p where p.position = :position",nativeQuery = false)
    List<Personnel> selectAllPersonnelWithPositionEqualTo(@Param("position") String position, Pageable pageable);

    @Modifying
    @Query(value = "update personnel p set p.sickLeave = false")
    @Transactional
    void updateAllPersonnelToBeHealthy();
}
