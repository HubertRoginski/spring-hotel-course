package org.springproject.springproject.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    private Long customerId;
    @Pattern(regexp = "[\\p{IsAlphabetic}-. ]+", message = "First name can only consist of letters,spaces, dashes and dots")
    private String firstName;
    @Pattern(regexp = "[\\p{IsAlphabetic}-. ]+", message = "Last name can only consist of letters,spaces, dashes and dots")
    private String lastName;
    @Pattern(regexp = "[\\p{IsAlphabetic}[0-9]-. ]+", message = "Address can only consist of letters, numbers ,spaces, dashes and dots")
    private String address;
    @Pattern(regexp = "[[0-9]]+", message = "Phone number can only consist of numbers")
    private String phoneNumber;
//    private List<Reservation> reservations;



    @OneToOne(mappedBy = "customer")
    @Getter(value=AccessLevel.NONE)
    private User user;

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
