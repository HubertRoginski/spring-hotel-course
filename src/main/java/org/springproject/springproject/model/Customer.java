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
public class Customer {
    private Long customerId;
    private String firstName;
    private String lastName;
    private LocalDate startOfBooking;
    private LocalDate endOfBooking;
    private Long roomNumber;
}
