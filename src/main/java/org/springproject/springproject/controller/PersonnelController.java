package org.springproject.springproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Personnel;
import org.springproject.springproject.service.PersonnelService;

import javax.validation.Valid;

@Controller
public class PersonnelController {

    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }


    @GetMapping("/personnel")
    public String personnel(ModelMap modelMap){
        modelMap.addAttribute("personnelList", personnelService.getAllPersonnel(1,100));
        return "personnel";
    }
    @GetMapping("/personnel/{id}")
    public String personnelWithId(ModelMap modelMap, @PathVariable Long id){
        modelMap.addAttribute("personnel", personnelService.getPersonnelById(id));
        return "one-personnel";
    }

    @PostMapping("/personnel/{id}")
    public String updatePersonnel(@Valid @ModelAttribute("personnel") Personnel personnel, @PathVariable Long id, final Errors errors){
        if (errors.hasErrors()){
            return "one-personnel";
        }
        personnelService.updatePersonnelById(id,personnel);
        return "redirect:/personnel/"+id;
    }

    @GetMapping("/personnel/add")
    public String showPersonnelAdd(ModelMap modelMap){
        modelMap.addAttribute("personnel", new Personnel());
        return "personnel-add";
    }

    @PostMapping("/personnel/add")
    public String addPersonnel(@Valid @ModelAttribute("personnel") Personnel personnel, final Errors errors){
        if (errors.hasErrors()){
            return "personnel-add";
        }
        if (personnel.getFirstName().equals("Antonio")){
            throw new RuntimeException("Blad!");
        }
        personnelService.createNewPersonnel(personnel);
        return "redirect:/personnel";
    }
}
