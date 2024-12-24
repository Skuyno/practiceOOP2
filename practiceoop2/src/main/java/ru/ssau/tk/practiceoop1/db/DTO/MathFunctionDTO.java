package ru.ssau.tk.practiceoop1.db.DTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MathFunctionDTO {

    private Long id;

    @NotBlank(message = "Имя функции не может быть пустым")
    private String name;

    @Min(value = 1, message = "Количество точек должно быть >= 1")
    private int count;

    @DecimalMin(value = "-10000", message = "x_from слишком маленькое")
    private double x_from;

    @DecimalMax(value = "10000", message = "x_to слишком большое")
    private double x_to;
}
