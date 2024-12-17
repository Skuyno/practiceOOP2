package ru.ssau.tk.practiceoop1.db.mapper;

import org.springframework.stereotype.Repository;
import ru.ssau.tk.practiceoop1.db.DTO.MathFunctionDTO;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;

@Repository
public class MathFunctionMapper {
    public MathFunctionEntity toEntity(MathFunctionDTO dto){
        if (dto == null) {
            return null;
        }
        MathFunctionEntity entity = new MathFunctionEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCount(dto.getCount());
        entity.setX_from(dto.getX_from());
        entity.setX_to(dto.getX_to());
        return entity;
    }
    public MathFunctionDTO toDTO(MathFunctionEntity entity){
        if (entity == null) {
            return null;
        }
        MathFunctionDTO dto = new MathFunctionDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCount(entity.getCount());
        dto.setX_from(entity.getX_from());
        dto.setX_to(entity.getX_to());
        return dto;
    }
}