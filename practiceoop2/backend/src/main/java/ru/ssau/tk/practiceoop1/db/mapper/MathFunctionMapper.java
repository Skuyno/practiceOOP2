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
        entity.setXFrom(dto.getXFrom());
        entity.setXTo(dto.getXTo());
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
        dto.setXFrom(entity.getXFrom());
        dto.setXTo(entity.getXTo());
        return dto;
    }
}