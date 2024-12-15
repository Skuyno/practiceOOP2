package ru.ssau.tk.practiceoop1.db.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointDTO {
    private Long id;
    private Long functionId;
    private Double x;
    private Double y;
}
