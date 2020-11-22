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
    public ResponseEntity<?> getPersonnelById(@PathVariable Long id){
        Personnel personnel = personnelService.getPersonnelById(id);
        if (Objects.nonNull(personnel)){
            return ResponseEntity.ok(personnel);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Personnel>> getPersonnel(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size){
        return ResponseEntity.ok(personnelService.getAllPersonnel(page,size));
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
    public ResponseEntity<?> createNewPersonnel(@Valid @RequestBody Personnel personnels) {
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

    @GetMapping("/sick/{sickLeave}")
    public ResponseEntity<?> getPersonnelBySickLeave(@PathVariable Boolean sickLeave){
        return ResponseEntity.ok(personnelService.getPersonnelBySickLeave(sickLeave));
    }

    @GetMapping("/position")
    public ResponseEntity<?> getPersonnelByPosition(@RequestParam String position){
        return ResponseEntity.ok(personnelService.getPersonnelByPosition(position));
    }

    @GetMapping("/cure")
    public void cureALlPersonnel(){
        personnelService.cureAllPersonnel();
    }
}
