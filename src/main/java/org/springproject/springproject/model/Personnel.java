package org.springproject.springproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Personnel {
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private LocalDate hireDate;
    private Double salary;
    private Boolean sickLeave;
//    private płeć
}
