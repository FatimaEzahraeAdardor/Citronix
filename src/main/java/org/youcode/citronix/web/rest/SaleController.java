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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ResponseEntity<SaleDto> save(@RequestBody @Valid Sale sale){
        SaleDto savedDto =saleService.save(sale);
        return new ResponseEntity<>(savedDto , HttpStatus.CREATED);
    }
    @GetMapping("/details/{saleId}")
    public ResponseEntity<SaleResponseVm> getSaleById(@PathVariable UUID saleId) {
        Sale sale = saleService.findById(saleId);
        SaleDto saleDto = saleVmMapper.toDto(sale);
        SaleResponseVm saleResponseVM = saleVmMapper.toResponseVM(saleDto);
        return ResponseEntity.ok(saleResponseVM);
    }
    @GetMapping("/all")
    public ResponseEntity<List<SaleResponseVm>> getAllSales() {
        List<Sale> saleList = saleService.findAll();

        List<SaleDto> saleResponseDtoList = saleList.stream()
                .map(saleVmMapper::toDto)
                .collect(Collectors.toList());
        List<SaleResponseVm> saleResponseVmList = saleResponseDtoList.stream()
                .map(saleVmMapper::toResponseVM)
                .collect(Collectors.toList());
        return ResponseEntity.ok(saleResponseVmList);
    }
    @DeleteMapping("/delete/{saleId}")
    public ResponseEntity<String> deleteSale(@PathVariable UUID saleId) {
        saleService.delete(saleId);
        return ResponseEntity.ok("Sale deleted successfully");
    }
}
