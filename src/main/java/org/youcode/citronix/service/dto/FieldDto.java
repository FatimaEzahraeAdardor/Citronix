package org.youcode.citronix.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldDto {
    private UUID id;
    @Positive(message = "Field area must be positive.")
    private double area;
    @NotNull(message = "farm id is required")
    private UUID farmId;
}