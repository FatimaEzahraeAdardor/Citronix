package org.youcode.citronix.web.vm.harvestDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.domain.enums.Saison;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HarvestDetailsResponseVm {

    private double quantity;
    private List<Tree> trees;
}
