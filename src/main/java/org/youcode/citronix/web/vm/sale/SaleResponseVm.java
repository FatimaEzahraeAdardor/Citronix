package org.youcode.citronix.web.vm.sale;

import jakarta.persistence.ManyToOne;
import lombok.*;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.web.vm.harvest.HarvestVm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleResponseVm {

    private LocalDate saleDate;

    private double unit_price;

    private String client_name;

    private UUID harvest_id;

    private double revenue;

}
