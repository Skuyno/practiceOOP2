package ru.ssau.tk.practiceoop1.db.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ssau.tk.practiceoop1.db.DTO.MathFunctionDTO;
import ru.ssau.tk.practiceoop1.db.exceptions.MathFunctionNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MathFunctionServiceTest {

    @Autowired
    private MathFunctionService mathFunctionService;

    @Test
    public void testCreateMathFunction() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(null, "New Function", 3, 1.0, 5.0);

        MathFunctionDTO createdFunction = mathFunctionService.create(functionDTO);
        assertNotNull(createdFunction);
        assertEquals("New Function", createdFunction.getName());
        assertEquals(3, createdFunction.getCount());
        assertEquals(1.0, createdFunction.getX_from());
        assertEquals(5.0, createdFunction.getX_to());
    }

    @Test
    public void testReadMathFunction() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(null, "Read Function", 4, 0.0, 10.0);
        MathFunctionDTO createdFunction = mathFunctionService.create(functionDTO);

        MathFunctionDTO fetchedFunction = mathFunctionService.read(createdFunction.getId());
        assertNotNull(fetchedFunction);
        assertEquals(createdFunction.getId(), fetchedFunction.getId());
    }

    @Test
    public void testUpdateMathFunction() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(null, "Update Function", 2, 1.0, 5.0);
        MathFunctionDTO createdFunction = mathFunctionService.create(functionDTO);

        createdFunction.setName("Updated Function");
        createdFunction.setCount(10);
        MathFunctionDTO updatedFunction = mathFunctionService.update(createdFunction);

        assertNotNull(updatedFunction);
        assertEquals("Updated Function", updatedFunction.getName());
        assertEquals(10, updatedFunction.getCount());
    }

    @Test
    public void testDeleteMathFunction() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(null, "Delete Function", 3, 0.0, 5.0);
        MathFunctionDTO createdFunction = mathFunctionService.create(functionDTO);

        mathFunctionService.delete(createdFunction.getId());

        MathFunctionNotFoundException exception = assertThrows(MathFunctionNotFoundException.class, () -> {
            mathFunctionService.read(createdFunction.getId());
        });

        assertEquals("Function not found with id: " + createdFunction.getId(), exception.getMessage());
    }

    @Test
    public void testUpdateNonExistingFunction() {
        MathFunctionDTO functionDTO = new MathFunctionDTO(999L, "Non-existing Function", 10, 0.0, 10.0);

        MathFunctionNotFoundException exception = assertThrows(MathFunctionNotFoundException.class, () -> {
            mathFunctionService.update(functionDTO);
        });

        assertEquals("Function not found with id: 999", exception.getMessage());
    }

    @Test
    public void testDeleteNonExistingFunction() {
        MathFunctionNotFoundException exception = assertThrows(MathFunctionNotFoundException.class, () -> {
            mathFunctionService.delete(999L);
        });

        assertEquals("Function not found with id: 999", exception.getMessage());
    }
}
