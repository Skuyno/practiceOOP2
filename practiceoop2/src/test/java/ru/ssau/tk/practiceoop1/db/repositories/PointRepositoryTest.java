package ru.ssau.tk.practiceoop1.db.repositories;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;
import ru.ssau.tk.practiceoop1.db.model.PointEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class PointRepositoryTest {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private MathFunctionRepository mathFunctionRepository;

    @Test
    void testFindByFunction() {
        MathFunctionEntity function = new MathFunctionEntity(null, "Test Function", 1, 0.0, 10.0, null);
        mathFunctionRepository.save(function);

        PointEntity point1 = new PointEntity(null, function, 1.0, 2.0);
        pointRepository.save(point1);
        PointEntity point2 = new PointEntity(null, function, 3.0, 6.0);
        pointRepository.save(point2);

        List<PointEntity> points = pointRepository.findByFunction(function);
        assertEquals(2, points.size());
    }
}
