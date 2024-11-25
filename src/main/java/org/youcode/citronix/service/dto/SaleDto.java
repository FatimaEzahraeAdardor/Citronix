package org.youcode.citronix.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {

    @NotNull(message = "sale date is required.")
    private LocalDate saleDate;
    @Positive(message = "Quantity must be positive.")
    private double quantity;

    @Positive(message = "unit price must be positive.")
    private double unit_price;

    @NotNull(message = "client name is required")
    private String client_name;

    @NotNull(message = "Harvest id is required")
    private UUID harvestId;

    private double revenue;

}