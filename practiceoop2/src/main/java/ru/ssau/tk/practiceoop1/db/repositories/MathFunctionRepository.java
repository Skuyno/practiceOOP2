package ru.ssau.tk.practiceoop1.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;

import java.util.List;

@Repository
public interface MathFunctionRepository extends JpaRepository<MathFunctionEntity, Long> {
    List<MathFunctionEntity> findByName(String name);
}
