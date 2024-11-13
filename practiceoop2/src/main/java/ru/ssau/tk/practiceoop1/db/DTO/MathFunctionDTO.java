package ru.ssau.tk.practiceoop1.db.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MathFunctionDTO {
    private Long id;
    private String name;
    private Integer count;
    private Double xFrom;
    private Double xTo;
}
