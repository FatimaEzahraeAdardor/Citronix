package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.web.vm.harvest.HarvestResponseVm;
import org.youcode.citronix.web.vm.harvest.HarvestVm;

@Mapper(componentModel = "spring" )
public interface HarvestMapperVm {
    Harvest toharvest(HarvestVm harvestVm);

    HarvestVm toharvestVm(Harvest harvest);
    @Mapping(target = "harvestDate", source = "date")
    @Mapping(target = "season", source = "saison")
    HarvestResponseVm toharvestResponseVm(Harvest harvest);

}
