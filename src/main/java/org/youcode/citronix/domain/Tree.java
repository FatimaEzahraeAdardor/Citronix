package org.youcode.citronix.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDate planting_date;

    @ManyToOne
    private Field field;

    @OneToMany(mappedBy = "tree" , cascade = CascadeType.ALL)
    private List<HarvestDetails> harvestDetails;

    public int getAge(){
        if (planting_date == null) return 0;
        return LocalDate.now().getYear() - planting_date.getYear();
    }

    public Double getProductiviteAnnuelle() {
        int age = getAge();
        if (age < 3) {
            return 2.5;
        } else if (age <= 10) {
            return 12.0;
        } else {
            return 20.0;
        }
    }



}
