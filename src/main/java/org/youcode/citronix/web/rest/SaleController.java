package org.youcode.citronix.web.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Sale;
import org.youcode.citronix.service.SaleService;
import org.youcode.citronix.service.dto.SaleDto;
import org.youcode.citronix.web.vm.mapper.SaleVmMapper;
import org.youcode.citronix.web.vm.sale.SaleResponseVm;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleController {
    private final SaleService saleService;
    public SaleController(SaleService saleService, SaleVmMapper saleVmMapper) {
        this.saleService = saleService;
    }
    @PostMapping("/save")
    public ResponseEntity<SaleDto> save(@RequestBody @Valid Sale sale){
        SaleDto savedDto =saleService.save(sale);
        return new ResponseEntity<>(savedDto , HttpStatus.CREATED);
    }

}
