package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.web.vm.farm.FarmResponseVm;
import org.youcode.citronix.web.vm.farm.FarmVm;
@Mapper(componentModel = "spring")
public interface FarmVmMapper {
    Farm toFarm(FarmVm farmVm);
    FarmVm toFarmVm(Farm farm);
    FarmResponseVm toResponseVM(Farm farm);

}
