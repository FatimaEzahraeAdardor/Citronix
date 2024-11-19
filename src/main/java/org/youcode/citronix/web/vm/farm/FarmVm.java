package org.youcode.citronix.web.vm.farm;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.youcode.citronix.web.vm.field.FieldVm;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmVm {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "location is required")
    private String location;

    @Positive(message = "Area must be greater than zero")
    @NotNull(message = "Area cannot be null")
    private double area;

    private LocalDateTime created_at = LocalDateTime.now();

    private List<@Valid FieldVm> fields;

}
