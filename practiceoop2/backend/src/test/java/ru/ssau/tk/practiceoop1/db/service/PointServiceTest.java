package ru.ssau.tk.practiceoop1.db.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ssau.tk.practiceoop1.db.DTO.MathFunctionDTO;
import ru.ssau.tk.practiceoop1.db.DTO.PointDTO;
import ru.ssau.tk.practiceoop1.exceptions.PointNotFoundException;
import ru.ssau.tk.practiceoop1.exceptions.MathFunctionNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PointServiceTest {

    @Autowired
    private PointService pointService;

    @Autowired
    private MathFunctionService mathFunctionService;

    @Test
    public void testCreatePoint() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(null, "Function", 3, 1.0, 5.0);
        MathFunctionDTO createdFunction = mathFunctionService.create(functionDTO);

        PointDTO pointDTO = new PointDTO(null, createdFunction.getId(),2.0, 3.0);
        PointDTO createdPoint = pointService.create(pointDTO);

        assertNotNull(createdPoint);
        assertEquals(2.0, createdPoint.getX());
        assertEquals(3.0, createdPoint.getY());
    }

    @Test
    public void testReadPoint() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(null, "Read Function", 4, 0.0, 10.0);
        MathFunctionDTO createdFunction = mathFunctionService.create(functionDTO);

        PointDTO pointDTO = new PointDTO(null, createdFunction.getId(),2.0, 3.0);
        PointDTO createdPoint = pointService.create(pointDTO);

        PointDTO fetchedPoint = pointService.read(createdPoint.getId());
        assertNotNull(fetchedPoint);
        assertEquals(createdPoint.getId(), fetchedPoint.getId());
    }

    @Test
    public void testUpdatePoint() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(null, "Update Function", 2, 1.0, 5.0);
        MathFunctionDTO createdFunction = mathFunctionService.create(functionDTO);

        PointDTO pointDTO = new PointDTO(null, createdFunction.getId(),1.0, 2.0);
        PointDTO createdPoint = pointService.create(pointDTO);

        createdPoint.setX(3.0);
        createdPoint.setY(4.0);
        PointDTO updatedPoint = pointService.update(createdPoint);

        assertNotNull(updatedPoint);
        assertEquals(3.0, updatedPoint.getX());
        assertEquals(4.0, updatedPoint.getY());
    }

    @Test
    public void testDeletePoint() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(null, "Delete Function", 3, 0.0, 5.0);
        MathFunctionDTO createdFunction = mathFunctionService.create(functionDTO);

        PointDTO pointDTO = new PointDTO(null, createdFunction.getId(), 2.0, 3.0);
        PointDTO createdPoint = pointService.create(pointDTO);

        pointService.delete(createdPoint.getId());

        PointNotFoundException exception = assertThrows(PointNotFoundException.class, () -> {
            pointService.read(createdPoint.getId());
        });

        assertEquals("Point not found with id: " + createdPoint.getId(), exception.getMessage());
    }


    @Test
    public void testUpdateNonExistingPoint() {
        PointDTO pointDTO = new PointDTO(999L, 1L,1.0, 1.0);

        PointNotFoundException exception = assertThrows(PointNotFoundException.class, () -> {
            pointService.update(pointDTO);
        });

        assertEquals("Point not found with id: 999", exception.getMessage());
    }

    @Test
    public void testDeleteNonExistingPoint() {
        PointNotFoundException exception = assertThrows(PointNotFoundException.class, () -> {
            pointService.delete(999L);
        });

        assertEquals("Point not found with id: 999", exception.getMessage());
    }

    @Test
    public void testFindPointsForNonExistingFunction() {
        MathFunctionNotFoundException exception = assertThrows(MathFunctionNotFoundException.class, () -> {
            pointService.findByFunction(999L);
        });

        assertEquals("Function not found with id: 999", exception.getMessage());
    }
}
