package org.youcode.citronix.web.vm.harvest;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.youcode.citronix.domain.enums.Saison;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HarvestVm {
    @NotNull(message = "date is required")
    private LocalDate date;

    @NotNull(message = "Field id is required")
    private UUID fieldId;


}
