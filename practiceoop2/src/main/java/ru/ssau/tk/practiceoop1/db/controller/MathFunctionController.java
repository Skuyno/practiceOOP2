package ru.ssau.tk.practiceoop1.db.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.practiceoop1.db.DTO.MathFunctionDTO;
import ru.ssau.tk.practiceoop1.db.model.MathFunctionEntity;
import ru.ssau.tk.practiceoop1.db.service.MathFunctionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/functions")
public class MathFunctionController {

    private final MathFunctionService mathFunctionService;

    @GetMapping("list")
    public ResponseEntity<List<MathFunctionDTO>> getMathFunctions(@RequestParam(required = false) String name) {
        List<MathFunctionDTO> result = mathFunctionService.findFunctions(name);
        return result.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MathFunctionDTO> create(@Valid @RequestBody MathFunctionDTO mathFunctionDTO) {
        MathFunctionDTO createdFunction = mathFunctionService.create(mathFunctionDTO);
        return new ResponseEntity<>(createdFunction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MathFunctionDTO> read(@PathVariable Long id) {
        MathFunctionDTO function = mathFunctionService.read(id);
        return new ResponseEntity<>(function, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MathFunctionDTO> update(@PathVariable Long id, @RequestBody MathFunctionDTO mathFunctionDTO) {
        mathFunctionDTO.setId(id);
        MathFunctionDTO updatedFunction = mathFunctionService.update(mathFunctionDTO);
        return new ResponseEntity<>(updatedFunction, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mathFunctionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/operate")
    public ResponseEntity<?> operateFunctions(
            @RequestParam Long funcId1,
            @RequestParam Long funcId2,
            @RequestParam String operation
    ) {
        try {
            MathFunctionDTO result = mathFunctionService.operateFunctions(funcId1, funcId2, operation);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (ArithmeticException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/list-paged")
    public ResponseEntity<Page<MathFunctionDTO>> listPaged(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<MathFunctionDTO> result = mathFunctionService.findFunctionsByPage(name, page, size);
        return ResponseEntity.ok(result);
    }
}

