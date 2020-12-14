package org.springproject.springproject.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Room {
    @Id
    @GeneratedValue
    private Long id;
    @Min(value = 1, message = "Minimum value of room number is 1") @Max(value = 10,message = "Maximum value of room number is 10")
    private Integer roomNumber;

    @Min(value = 1, message = "Minimum value of room class is 1") @Max(value = 10,message = "Maximum value of room class is 3")
    private Integer roomClass;

    private Integer maxPeopleCapacity;

    private Integer oneDayCost;

    private Boolean isOccupied;

    @ManyToMany(mappedBy = "rooms")
    @Setter(value=AccessLevel.NONE)
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation){
        reservations.add(reservation);
    }
}
