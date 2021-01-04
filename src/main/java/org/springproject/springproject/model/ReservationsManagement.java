package org.springproject.springproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationsManagement {
    private List<ReservationsManagementContent> reservationsManagementContentList;
    private Page<Reservation> reservationPage;
}
