package ru.ssau.tk.practiceoop1.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "points",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"function_id", "x_value"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "function_id", referencedColumnName = "id")
    private MathFunctionEntity function;

    @Column(name = "x_value", nullable = false)
    private Double x;

    @Column(name = "y_value", nullable = false)
    private Double y;
}
