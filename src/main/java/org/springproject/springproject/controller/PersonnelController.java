package org.springproject.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.model.Personnel;
import org.springproject.springproject.service.PersonnelService;

import java.time.LocalDate;
import java.util.Collections;
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
    public ResponseEntity<?> getPersonnelById(@PathVariable Long id){
        Personnel personnel = personnelService.getPersonnelById(id);
        if (Objects.nonNull(personnel)){
            return ResponseEntity.ok(personnel);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllPersonnel(){
        return ResponseEntity.ok(personnelService.getAllPersonnel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePersonnelById(@PathVariable Long id){
        if (personnelService.removePersonnelById(id)){
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<?> createNewBatchOfPersonnel(@RequestBody List<Personnel> personnels) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personnelService.createBatchOfPersonnel(personnels));
    }

    @PostMapping()
    public ResponseEntity<?> createNewPersonnel(@RequestBody Personnel personnels) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personnelService.createNewPersonnel(personnels));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersonnelById(@PathVariable Long id, @RequestBody Personnel personnel){
        Personnel updatedPersonnel = personnelService.updatePersonnelById(id,personnel);
        if (Objects.nonNull(updatedPersonnel)){
            return ResponseEntity.ok(personnel);
        }
        return ResponseEntity.notFound().build();
    }


}
