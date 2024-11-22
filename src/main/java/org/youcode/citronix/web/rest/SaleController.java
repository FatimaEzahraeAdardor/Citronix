package org.youcode.citronix.web.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Sale;
import org.youcode.citronix.service.SaleService;
import org.youcode.citronix.web.vm.sale.SaleVm;
import org.youcode.citronix.web.vm.mapper.SaleVmMapper;
import org.youcode.citronix.web.vm.sale.SaleResponseVm;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleController {
    private final SaleService saleService;
    private final SaleVmMapper saleVmMapper;
    public SaleController(SaleService saleService, SaleVmMapper saleVmMapper) {
        this.saleService = saleService;
        this.saleVmMapper = saleVmMapper;
    }
    @PostMapping("/save")
    public ResponseEntity<SaleResponseVm> save(@RequestBody @Valid SaleVm saleVm){
        Sale sale = saleVmMapper.toEntity(saleVm);
        Sale savedSale =saleService.save(saleVm.getHarvestId(),sale);
        SaleResponseVm farmResponseVm = saleVmMapper.toResponseVM(savedSale);
        return new ResponseEntity<>(farmResponseVm , HttpStatus.CREATED);
    }

}
