package ru.ssau.tk.practiceoop1.db.DTO;

import lombok.Data;

@Data
public class MathFunctionDTO {
    private Long id;
    private String name;
    private Integer count;
    private Double xFrom;
    private Double xTo;
}
