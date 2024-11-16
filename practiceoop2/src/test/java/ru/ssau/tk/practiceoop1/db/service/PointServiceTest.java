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

import java.util.List;

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
        function = new MathFunctionEntity(null, "Test Function", 5, 0.0, 10.0, null);
        mathFunctionRepository.save(function);
    }

    @Test
    public void testCreatePoint() {
        // Создаем DTO для точки
        PointDTO pointDTO = new PointDTO(null,function.getId(),5.0, 10.0);

        // Создаем точку через сервис
        PointDTO createdPoint = pointService.create(pointDTO);

        // Проверка на успешное создание
        assertNotNull(createdPoint);
        assertEquals(5.0, createdPoint.getX());
        assertEquals(10.0, createdPoint.getY());
        assertEquals(function.getId(), createdPoint.getFunctionId());
    }

    @Test
    public void testReadPoint() {
        // Создаем DTO для точки
        PointDTO pointDTO = new PointDTO(null,function.getId(),3.0, 6.0);
        PointDTO createdPoint = pointService.create(pointDTO);

        // Читаем точку из сервиса
        PointDTO fetchedPoint = pointService.read(createdPoint.getId());

        // Проверка на успешное извлечение
        assertNotNull(fetchedPoint);
        assertEquals(createdPoint.getId(), fetchedPoint.getId());
    }

    @Test
    public void testUpdatePoint() {
        // Создаем DTO для точки
        PointDTO pointDTO = new PointDTO(null,function.getId(),1.0, 2.0);
        PointDTO createdPoint = pointService.create(pointDTO);

        // Обновляем значения
        createdPoint.setX(10.0);
        createdPoint.setY(20.0);
        PointDTO updatedPoint = pointService.update(createdPoint);

        // Проверка на успешное обновление
        assertNotNull(updatedPoint);
        assertEquals(10.0, updatedPoint.getX());
        assertEquals(20.0, updatedPoint.getY());
    }

    @Test
    public void testDeletePoint() {
        // Создаем DTO для точки
        PointDTO pointDTO = new PointDTO(null,function.getId(),2.0, 4.0);
        PointDTO createdPoint = pointService.create(pointDTO);

        // Удаляем точку
        pointService.delete(createdPoint.getId());

        // Проверка, что точка была удалена
        assertNull(pointService.read(createdPoint.getId()));
    }

    @Test
    public void testUpdateNonExistingPoint() {
        PointDTO pointDTO = new PointDTO(999L, function.getId(), 10.0, 20.0);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pointService.update(pointDTO);
        });

        assertEquals("Point not found with id: 999", exception.getMessage());
    }

    @Test
    public void testDeleteNonExistingPoint() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            pointService.delete(999L);
        });

        assertEquals("Point not found with id: 999", exception.getMessage());
    }

    @Test
    public void testFindByFunctionEntity() {
        MathFunctionEntity function = new MathFunctionEntity(null, "Test Function", 5, 0.0, 10.0, null);
        MathFunctionEntity savedFunction = mathFunctionRepository.save(function);

        PointEntity point1 = new PointEntity(null, savedFunction, 1.0, 2.0);
        PointEntity point2 = new PointEntity(null, savedFunction, 3.0, 4.0);
        pointRepository.save(point1);
        pointRepository.save(point2);

        List<PointDTO> points = pointService.findByFunction(savedFunction.getId());

        assertNotNull(points);
        assertEquals(2, points.size());

        assertEquals(1.0, points.get(0).getX());
        assertEquals(2.0, points.get(0).getY());

        assertEquals(3.0, points.get(1).getX());
        assertEquals(4.0, points.get(1).getY());
    }
}
