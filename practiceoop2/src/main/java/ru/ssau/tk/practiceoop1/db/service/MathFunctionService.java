package ru.ssau.tk.practiceoop1.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.tk.practiceoop1.db.DTO.MathFunctionDTO;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;
import ru.ssau.tk.practiceoop1.db.mapper.MathFunctionMapper;
import ru.ssau.tk.practiceoop1.db.repositories.MathFunctionRepository;

@Service
public class MathFunctionService {

    private final MathFunctionRepository mathFunctionRepository;
    private final MathFunctionMapper mathFunctionMapper;

    @Autowired
    public MathFunctionService(MathFunctionRepository mathFunctionRepository, MathFunctionMapper mathFunctionMapper) {
        this.mathFunctionRepository = mathFunctionRepository;
        this.mathFunctionMapper = mathFunctionMapper;
    }

    public MathFunctionDTO create(MathFunctionDTO mathFunctionDTO) {
        MathFunctionEntity mathFunctionEntity = mathFunctionMapper.toEntity(mathFunctionDTO);
        MathFunctionEntity savedEntity = mathFunctionRepository.save(mathFunctionEntity);
        return mathFunctionMapper.toDTO(savedEntity);
    }

    public MathFunctionDTO read(Long id) {
        return mathFunctionRepository.findById(id)
                .map(mathFunctionMapper::toDTO)
                .orElse(null);
    }

    public MathFunctionDTO update(MathFunctionDTO mathFunctionDTO) {
        if (mathFunctionRepository.existsById(mathFunctionDTO.getId())) {
            MathFunctionEntity mathFunctionEntity = mathFunctionMapper.toEntity(mathFunctionDTO);
            MathFunctionEntity updatedEntity = mathFunctionRepository.save(mathFunctionEntity);
            return mathFunctionMapper.toDTO(updatedEntity);
        }
        throw new RuntimeException("Function not found with id: " + mathFunctionDTO.getId());
    }

    public void delete(Long id) {
        if (mathFunctionRepository.existsById(id)) {
            mathFunctionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Function not found with id " + id);
        }
    }
}
