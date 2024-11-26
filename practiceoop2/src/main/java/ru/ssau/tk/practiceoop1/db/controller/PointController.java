package ru.ssau.tk.practiceoop1.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.practiceoop1.db.DTO.PointDTO;
import ru.ssau.tk.practiceoop1.db.service.PointService;

import java.util.List;

@RestController
@RequestMapping("/api/points")
public class PointController {
    private final PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/{functionId}")
    public ResponseEntity<List<PointDTO>> getAllPoints(@RequestParam(required = false) Long functionId) {
        try {
            if (functionId == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            List<PointDTO> result = pointService.findByFunction(functionId);

            return result.isEmpty()
                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<PointDTO> create(@RequestBody PointDTO pointDTO) {
        try {
            PointDTO createdPoint = pointService.create(pointDTO);
            return new ResponseEntity<>(createdPoint, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PointDTO> read(@PathVariable Long id) {
        PointDTO point = pointService.read(id);
        return point != null
                ? new ResponseEntity<>(point, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PointDTO> update(@PathVariable Long id, @RequestBody PointDTO pointDTO) {
        try {
            pointDTO.setId(id);
            PointDTO updatedPoint = pointService.update(pointDTO);
            return new ResponseEntity<>(updatedPoint, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            pointService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}