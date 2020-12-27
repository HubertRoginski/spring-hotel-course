package org.springproject.springproject.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.exception.NoSuchPersonnelId;
import org.springproject.springproject.model.Customer;
import org.springproject.springproject.model.Personnel;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.PersonnelService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PersonnelController {

    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }


    @GetMapping("/personnel")
    public String getPersonnel(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser,
                               @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "5") Integer size,
                               String keyword) {
        Page<Personnel> personnelPage;
        if (Objects.nonNull(keyword)) {
            modelMap.addAttribute("personnelList", personnelService.getByKeyword(keyword, page - 1, size).getContent());
            personnelPage = personnelService.getByKeyword(keyword, page - 1, size);
            modelMap.addAttribute("personnelPage", personnelPage);
            modelMap.addAttribute("addedKeyword", keyword);
        } else {
            modelMap.addAttribute("personnelList", personnelService.getAllPersonnel(page - 1, size).getContent());
            personnelPage = personnelService.getAllPersonnel(page - 1, size);
            modelMap.addAttribute("personnelPage", personnelPage);
            modelMap.addAttribute("addedKeyword", null);
        }
        int totalPages = personnelPage.getTotalPages();
        if (totalPages > 0) {
            int pageOffset = 2;
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .filter(integer -> (integer == 1) || ((integer >= page - pageOffset) && (integer <= page + pageOffset)) || (integer == totalPages))
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }

        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        return "personnel";
    }

    @GetMapping("/personnel/{id}")
    public String getOnePersonnelById(ModelMap modelMap, @PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser) {
        modelMap.addAttribute("personnel", personnelService.getPersonnelById(id));
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        return "one-personnel";
    }

    @PostMapping("/personnel/{id}")
    public String updateUserById(@Valid @ModelAttribute("personnel") Personnel personnel, @PathVariable Long id, final Errors errors, ModelMap modelMap) {
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        if (errors.hasErrors()) {
            return "one-personnel";
        }

        try {
            personnelService.updatePersonnelById(id, personnel);
        } catch (NoSuchPersonnelId e) {
            modelMap.addAttribute("personnelExistsError", "Can't update personnel.");
            return "one-personnel";
        }

        return "redirect:/personnel/" + id;
    }

    @PostMapping("/personnel/{id}/delete")
    public String deletePersonnelById(@PathVariable(name = "id") Long id) {
        personnelService.removePersonnelById(id);
        return "redirect:/personnel";
    }

    @GetMapping("/personnel/add")
    public String showPersonnelAdd(ModelMap modelMap) {
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        modelMap.addAttribute("personnel", new Personnel());
        return "personnel-add";
    }

    @PostMapping("/personnel/add")
    public String addPersonnel(@Valid @ModelAttribute("personnel") Personnel personnel, final Errors errors, ModelMap modelMap) {
        modelMap.addAttribute("isUserLogged", true);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", true);
        if (errors.hasErrors()) {
            return "personnel-add";
        }
        Personnel newPersonnel = personnelService.createNewPersonnel(personnel);
        if (Objects.isNull(newPersonnel)) {
            modelMap.addAttribute("personnelExistsError","Can't create new personnel.");
            return "personnel-add";
        }
        return "redirect:/users";

    }
}
