package org.youcode.citronix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String location;

    private double area;

    private LocalDateTime created_at;

    @OneToMany(mappedBy = "farm" ,cascade = CascadeType.ALL)
    private List<Field> fields;



}
