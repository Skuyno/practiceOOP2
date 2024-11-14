package ru.ssau.tk.practiceoop1.db.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ssau.tk.practiceoop1.db.DTO.PointDTO;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;
import ru.ssau.tk.practiceoop1.db.model.PointEntity;
import ru.ssau.tk.practiceoop1.db.repositories.PointRepository;
import ru.ssau.tk.practiceoop1.db.repositories.MathFunctionRepository;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PointServiceTest {

    @Autowired
    private PointService pointService;

    @Autowired
    private MathFunctionRepository mathFunctionRepository;

    @Autowired
    private PointRepository pointRepository;

    private MathFunctionEntity function;

    @BeforeEach
    public void setUp() {
        function = new MathFunctionEntity();
        function.setName("Test Function");
        function.setCount(5);
        function.setXFrom(0.0);
        function.setXTo(10.0);
        mathFunctionRepository.save(function);
    }

    @Test
    public void testCreatePoint() {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setX(5.0);
        pointDTO.setY(10.0);
        pointDTO.setFunctionId(function.getId());

        PointDTO createdPoint = pointService.create(pointDTO);
        assertNotNull(createdPoint);
        assertEquals(5.0, createdPoint.getX());
        assertEquals(10.0, createdPoint.getY());
        assertEquals(function.getId(), createdPoint.getFunctionId());
    }

    @Test
    public void testReadPoint() {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setX(3.0);
        pointDTO.setY(6.0);
        pointDTO.setFunctionId(function.getId());

        PointDTO createdPoint = pointService.create(pointDTO);
        PointDTO fetchedPoint = pointService.read(createdPoint.getId());

        assertNotNull(fetchedPoint);
        assertEquals(createdPoint.getId(), fetchedPoint.getId());
    }

    @Test
    public void testUpdatePoint() {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setX(1.0);
        pointDTO.setY(2.0);
        pointDTO.setFunctionId(function.getId());
        PointDTO createdPoint = pointService.create(pointDTO);

        createdPoint.setX(10.0);
        createdPoint.setY(20.0);
        PointDTO updatedPoint = pointService.update(createdPoint);

        assertNotNull(updatedPoint);
        assertEquals(10.0, updatedPoint.getX());
        assertEquals(20.0, updatedPoint.getY());
    }

    @Test
    public void testDeletePoint() {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setX(2.0);
        pointDTO.setY(4.0);
        pointDTO.setFunctionId(function.getId());
        PointDTO createdPoint = pointService.create(pointDTO);

        pointService.delete(createdPoint.getId());

        assertNull(pointService.read(createdPoint.getId()));
    }
}
