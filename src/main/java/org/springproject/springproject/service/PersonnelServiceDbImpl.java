package org.springproject.springproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springproject.springproject.exception.WrongPageException;
import org.springproject.springproject.model.Personnel;
import org.springproject.springproject.repository.PersonnelRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Profile("!old")
@Service
@Scope("prototype")
@Slf4j
public class PersonnelServiceDbImpl implements PersonnelService {


    private final PersonnelRepository personnelRepository;


    public PersonnelServiceDbImpl(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    @Override
    public Personnel getPersonnelById(Long id) {
        return personnelRepository.findById(id).orElse(null);
    }

    @Override
    public List<Personnel> getAllPersonnel(Integer page, Integer size) throws WrongPageException {
        if (!Objects.nonNull(page)) {
            page = 1;
        }
        if (!Objects.nonNull(size)) {
            size = 5;
        }
        if (page < 1) {
            throw new WrongPageException("Strona nie może być mniejsza niż 1");
        }
        Sort sort = Sort.by("salary").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return personnelRepository.findAll(pageable).getContent();
    }


    @Override
    public boolean removePersonnelById(Long id) {
        personnelRepository.deleteById(id);
        return true;
    }

    @Override
    public Personnel createNewPersonnel(Personnel personnel) {
        log.info("Tworze nowy personel");
        return personnelRepository.save(personnel);
    }

    @Override
    public List<Personnel> createBatchOfPersonnel(List<Personnel> personnels) {
        return personnelRepository.saveAll(personnels);
    }

    @Override
    public Personnel updatePersonnelById(Long id, Personnel personnel) {
        if (personnelRepository.existsById(id)) {
            personnel.setId(id);
            return personnelRepository.save(personnel);
        }
        return null;
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

        return personnelRepository.selectAllPersonnelWithPositionEqualTo(position, pageable);

    }

    @Override
    public List<Personnel> getPersonnelBySpecifiedParameters(Long id, String firstName, String lastName, String position, String hireDate, Double salary, Boolean sickLeave, Integer page, Integer size) {
        Pageable pageable = createPagination(page, size);

        LocalDate localDate = null;
        if (Objects.nonNull(hireDate)) {
            localDate = LocalDate.parse(hireDate);
        }

        Personnel personnel = Personnel.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .position(position)
                .hireDate(localDate)
                .salary(salary)
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
