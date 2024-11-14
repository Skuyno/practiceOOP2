package ru.ssau.tk.practiceoop1.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.tk.practiceoop1.db.model.PointEntity;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, Long> {
}
