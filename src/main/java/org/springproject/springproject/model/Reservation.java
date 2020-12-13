package org.springproject.springproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startOfBooking;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endOfBooking;
    @Min(value = 1, message = "Minimalna wartosc numeru pokoju to 1") @Max(value = 50,message = "Maksymalna wartosc numeru pokoju to 50")
    private Long roomNumber;


}
