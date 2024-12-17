package ru.ssau.tk.practiceoop1.db.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathFunctionDTOTest {

    @Test
    void testMathFunctionDTO() {
        MathFunctionDTO mathFunctionDTO = new MathFunctionDTO(1L, "Test Function", 5, 0.0, 10.0);

        assertEquals(1L, mathFunctionDTO.getId());
        assertEquals("Test Function", mathFunctionDTO.getName());
        assertEquals(5, mathFunctionDTO.getCount());
        assertEquals(0.0, mathFunctionDTO.getX_from());
        assertEquals(10.0, mathFunctionDTO.getX_to());
    }

    @Test
    void testMathFunctionDTOSettersAndGetters() {
        MathFunctionDTO mathFunctionDTO = new MathFunctionDTO();
        mathFunctionDTO.setId(2L);
        mathFunctionDTO.setName("Updated Function");
        mathFunctionDTO.setCount(5);
        mathFunctionDTO.setX_from(1.0);
        mathFunctionDTO.setX_to(20.0);

        assertEquals(2L, mathFunctionDTO.getId());
        assertEquals("Updated Function", mathFunctionDTO.getName());
        assertEquals(5, mathFunctionDTO.getCount());
        assertEquals(1.0, mathFunctionDTO.getX_from());
        assertEquals(20.0, mathFunctionDTO.getX_to());
    }
}