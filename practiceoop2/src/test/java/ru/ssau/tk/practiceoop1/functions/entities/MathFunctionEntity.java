package ru.ssau.tk.practiceoop1.functions.entities;

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

    private String name;

    private String type; // Например, "Identity", "Sqr", "Composite" и т.д.

    @Column(columnDefinition = "TEXT")
    private String expression; // Аналитическое выражение функции (если есть)

    // Другие поля, соответствующие вашей модели
}
