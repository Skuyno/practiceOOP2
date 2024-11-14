package ru.ssau.tk.practiceoop1.db.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ssau.tk.practiceoop1.db.DTO.MathFunctionDTO;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;
import ru.ssau.tk.practiceoop1.db.repositories.MathFunctionRepository;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MathFunctionServiceTest {

    @Autowired
    private MathFunctionService mathFunctionService;

    @Autowired
    private MathFunctionRepository mathFunctionRepository;

    @BeforeEach
    public void setUp() {
        MathFunctionEntity function = new MathFunctionEntity();
        function.setName("Test Function");
        function.setCount(5);
        function.setXFrom(0.0);
        function.setXTo(10.0);
        mathFunctionRepository.save(function);
    }

    @Test
    public void testCreateMathFunction() {
        MathFunctionDTO functionDTO = new MathFunctionDTO();
        functionDTO.setName("New Function");
        functionDTO.setCount(3);
        functionDTO.setXFrom(1.0);
        functionDTO.setXTo(5.0);

        MathFunctionDTO createdFunction = mathFunctionService.create(functionDTO);
        assertNotNull(createdFunction);
        assertEquals("New Function", createdFunction.getName());
        assertEquals(3, createdFunction.getCount());
        assertEquals(1.0, createdFunction.getXFrom());
        assertEquals(5.0, createdFunction.getXTo());
    }

    @Test
    public void testReadMathFunction() {
        MathFunctionDTO functionDTO = new MathFunctionDTO();
        functionDTO.setName("Read Function");
        functionDTO.setCount(4);
        functionDTO.setXFrom(0.0);
        functionDTO.setXTo(10.0);
        MathFunctionDTO createdFunction = mathFunctionService.create(functionDTO);

        MathFunctionDTO fetchedFunction = mathFunctionService.read(createdFunction.getId());
        assertNotNull(fetchedFunction);
        assertEquals(createdFunction.getId(), fetchedFunction.getId());
    }

    @Test
    public void testUpdateMathFunction() {
        MathFunctionDTO functionDTO = new MathFunctionDTO();
        functionDTO.setName("Update Function");
        functionDTO.setCount(2);
        functionDTO.setXFrom(1.0);
        functionDTO.setXTo(5.0);
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
        MathFunctionDTO functionDTO = new MathFunctionDTO();
        functionDTO.setName("Delete Function");
        functionDTO.setCount(3);
        functionDTO.setXFrom(0.0);
        functionDTO.setXTo(5.0);
        MathFunctionDTO createdFunction = mathFunctionService.create(functionDTO);

        mathFunctionService.delete(createdFunction.getId());

        assertNull(mathFunctionService.read(createdFunction.getId()));
    }
}
