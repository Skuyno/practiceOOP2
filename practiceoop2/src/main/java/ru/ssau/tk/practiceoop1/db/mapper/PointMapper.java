package ru.ssau.tk.practiceoop1.db.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ssau.tk.practiceoop1.db.DTO.PointDTO;
import ru.ssau.tk.practiceoop1.db.model.PointEntity;

@Mapper(componentModel = "spring")
public interface PointMapper {

    PointMapper INSTANCE = Mappers.getMapper(PointMapper.class);

    @Mapping(source = "functionId", target = "function.id")
    PointEntity toEntity(PointDTO dto);

    @Mapping(source = "function.id", target = "functionId")
    PointDTO toDto(PointEntity entity);
}
