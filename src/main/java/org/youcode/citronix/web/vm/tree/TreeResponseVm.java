package org.youcode.citronix.web.vm.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreeResponseVm {
    private UUID id;
    private LocalDate planting_date;
    private int age;
    private double ProductiviteAnnuelle;
}
