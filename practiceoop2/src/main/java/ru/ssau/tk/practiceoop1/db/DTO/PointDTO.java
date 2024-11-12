package ru.ssau.tk.practiceoop1.db.DTO;

import lombok.*;

@Data
public class PointDTO {
    private Long id;
    private Long functionId;
    private Double x;
    private Double y;
}
