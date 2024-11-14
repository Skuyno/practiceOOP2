package ru.ssau.tk.practiceoop1.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.tk.practiceoop1.db.DTO.PointDTO;
import ru.ssau.tk.practiceoop1.db.model.PointEntity;
import ru.ssau.tk.practiceoop1.db.mapper.PointMapper;
import ru.ssau.tk.practiceoop1.db.repositories.PointRepository;

@Service
public class PointService {

    private final PointRepository pointRepository;
    private final PointMapper pointMapper;

    @Autowired
    public PointService(PointRepository pointRepository, PointMapper pointMapper) {
        this.pointRepository = pointRepository;
        this.pointMapper = pointMapper;
    }

    public PointDTO create(PointDTO pointDTO) {
        PointEntity pointEntity = pointMapper.toEntity(pointDTO);
        PointEntity savedEntity = pointRepository.save(pointEntity);
        return pointMapper.toDTO(savedEntity);
    }

    public PointDTO read(Long id) {
        return pointRepository.findById(id)
                .map(pointMapper::toDTO)
                .orElse(null);
    }

    public PointDTO update(PointDTO pointDTO) {
        if (pointRepository.existsById(pointDTO.getId())) {
            PointEntity pointEntity = pointMapper.toEntity(pointDTO);
            PointEntity updatedEntity = pointRepository.save(pointEntity);
            return pointMapper.toDTO(updatedEntity);
        }
        throw new RuntimeException("Point not found with id: " + pointDTO.getId());
    }

    public void delete(Long id) {
        if (pointRepository.existsById(id)) {
            pointRepository.deleteById(id);
        } else {
            throw new RuntimeException("Point not found with id: " + id);
        }
    }
}