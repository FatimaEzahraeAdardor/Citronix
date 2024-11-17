package org.youcode.citronix.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.youcode.citronix.domain.enums.Saison;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HarvestDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private double quantity;


    @ManyToOne
    private Tree tree;

    @ManyToOne
    private Harvest harvest;
}
