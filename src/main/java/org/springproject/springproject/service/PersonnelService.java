package org.springproject.springproject.service;

import org.springproject.springproject.model.Personnel;

import java.util.List;

public interface PersonnelService {

    Personnel getPersonnelById(Long id);

    List<Personnel> getAllPersonnel(Integer page, Integer size);

    boolean removePersonnelById(Long id);

    Personnel createNewPersonnel(Personnel personnel);

    List<Personnel> createBatchOfPersonnel(List<Personnel> personnels);

    Personnel updatePersonnelById(Long id, Personnel personnel);

    List<Personnel> getPersonnelBySickLeave(Boolean sickLeave, Integer page, Integer size);

    List<Personnel> getPersonnelByPosition(String position, Integer page, Integer size);

    List<Personnel> getPersonnelBySpecifiedParameters(Long id, String firstName, String lastName,
                                                      String position, String hireDate, Double salary,
                                                      Boolean sickLeave , Integer page, Integer size);

    void cureAllPersonnel();
}
