package org.springproject.springproject.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@ConfigurationProperties(prefix = "hotel.customer.minions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelCustomerConfig {
    private String firstName;
    private String lastName;
    private String startOfBooking;
    private String endOfBooking;
    private Long roomNumber;
}
