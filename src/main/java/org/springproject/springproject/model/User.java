package org.springproject.springproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//klasa uzytkownika do autentykacji i autoryzacji
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue

    @Column(nullable = false,updatable = false)
    private Long id;


    @Size(min = 4,max = 20,message = "username length must be between 4 and 20")
    @Pattern(regexp = "[\\p{IsAlphabetic}[0-9]-_]+", message = "username can only consist of letters, numbers, dashes and underscores")
    @Column(nullable = false,unique = true)
    private String username;

    @Email(message = "an example e-mail address is example@gmail.com")
    @Column(nullable = false,unique = true)
    private String email;

    @Size(min = 4,message = "password length must be min 4")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(columnDefinition = "boolean not null default false")
    private Boolean enabled;
}
