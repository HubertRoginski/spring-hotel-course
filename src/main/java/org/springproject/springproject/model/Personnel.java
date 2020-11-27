package org.springproject.springproject.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "personnel")
public class Personnel {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
//    @Length(min = 5, max = 25)
    private String firstName;
    private String lastName;
    private String position;
    private LocalDate hireDate;
    @DecimalMin(value = "1000.0")
    private Double salary;
    private Boolean sickLeave;
//    private płeć
}
