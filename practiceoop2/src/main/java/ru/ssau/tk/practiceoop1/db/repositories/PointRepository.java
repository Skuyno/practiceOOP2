package ru.ssau.tk.practiceoop1.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;
import ru.ssau.tk.practiceoop1.db.model.PointEntity;

import java.util.List;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
    List<PointEntity> findByFunction(MathFunctionEntity functionEntity);
}
