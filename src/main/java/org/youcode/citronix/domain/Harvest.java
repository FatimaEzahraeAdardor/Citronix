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
public class Harvest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate date;

    private double total_quantity;

    @Enumerated(EnumType.STRING)
    private Saison saison;

    @OneToMany(mappedBy = "harvest")
    private List<HarvestDetails> harvestDetails;

    @OneToMany(mappedBy = "harvest")
    private List<Sale> sales;



}
