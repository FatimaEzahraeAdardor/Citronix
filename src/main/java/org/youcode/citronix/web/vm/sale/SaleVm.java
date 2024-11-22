package org.youcode.citronix.web.vm.sale;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleVm {
    private double area;
    private UUID id;

    @NotNull(message = "sale date is required.")
    private LocalDate saleDate;


    @Positive(message = "unit price must be positive.")
    private double unit_price;

    @NotNull(message = "client name is required")
    private String client_name;

    @NotNull(message = "Harvest id is required")
    private UUID harvestId;

}