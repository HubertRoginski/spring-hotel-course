package org.springproject.springproject.model;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.Period;
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

    @Column(nullable = false, unique = true)
    private Integer roomNumber;

    @Min(value = 1, message = "Minimum value of room class is 1")
    @Max(value = 10, message = "Maximum value of room class is 3")
    @Column(nullable = false)
    private Integer roomClass;

    @Column(nullable = false)
    private Integer maxPeopleCapacity;

    @Column(nullable = false)
    private Integer oneDayCost;

    @ManyToMany(mappedBy = "rooms")
    @Setter(value = AccessLevel.NONE)
    @Getter(value = AccessLevel.NONE)
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", roomClass=" + roomClass +
                ", maxPeopleCapacity=" + maxPeopleCapacity +
                ", oneDayCost=" + oneDayCost +
                '}';
    }
}
