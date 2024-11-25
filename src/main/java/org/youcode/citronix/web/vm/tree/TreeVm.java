package org.youcode.citronix.web.vm.tree;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.youcode.citronix.domain.Field;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreeVm {
    private UUID id;

    @NotNull(message = "planting date is required")
    private LocalDate planting_date;

    @NotNull(message = "farm id is required")
    private UUID fieldId;
}
