package org.springproject.springproject.service;

import org.springframework.data.domain.Page;
import org.springproject.springproject.model.Personnel;
import org.springproject.springproject.model.User;

import java.util.List;

public interface PersonnelService {

    Personnel getPersonnelById(Long id);

    Page<Personnel> getAllPersonnel(Integer page, Integer size);

    Page<Personnel> getByKeyword(String keyword, Integer page, Integer size);

    boolean removePersonnelById(Long id);

    Personnel createNewPersonnel(Personnel personnel);

    List<Personnel> createBatchOfPersonnel(List<Personnel> personnels);

    Personnel updatePersonnelById(Long id, Personnel personnel);

    List<Personnel> getPersonnelBySickLeave(Boolean sickLeave, Integer page, Integer size);

    List<Personnel> getPersonnelByPosition(String position, Integer page, Integer size);

    List<Personnel> getPersonnelBySpecifiedParameters(Long id, String firstName, String lastName,
                                                      String position, String hireDate, Double salary,
                                                      String gender, Boolean sickLeave, Integer page,
                                                      Integer size);

    void cureAllPersonnel();
}
