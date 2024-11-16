package ru.ssau.tk.practiceoop1.db.mapper;

import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.db.DTO.PointDTO;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;
import ru.ssau.tk.practiceoop1.db.model.PointEntity;

import static org.junit.jupiter.api.Assertions.*;

public class PointMapperTest {

    private final PointMapper pointMapper = new PointMapper();

    @Test
    public void testToEntity() {
        PointDTO dto = new PointDTO(null, 2L, 3.0, 4.0);

        PointEntity entity = pointMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getX(), entity.getX());
        assertEquals(dto.getY(), entity.getY());
        assertEquals(dto.getFunctionId(), entity.getFunction().getId());
    }

    @Test
    public void testToDTO() {
        MathFunctionEntity function = new MathFunctionEntity();
        function.setId(2L);

        PointEntity entity = new PointEntity(null, function, 3.0, 4.0);

        PointDTO dto = pointMapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getX(), dto.getX());
        assertEquals(entity.getY(), dto.getY());
        assertEquals(entity.getFunction().getId(), dto.getFunctionId());
    }
}
