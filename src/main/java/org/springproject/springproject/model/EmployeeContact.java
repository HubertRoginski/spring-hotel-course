package org.springproject.springproject.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeContact {

    @Id
    @GeneratedValue
    private Long id;

    @Email(message = "an example email address is example@gmail.com")
    @Size(min = 1,message = "email field cannot be empty")
    @NotNull(message = "Email cannot be empty")
    private String email;

    @Pattern(regexp = "[[0-9]]+", message = "Phone number can only consist of numbers")
    @NotNull(message = "Phone number cannot be empty")
    private String phoneNumber;

    @Pattern(regexp = "[\\p{IsAlphabetic}[0-9]-. ]+", message = "Address can only consist of letters, numbers ,spaces, dashes and dots")
    @NotNull(message = "Address cannot be empty")
    private String address;

    @OneToOne(mappedBy = "employeeContact")
    @Getter(value=AccessLevel.NONE)
    private Personnel personnel;

}
