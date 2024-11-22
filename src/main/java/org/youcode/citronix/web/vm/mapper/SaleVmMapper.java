package org.youcode.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.youcode.citronix.domain.Sale;
import org.youcode.citronix.service.dto.SaleDto;
import org.youcode.citronix.web.vm.sale.SaleResponseVm;

@Mapper(componentModel = "spring",uses = HarvestMapperVm.class)
public interface SaleVmMapper {
    @Mapping(target = "harvestId", source = "harvest.id")
    SaleDto toDto(Sale sale);
    Sale toEntity(SaleDto saleDto);
    @Mapping(target = "harvest_id", source = "harvest.id")
    SaleResponseVm toResponseVM(Sale sale);

}