package ru.ssau.tk.practiceoop1.db.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointDTOTest {

    @Test
    void testPointDTOConstructor() {
        PointDTO pointDTO = new PointDTO(1L, 1L, 2.0, 3.0);
        assertEquals(1L, pointDTO.getId());
        assertEquals(1L, pointDTO.getFunctionId());
        assertEquals(2.0, pointDTO.getX());
        assertEquals(3.0, pointDTO.getY());
    }

    @Test
    void testPointDTOSettersAndGetters() {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setId(2L);
        pointDTO.setFunctionId(3L);
        pointDTO.setX(5.0);
        pointDTO.setY(7.0);

        assertEquals(2L, pointDTO.getId());
        assertEquals(3L, pointDTO.getFunctionId());
        assertEquals(5.0, pointDTO.getX());
        assertEquals(7.0, pointDTO.getY());
    }
}