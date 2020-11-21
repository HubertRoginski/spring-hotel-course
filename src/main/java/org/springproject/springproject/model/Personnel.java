package org.springproject.springproject.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private String firstName;
    private String lastName;
    private String position;
    private LocalDate hireDate;
    private Double salary;
    private Boolean sickLeave;
//    private płeć
}
