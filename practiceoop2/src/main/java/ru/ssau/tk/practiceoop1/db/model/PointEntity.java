package ru.ssau.tk.practiceoop1.db.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "points")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "function")
    private MathFunctionEntity function;

    @Column(columnDefinition = "xValue")
    private Double x;

    @Column(columnDefinition = "yValue")
    private Double y;
}
