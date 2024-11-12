package ru.ssau.tk.practiceoop1.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "math_functions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MathFunctionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "name")
    private String name;

    @Column(columnDefinition = "count")
    private Integer count;

    @Column(columnDefinition = "xFrom")
    private Double xFrom;

    @Column(columnDefinition = "xTo")
    private Double xTo;
}
