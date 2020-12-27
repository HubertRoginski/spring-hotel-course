package org.springproject.springproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springproject.springproject.exception.NoSuchPersonnelId;
import org.springproject.springproject.exception.WrongPageException;
import org.springproject.springproject.model.EmployeeContact;
import org.springproject.springproject.model.Personnel;
import org.springproject.springproject.repository.PersonnelRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

//@Profile("!old")
@Service
@Scope("prototype")
@Slf4j
public class PersonnelServiceDbImpl implements PersonnelService {


    private final PersonnelRepository personnelRepository;
    private final EmployeeContactService employeeContactService;


    public PersonnelServiceDbImpl(PersonnelRepository personnelRepository, EmployeeContactService employeeContactService) {
        this.personnelRepository = personnelRepository;
        this.employeeContactService = employeeContactService;
    }

    @Override
    public Personnel getPersonnelById(Long id) throws NoSuchPersonnelId{
        Personnel personnel = personnelRepository.findById(id).orElse(null);
        if (Objects.isNull(personnel)){
            throw new NoSuchPersonnelId(id.toString());
        }
        return personnel;
    }

    @Override
    public Page<Personnel> getAllPersonnel(Integer page, Integer size) throws WrongPageException {
        if (!Objects.nonNull(page)) {
            page = 1;
        }
        if (!Objects.nonNull(size)) {
            size = 5;
        }
        if (page < 0) {
            throw new WrongPageException("Page number can't be less than 1");
        }
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return personnelRepository.findAll(pageable);
    }

    @Override
    public Page<Personnel> getByKeyword(String keyword, Integer page, Integer size) {
        if (!Objects.nonNull(page)) {
            page = 1;
        }
        if (!Objects.nonNull(size)) {
            size = 5;
        }
        if (page < 0) {
            throw new WrongPageException("Page number can't be less than 1");
        }
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return personnelRepository.findByKeyword(keyword,pageable);
    }


    @Override
    public boolean removePersonnelById(Long id) throws NoSuchPersonnelId {
        if (personnelRepository.existsById(id)) {
            personnelRepository.deleteById(id);
            return true;
        }
        throw new NoSuchPersonnelId(id.toString());
    }

    @Override
    public Personnel createNewPersonnel(Personnel personnel) {
        EmployeeContact employeeContact = employeeContactService.addEmployeeContact(personnel.getEmployeeContact());
        personnel.setEmployeeContact(employeeContact);
        return personnelRepository.save(personnel);
    }

    @Override
    public List<Personnel> createBatchOfPersonnel(List<Personnel> personnels) {
        personnels.forEach((personnel) -> {
                EmployeeContact employeeContact = employeeContactService.addEmployeeContact(personnel.getEmployeeContact());
                personnel.setEmployeeContact(employeeContact);
                personnelRepository.save(personnel);
        });
        return personnels;
    }

    @Override
    public Personnel updatePersonnelById(Long id, Personnel personnel) throws NoSuchPersonnelId {
        if (personnelRepository.existsById(id)) {
            personnel.setId(id);
            return personnelRepository.save(personnel);
        }
        throw new NoSuchPersonnelId(id.toString());
    }

    @Override
    public void cureAllPersonnel() {
        personnelRepository.updateAllPersonnelToBeHealthy();
    }

    @Override
    public List<Personnel> getPersonnelBySickLeave(Boolean sickLeave, Integer page, Integer size) throws WrongPageException {
        Pageable pageable = createPagination(page, size);
        return personnelRepository.findPersonnelsBySickLeaveEquals(sickLeave, pageable);

    }

    @Override
    public List<Personnel> getPersonnelByPosition(String position, Integer page, Integer size) throws WrongPageException {
        Pageable pageable = createPagination(page, size);
        return personnelRepository.selectAllPersonnelWithJobTitleEqualTo(position, pageable);

    }

    @Override
    public List<Personnel> getPersonnelBySpecifiedParameters(Long id, String firstName, String lastName, String position, String hireDate, Double salary, String gender, Boolean sickLeave, Integer page, Integer size) {
        Pageable pageable = createPagination(page, size);

        LocalDate localDate = null;
        if (Objects.nonNull(hireDate)) {
            localDate = LocalDate.parse(hireDate);
        }

        Personnel personnel = Personnel.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .jobTitle(position)
                .hireDate(localDate)
                .salary(salary)
                .gender(gender)
                .sickLeave(sickLeave)
                .build();

        return personnelRepository.findAll(Example.of(personnel), pageable).getContent();

    }

    private Pageable createPagination(Integer page, Integer size) throws WrongPageException {
        if (!Objects.nonNull(page)) {
            page = 1;
        }
        if (!Objects.nonNull(size)) {
            size = 5;
        }
        if (page < 1) {
            throw new WrongPageException("Page number can't be less than 1");
        }
        return PageRequest.of(page - 1, size);
    }


}
