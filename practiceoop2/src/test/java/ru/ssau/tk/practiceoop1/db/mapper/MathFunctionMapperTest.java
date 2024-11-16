package ru.ssau.tk.practiceoop1.db.mapper;

import org.junit.jupiter.api.Test;
import ru.ssau.tk.practiceoop1.db.DTO.MathFunctionDTO;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;

import static org.junit.jupiter.api.Assertions.*;

public class MathFunctionMapperTest {

    private final MathFunctionMapper mathFunctionMapper = new MathFunctionMapper();

    @Test
    public void testToEntity() {
        MathFunctionDTO dto = new MathFunctionDTO(null, "Test Function", 5, 0.0, 10.0);

        MathFunctionEntity entity = mathFunctionMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getCount(), entity.getCount());
        assertEquals(dto.getXFrom(), entity.getXFrom());
        assertEquals(dto.getXTo(), entity.getXTo());
    }

    @Test
    public void testToDTO() {
        MathFunctionEntity entity = new MathFunctionEntity(null, "Test Function", 5, 0.0, 10.0, null);

        MathFunctionDTO dto = mathFunctionMapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getCount(), dto.getCount());
        assertEquals(entity.getXFrom(), dto.getXFrom());
        assertEquals(entity.getXTo(), dto.getXTo());
    }
}
