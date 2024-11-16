package ru.ssau.tk.practiceoop1.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.tk.practiceoop1.db.DTO.PointDTO;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;
import ru.ssau.tk.practiceoop1.db.model.PointEntity;
import ru.ssau.tk.practiceoop1.db.mapper.PointMapper;
import ru.ssau.tk.practiceoop1.db.repositories.MathFunctionRepository;
import ru.ssau.tk.practiceoop1.db.repositories.PointRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointService {

    private final MathFunctionRepository mathFunctionRepository;
    private final PointRepository pointRepository;
    private final PointMapper pointMapper;

    @Autowired
    public PointService(PointRepository pointRepository, PointMapper pointMapper, MathFunctionRepository mathFunctionRepository) {
        this.mathFunctionRepository = mathFunctionRepository;
        this.pointRepository = pointRepository;
        this.pointMapper = pointMapper;
    }

    private List<PointDTO> getPointsForFunction(MathFunctionEntity function) {
        return pointRepository.findByFunction(function)
                .stream()
                .map(pointMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PointDTO> findByFunction(Long id) {
        return mathFunctionRepository.findById(id)
                .map(this::getPointsForFunction)
                .orElseGet(() -> {
                    return null;
                });
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