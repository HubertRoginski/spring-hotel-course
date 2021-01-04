package org.springproject.springproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationsManagementContent {
    private User user;
    private Customer customer;
    private Reservation reservation;
}
