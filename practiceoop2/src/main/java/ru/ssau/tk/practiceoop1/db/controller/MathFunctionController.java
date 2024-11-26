package ru.ssau.tk.practiceoop1.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.practiceoop1.db.DTO.MathFunctionDTO;
import ru.ssau.tk.practiceoop1.db.service.MathFunctionService;

import java.util.List;

@RestController
@RequestMapping("/api/functions")
public class MathFunctionController {

    private final MathFunctionService mathFunctionService;

    @Autowired
    public MathFunctionController(MathFunctionService mathFunctionService) {
        this.mathFunctionService = mathFunctionService;
    }

    @GetMapping("list")
    public ResponseEntity<List<MathFunctionDTO>> getMathFunctions(@RequestParam(required = false) String name) {
        try {
            List<MathFunctionDTO> result = mathFunctionService.findFunctions(name);
            return result.isEmpty()
                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<MathFunctionDTO> create(@RequestBody MathFunctionDTO mathFunctionDTO) {
        try {
            MathFunctionDTO createdFunction = mathFunctionService.create(mathFunctionDTO);
            return new ResponseEntity<>(createdFunction, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MathFunctionDTO> read(@PathVariable Long id) {
        MathFunctionDTO function = mathFunctionService.read(id);
        return function != null
                ? new ResponseEntity<>(function, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MathFunctionDTO> update(@PathVariable Long id, @RequestBody MathFunctionDTO mathFunctionDTO) {
        try {
            mathFunctionDTO.setId(id);
            MathFunctionDTO updatedFunction = mathFunctionService.update(mathFunctionDTO);
            return new ResponseEntity<>(updatedFunction, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            mathFunctionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
