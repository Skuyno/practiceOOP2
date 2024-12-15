package ru.ssau.tk.practiceoop1.db.repositories;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class MathFunctionRepositoryTest {

    @Autowired
    private MathFunctionRepository mathFunctionRepository;

    @Test
    void testFindByName(){
        MathFunctionEntity function1 = new MathFunctionEntity(null, "Test findByName", 1, 0.0, 10.0, null);
        mathFunctionRepository.save(function1);
        MathFunctionEntity function2 = new MathFunctionEntity(null, "Test findByName", 5, 0.0, 10.0, null);
        mathFunctionRepository.save(function2);

        List<MathFunctionEntity> functions = mathFunctionRepository.findByName("Test findByName");
        assertEquals(2, functions.size());
    }

}