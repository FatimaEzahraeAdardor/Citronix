package org.youcode.citronix.web.vm.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreeResponseVm {
    private UUID id;
    private LocalDateTime planting_date;
}
