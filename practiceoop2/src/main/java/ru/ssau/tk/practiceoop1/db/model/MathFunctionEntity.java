package ru.ssau.tk.practiceoop1.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "math_functions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MathFunctionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "x_from")
    private Double x_from;

    @Column(name = "x_to")
    private Double x_to;

    @OneToMany(mappedBy = "function", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PointEntity> points;
}
