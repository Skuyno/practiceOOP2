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

    @GetMapping("/functions/{functionId}")
    public ResponseEntity<List<PointDTO>> getAllPoints(@PathVariable Long functionId) {
        List<PointDTO> result = pointService.findByFunction(functionId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PointDTO> create(@RequestBody PointDTO pointDTO) {
        PointDTO createdPoint = pointService.create(pointDTO);
        return new ResponseEntity<>(createdPoint, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PointDTO> read(@PathVariable Long id) {
        PointDTO point = pointService.read(id);
        return new ResponseEntity<>(point, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PointDTO> update(@PathVariable Long id, @RequestBody PointDTO pointDTO) {
        pointDTO.setId(id);
        PointDTO updatedPoint = pointService.update(pointDTO);
        return new ResponseEntity<>(updatedPoint, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pointService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
