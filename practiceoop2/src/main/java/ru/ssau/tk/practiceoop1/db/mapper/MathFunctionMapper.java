package ru.ssau.tk.practiceoop1.db.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ssau.tk.practiceoop1.db.DTO.MathFunctionDTO;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;

@Mapper
public interface MathFunctionMapper {

    MathFunctionMapper INSTANCE = Mappers.getMapper(MathFunctionMapper.class);

    MathFunctionDTO toDto(MathFunctionEntity entity);

    MathFunctionEntity toEntity(MathFunctionDTO dto);
}

