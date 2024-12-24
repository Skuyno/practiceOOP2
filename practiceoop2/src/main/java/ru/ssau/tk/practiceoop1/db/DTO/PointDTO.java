package ru.ssau.tk.practiceoop1.db.DTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointDTO {
    private Long id;

    @NotNull
    private Long functionId;

    @NotNull
    @DecimalMin(value = "-1E10", inclusive = true)
    @DecimalMax(value = "1E10", inclusive = true)
    private Double x;

    @NotNull
    @DecimalMin(value = "-1E10", inclusive = true)
    @DecimalMax(value = "1E10", inclusive = true)
    private Double y;
}
