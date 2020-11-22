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

    List<Personnel> getPersonnelBySickLeave(Boolean sickLeave);

    List<Personnel> getPersonnelByPosition(String position);

    void cureAllPersonnel();
}
