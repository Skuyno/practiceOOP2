package ru.ssau.tk.practiceoop1.db.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ssau.tk.practiceoop1.db.DTO.MathFunctionDTO;
import ru.ssau.tk.practiceoop1.db.DTO.PointDTO;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;
import ru.ssau.tk.practiceoop1.db.mapper.MathFunctionMapper;
import ru.ssau.tk.practiceoop1.db.repositories.MathFunctionRepository;
import ru.ssau.tk.practiceoop1.db.exceptions.MathFunctionNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MathFunctionService {

    private final MathFunctionRepository mathFunctionRepository;
    private final MathFunctionMapper mathFunctionMapper;
    private final PointService pointService;

    public List<MathFunctionDTO> findFunctions(String name) {
        List<MathFunctionEntity> mathFunctionEntities;

        if (name != null && !name.isEmpty()) {
            mathFunctionEntities = mathFunctionRepository.findByName(name);
        } else {
            mathFunctionEntities = mathFunctionRepository.findAll();
        }

        return mathFunctionEntities.stream()
                .map(mathFunctionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MathFunctionDTO create(MathFunctionDTO mathFunctionDTO) {
        MathFunctionEntity mathFunctionEntity = mathFunctionMapper.toEntity(mathFunctionDTO);
        MathFunctionEntity savedEntity = mathFunctionRepository.save(mathFunctionEntity);
        return mathFunctionMapper.toDTO(savedEntity);
    }

    public MathFunctionDTO read(Long id) {
        return mathFunctionRepository.findById(id)
                .map(mathFunctionMapper::toDTO)
                .orElseThrow(() -> new MathFunctionNotFoundException(id));
    }

    public MathFunctionDTO update(MathFunctionDTO mathFunctionDTO) {
        if (mathFunctionRepository.existsById(mathFunctionDTO.getId())) {
            MathFunctionEntity mathFunctionEntity = mathFunctionMapper.toEntity(mathFunctionDTO);
            MathFunctionEntity updatedEntity = mathFunctionRepository.save(mathFunctionEntity);
            return mathFunctionMapper.toDTO(updatedEntity);
        }
        throw new MathFunctionNotFoundException(mathFunctionDTO.getId());
    }

    public void delete(Long id) {
        if (mathFunctionRepository.existsById(id)) {
            mathFunctionRepository.deleteById(id);
        } else {
            throw new MathFunctionNotFoundException(id);
        }
    }

    public MathFunctionDTO operateFunctions(Long funcId1, Long funcId2, String operation) {
        MathFunctionDTO f1 = read(funcId1);
        MathFunctionDTO f2 = read(funcId2);

        List<PointDTO> p1 = pointService.findByFunction(funcId1);
        List<PointDTO> p2 = pointService.findByFunction(funcId2);

        Map<Double, Double> map1 = p1.stream().collect(Collectors.toMap(PointDTO::getX, PointDTO::getY));
        Map<Double, Double> map2 = p2.stream().collect(Collectors.toMap(PointDTO::getX, PointDTO::getY));

        Set<Double> intersection = new HashSet<>(map1.keySet());
        intersection.retainAll(map2.keySet());

        if (intersection.isEmpty()) {
            throw new RuntimeException("No matching x values to perform the operation on");
        }

        List<PointDTO> resultPoints = new ArrayList<>();
        for (Double xVal : intersection) {
            Double y1 = map1.get(xVal);
            Double y2 = map2.get(xVal);
            double yRes;
            switch (operation) {
                case "add":
                    yRes = y1 + y2;
                    break;
                case "subtract":
                    yRes = y1 - y2;
                    break;
                case "multiply":
                    yRes = y1 * y2;
                    break;
                case "divide":
                    if (y2 == 0.0) {
                        throw new ArithmeticException("Division by zero is not allowed");
                    }
                    yRes = y1 / y2;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operation: " + operation);
            }
            PointDTO rp = new PointDTO();
            rp.setX(xVal);
            rp.setY(yRes);
            resultPoints.add(rp);
        }

        double xMin = intersection.stream().min(Double::compareTo).get();
        double xMax = intersection.stream().max(Double::compareTo).get();

        MathFunctionDTO newFunc = new MathFunctionDTO();
        newFunc.setName("(" + f1.getName() + " " + operation + " " + f2.getName() + ")");
        newFunc.setCount(resultPoints.size());
        newFunc.setX_from(xMin);
        newFunc.setX_to(xMax);

        MathFunctionDTO createdFunc = create(newFunc);
        Long newFuncId = createdFunc.getId();

        for (PointDTO rp : resultPoints) {
            rp.setFunctionId(newFuncId);
            pointService.create(rp);
        }

        return read(newFuncId);
    }
}
