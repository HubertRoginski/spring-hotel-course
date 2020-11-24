package org.springproject.springproject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springproject.springproject.config.HotelPersonnelConfig;
import org.springproject.springproject.model.Personnel;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("old")
@Service
@Scope("singleton")
public class PersonnelServiceInMemoryImpl implements PersonnelService {
    @Override
    public Personnel getPersonnelById(Long id) {
        return null;
    }

    @Override
    public List<Personnel> getAllPersonnel(Integer page, Integer size) {
        return null;
    }

    @Override
    public boolean removePersonnelById(Long id) {
        return false;
    }

    @Override
    public Personnel createNewPersonnel(Personnel personnel) {
        return null;
    }

    @Override
    public List<Personnel> createBatchOfPersonnel(List<Personnel> personnels) {
        return null;
    }

    @Override
    public Personnel updatePersonnelById(Long id, Personnel personnel) {
        return null;
    }

    @Override
    public List<Personnel> getPersonnelBySickLeave(Boolean sickLeave) {
        return null;
    }

    @Override
    public List<Personnel> getPersonnelByPosition(String position) {
        return null;
    }

    @Override
    public void cureAllPersonnel() {

    }

//    private final Map<Long, Personnel> personnelMap = new HashMap<>();
//    private Long nextId = 1L;
//    private final HotelPersonnelConfig hotelPersonnelConfig;
//
//    @Value("${hotel.personnel.owner.name:Name}")
//    private String ownerName;
//
//    @Value("${hotel.personnel.owner.last-name:lastName}")
//    private String lastName;
//
//    @Value("${hotel.personnel.owner.hire-date:2000-07-04}")
//    private String hireDate;
//
//    @Value("${hotel.personnel.owner.position:position}")
//    private String position;
//
//    @Value("${hotel.personnel.owner.salary:200.0}")
//    private Double salary;
//
//    @Value("${hotel.personnel.owner.sick-leave:false}")
//    private Boolean sickLeave;
//
//    public PersonnelServiceInMemoryImpl(HotelPersonnelConfig hotelPersonnelConfig) {
//        this.hotelPersonnelConfig = hotelPersonnelConfig;
//    }
//
//    @PostConstruct
//    public void init() {
//        personnelMap.put(nextId, Personnel.builder()
//                .id(getNextId())
//                .firstName(ownerName)
//                .lastName(lastName)
//                .hireDate(LocalDate.parse(hireDate))
//                .position(position)
//                .salary(salary)
//                .sickLeave(sickLeave)
//                .build());
//        personnelMap.put(nextId, Personnel.builder()
//                .id(getNextId())
//                .firstName(hotelPersonnelConfig.getNames().get(2))
//                .lastName(lastName)
//                .hireDate(LocalDate.parse(hireDate))
//                .position(position)
//                .salary(salary)
//                .sickLeave(sickLeave)
//                .build());
//        personnelMap.put(nextId, Personnel.builder()
//                .id(getNextId())
//                .firstName(hotelPersonnelConfig.getNames().get(0))
//                .lastName(hotelPersonnelConfig.getPeople().get(hotelPersonnelConfig.getNames().get(0)))
//                .hireDate(LocalDate.parse(hireDate))
//                .position(position)
//                .salary(salary)
//                .sickLeave(sickLeave)
//                .build());
//    }
//
//    @Override
//    public Personnel getPersonnelById(Long id) {
//        return personnelMap.getOrDefault(id, null);
//    }
//
//    @Override
//    public List<Personnel> getAllPersonnel(Integer page, Integer size) {
//        return null;
//    }
//
//
//
//    @Override
//    public boolean removePersonnelById(Long id) {
//        if (personnelMap.containsKey(id)){
//            personnelMap.remove(id);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public Personnel createNewPersonnel(Personnel personnel) {
//        personnel.setId(getNextId());
//        personnelMap.put(personnel.getId(), personnel);
//        return personnel;
//    }
//
//    @Override
//    public List<Personnel> createBatchOfPersonnel(List<Personnel> personnels) {
//        return addPersonnel(personnels);
//    }
//
//    @Override
//    public Personnel updatePersonnelById(Long id, Personnel personnel) {
//        if (personnelMap.containsKey(id)){
//            personnel.setId(id);
//            return personnelMap.replace(id, personnel);
//        }
//        return null;
//    }
//
//    @Override
//    public List<Personnel> getPersonnelBySickLeave(Boolean sickLeave) {
//        return null;
//    }
//
//    @Override
//    public List<Personnel> getPersonnelByPosition(String position) {
//        return null;
//    }
//
//    @Override
//    public void cureAllPersonnel() {
//
//    }
//
//    private List<Personnel> addPersonnel(List<Personnel> personnels){
//        personnels.forEach(personnel -> {
//            personnel.setId(getNextId());
//            personnelMap.put(personnel.getId(), personnel);
//        });
//        return personnels;
//
//    }
//
//    private Long getNextId(){
//        return nextId++;
//    }
}
