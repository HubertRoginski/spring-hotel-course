package org.springproject.springproject.controllerRest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springproject.springproject.model.Employee;
import org.springproject.springproject.service.EmployeesService;


import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/api/hotel/employees")
@Slf4j
public class EmployeesRestController {

    private final EmployeesService employeesService;
    private final String DATE_REGEX = "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
            + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";

    public EmployeesRestController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeesService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getEmployees(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(employeesService.getAllEmployees(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeEmployeeById(@PathVariable Long id) {
        employeesService.removeEmployeeById(id);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<?> createNewBatchOfEmployees(@RequestBody List<Employee> employees) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeesService.createBatchOfEmployees(employees));
    }

    @PostMapping()
    public ResponseEntity<?> createNewEmployee(@Valid @RequestBody Employee employees) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeesService.createNewEmployee(employees));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployeeById(@PathVariable Long id, @RequestBody Employee employee) {
        employeesService.updateEmployeeById(id, employee);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/sick/{sickLeave}")
    public ResponseEntity<?> getEmployeesBySickLeave(@PathVariable Boolean sickLeave, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(employeesService.getEmployeesBySickLeave(sickLeave, page, size));
    }

    @GetMapping("/position")
    public ResponseEntity<?> getEmployeesByPosition(@RequestParam String position, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(employeesService.getEmployeesByPosition(position, page, size));
    }

    @GetMapping("/cure")
    public void cureALlEmployees() {
        employeesService.cureAllEmployees();
    }


    @GetMapping("/various-parameters")
    public ResponseEntity<List<Employee>> getEmployeesWithParameters(
            @RequestParam(required = false) Long id, @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName, @RequestParam(required = false) String position,
            @Valid @Pattern(regexp = DATE_REGEX,message = "Wrong date format. Valid format is yyyy-mm-dd")
            @RequestParam(required = false) String hireDate,
            @RequestParam(required = false) Double salary,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Boolean sickLeave, @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(employeesService.getEmployeesBySpecifiedParameters(id, firstName, lastName, position, hireDate, salary, gender, sickLeave, page, size));
    }
}
