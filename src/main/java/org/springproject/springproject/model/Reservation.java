package org.springproject.springproject.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @NotNull(message = "Start date o booking cannot be empty")
    private LocalDate startOfBooking;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "End date o booking cannot be empty")
    private LocalDate endOfBooking;

    private Long cost;

    @ManyToMany
    @JoinTable(
            name="RESERVATION_ROOM",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROOM_ID")
    )
    private List<Room> rooms = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter(value=AccessLevel.NONE)
    private Customer customer;

    public void addRoom(Room room){
        rooms.add(room);
    }
}
