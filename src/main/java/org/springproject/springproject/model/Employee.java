package org.springproject.springproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "employee")
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    @Pattern(regexp = "[\\p{IsAlphabetic}-'. ]+", message = "First name can only consist of letters, spaces, apostrophes, dashes and dots")
    @NotNull(message = "First name cannot be empty")
    private String firstName;

    @Pattern(regexp = "[\\p{IsAlphabetic}-'. ]+", message = "Last name can only consist of letters, spaces, apostrophes, dashes and dots")
    @NotNull(message = "Last name cannot be empty")
    private String lastName;

    @Pattern(regexp = "[\\p{IsAlphabetic}-'/. ]+", message = "Job title can only consist of letters, spaces, apostrophes, slashes, dashes and dots")
    @NotNull(message = "Job title cannot be empty")
    private String jobTitle;


    @NotNull(message = "Hire date cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate hireDate;

    @DecimalMin(value = "1000.00",message = "Salary cannot be less than 1000.00$")
    @NotNull(message = "Salary cannot be empty")
    private Double salary;

    @Pattern(regexp = "[\\p{IsAlphabetic} ]+", message = "Gender can only consist of letters and spaces")
    @NotNull(message = "Gender cannot be empty")
    private String gender;

    @Column(columnDefinition = "boolean not null default false")
    private Boolean sickLeave;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Employee contact cannot be empty")
    @Valid
    private EmployeeContact employeeContact;

}
