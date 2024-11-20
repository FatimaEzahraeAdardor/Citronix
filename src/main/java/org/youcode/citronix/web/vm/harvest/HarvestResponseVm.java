package org.youcode.citronix.web.vm.harvest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.youcode.citronix.domain.enums.Saison;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HarvestResponseVm {
    private LocalDate harvestDate;
    private Saison season;
}
