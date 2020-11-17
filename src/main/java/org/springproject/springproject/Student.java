package org.springproject.springproject;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private Integer index;
    private Double age;
    private List<String> friendsList;
}
