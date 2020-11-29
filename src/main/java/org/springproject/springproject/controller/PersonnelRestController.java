package org.springproject.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.exception.WrongPageException;
import org.springproject.springproject.model.Error;
import org.springproject.springproject.model.Personnel;
import org.springproject.springproject.service.PersonnelService;


import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@RestController
@Validated
@RequestMapping(path = "/api/hotel/personnel")
@Slf4j
public class PersonnelRestController {

    private final PersonnelService personnelService;
    private final String DATE_REGEX = "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
            + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";

    public PersonnelRestController(PersonnelService personnelService) {
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
            @Valid @Pattern(regexp = DATE_REGEX,message = "Wrong date format. Valid format is yyyy-mm-dd")
            @RequestParam(required = false) String hireDate,
            @RequestParam(required = false) Double salary,
            @RequestParam(required = false) Boolean sickLeave, @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(personnelService.getPersonnelBySpecifiedParameters(id, firstName, lastName, position, hireDate, salary, sickLeave, page, size));
    }
}
