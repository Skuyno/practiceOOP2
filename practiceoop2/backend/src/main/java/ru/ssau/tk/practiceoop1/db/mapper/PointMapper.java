package ru.ssau.tk.practiceoop1.db.mapper;

import org.springframework.stereotype.Repository;
import ru.ssau.tk.practiceoop1.db.DTO.PointDTO;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;
import ru.ssau.tk.practiceoop1.db.model.PointEntity;

@Repository
public class PointMapper {
    public PointEntity toEntity(PointDTO dto){
        if (dto == null) {
            return null;
        }
        PointEntity entity = new PointEntity();
        entity.setId(dto.getId());
        entity.setX(dto.getX());
        entity.setY(dto.getY());
        MathFunctionEntity function = new MathFunctionEntity();
        function.setId(dto.getFunctionId());
        entity.setFunction(function);
        return entity;
    }
    public PointDTO toDTO(PointEntity entity){
        if (entity == null) {
            return null;
        }
        PointDTO dto = new PointDTO();
        dto.setId(entity.getId());
        dto.setX(entity.getX());
        dto.setY(entity.getY());
        dto.setFunctionId(entity.getFunction().getId());
        return dto;
    }
}