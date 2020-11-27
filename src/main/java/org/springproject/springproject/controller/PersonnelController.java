package org.springproject.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.exception.WrongPageException;
import org.springproject.springproject.model.Error;
import org.springproject.springproject.model.Personnel;
import org.springproject.springproject.service.PersonnelService;


import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/hotel/personnel")
@Slf4j
public class PersonnelController {

    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personnel> getPersonnelById(@PathVariable Long id) {
        Personnel personnel = personnelService.getPersonnelById(id);
        return ResponseEntity.ok(personnel);
    }

    @GetMapping
    public ResponseEntity<List<Personnel>> getPersonnel(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(personnelService.getAllPersonnel(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePersonnelById(@PathVariable Long id) {
        personnelService.removePersonnelById(id);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<?> createNewBatchOfPersonnel(@RequestBody List<Personnel> personnels) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personnelService.createBatchOfPersonnel(personnels));
    }

    @PostMapping()
    public ResponseEntity<?> createNewPersonnel(@Valid @RequestBody Personnel personnels) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personnelService.createNewPersonnel(personnels));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersonnelById(@PathVariable Long id, @RequestBody Personnel personnel) {
        personnelService.updatePersonnelById(id, personnel);
        return ResponseEntity.ok(personnel);
    }

    @GetMapping("/sick/{sickLeave}")
    public ResponseEntity<?> getPersonnelBySickLeave(@PathVariable Boolean sickLeave, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(personnelService.getPersonnelBySickLeave(sickLeave, page, size));
    }

    @GetMapping("/position")
    public ResponseEntity<?> getPersonnelByPosition(@RequestParam String position, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(personnelService.getPersonnelByPosition(position, page, size));
    }

    @GetMapping("/cure")
    public void cureALlPersonnel() {
        personnelService.cureAllPersonnel();
    }

    @GetMapping("/various-parameters")
    public ResponseEntity<List<Personnel>> getPersonnelWithParameters(
            @RequestParam(required = false) Long id, @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName, @RequestParam(required = false) String position,
            @RequestParam(required = false) String hireDate, @RequestParam(required = false) Double salary,
            @RequestParam(required = false) Boolean sickLeave, @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(personnelService.getPersonnelBySpecifiedParameters(id, firstName, lastName, position, hireDate, salary, sickLeave, page, size));
    }
}
