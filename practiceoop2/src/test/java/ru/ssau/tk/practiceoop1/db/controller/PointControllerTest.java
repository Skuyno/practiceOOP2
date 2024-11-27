package ru.ssau.tk.practiceoop1.db.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.ssau.tk.practiceoop1.db.DTO.PointDTO;
import ru.ssau.tk.practiceoop1.db.service.PointService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PointControllerTest {

    @InjectMocks
    private PointController pointController;

    @Mock
    private PointService pointService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPointsSuccess() {
        Long functionId = 1L;
        List<PointDTO> points = new ArrayList<>();
        points.add(new PointDTO(1L, functionId, 2.0, 3.0));

        when(pointService.findByFunction(functionId)).thenReturn(points);

        ResponseEntity<List<PointDTO>> response = pointController.getAllPoints(functionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(points, response.getBody());
    }

    @Test
    public void testGetAllPointsNoContent() {
        Long functionId = 1L;

        when(pointService.findByFunction(functionId)).thenReturn(new ArrayList<>());

        ResponseEntity<List<PointDTO>> response = pointController.getAllPoints(functionId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testCreatePointSuccess() {
        PointDTO pointDTO = new PointDTO(null, 1L, 2.0, 3.0);
        PointDTO createdPoint = new PointDTO(1L, 1L, 2.0, 3.0);

        when(pointService.create(pointDTO)).thenReturn(createdPoint);

        ResponseEntity<PointDTO> response = pointController.create(pointDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdPoint, response.getBody());
    }

    @Test
    public void testReadPointSuccess() {
        Long id = 1L;
        PointDTO point = new PointDTO(id, 1L, 2.0, 3.0);

        when(pointService.read(id)).thenReturn(point);

        ResponseEntity<PointDTO> response = pointController.read(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(point, response.getBody());
    }

    @Test
    public void testReadPointNotFound() {
        Long id = 1L;

        when(pointService.read(id)).thenReturn(null);

        ResponseEntity<PointDTO> response = pointController.read(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdatePointSuccess() {
        Long id = 1L;
        PointDTO pointDTO = new PointDTO(id, 1L, 2.0, 3.0);
        PointDTO updatedPoint = new PointDTO(id, 1L, 4.0, 5.0);

        when(pointService.update(any(PointDTO.class))).thenReturn(updatedPoint);

        ResponseEntity<PointDTO> response = pointController.update(id, pointDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPoint, response.getBody());
    }

    @Test
    public void testDeletePointSuccess() {
        Long id = 1L;

        doNothing().when(pointService).delete(id);

        ResponseEntity<Void> response = pointController.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
