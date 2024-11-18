package org.youcode.citronix.web.vm.farm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmResponseVm {
    private UUID id;
    private String name;
    private String location;
    private double area;
    private LocalDate created_at;
}
