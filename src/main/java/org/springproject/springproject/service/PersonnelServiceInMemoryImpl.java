package org.springproject.springproject.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springproject.springproject.model.Personnel;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("singleton")
public class PersonnelServiceInMemoryImpl implements PersonnelService {

    private final Map<Long, Personnel> personnelMap = new HashMap<>();
    private Long nextId = 1L;

    @PostConstruct
    public void init() {
        personnelMap.put(nextId, Personnel.builder()
                .id(getNextId())
                .firstName("Wlasciciel")
                .lastName("Hotelu")
                .hireDate(LocalDate.parse("1800-01-01"))
                .position("Wlasciciel")
                .salary(0.0)
                .sickLeave(false)
                .build());
    }

    @Override
    public Personnel getPersonnelById(Long id) {
        return personnelMap.getOrDefault(id, null);
    }

    @Override
    public List<Personnel> getAllPersonnel() {
        return new ArrayList<>(personnelMap.values());
    }

    @Override
    public boolean removePersonnelById(Long id) {
        if (personnelMap.containsKey(id)){
            personnelMap.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Personnel createNewPersonnel(Personnel personnel) {
        personnel.setId(getNextId());
        personnelMap.put(personnel.getId(), personnel);
        return personnel;
    }

    @Override
    public List<Personnel> createBatchOfPersonnel(List<Personnel> personnels) {
        return addPersonnel(personnels);
    }

    @Override
    public Personnel updatePersonnelById(Long id, Personnel personnel) {
        if (personnelMap.containsKey(id)){
            personnel.setId(id);
            return personnelMap.replace(id, personnel);
        }
        return null;
    }

    private List<Personnel> addPersonnel(List<Personnel> personnels){
        personnels.forEach(personnel -> {
            personnel.setId(getNextId());
            personnelMap.put(personnel.getId(), personnel);
        });
        return personnels;

    }

    private Long getNextId(){
        return nextId++;
    }
}
