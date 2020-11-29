package org.springproject.springproject.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "[\\p{IsAlphabetic}-. ]+", message = "Imie moze zawierac wylacznie litery, spacje, kropki i myslniki")
    private String firstName;
    @Pattern(regexp = "[\\p{IsAlphabetic}-. ]+", message = "Nazwisko moze zawierac wylacznie litery, spacje, kropki i myslniki")
    private String lastName;
    @Pattern(regexp = "[\\p{IsAlphabetic}-. ]+", message = "Stanowisko moze zawierac wylacznie litery, spacje, kropki i myslniki")
    private String position;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;
    @DecimalMin(value = "1000.0",message = "Pensja nie moze byc mniejsza niz 1000.0")
    private Double salary;
    private Boolean sickLeave;
//    private płeć
}
