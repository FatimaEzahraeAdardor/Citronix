package org.youcode.citronix.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate saleDate;

    private double quantity;

    private double unit_price;

    private String client_name;

    @ManyToOne
    private Harvest harvest;


    public double getrevenue(){
       return this.getUnit_price() * this.getQuantity();
    }

}
