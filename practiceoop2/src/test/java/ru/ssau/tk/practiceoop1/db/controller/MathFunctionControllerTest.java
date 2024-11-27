package ru.ssau.tk.practiceoop1.db.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.ssau.tk.practiceoop1.db.DTO.MathFunctionDTO;
import ru.ssau.tk.practiceoop1.db.service.MathFunctionService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class MathFunctionControllerTest {

    @InjectMocks
    private MathFunctionController mathFunctionController;

    @Mock
    private MathFunctionService mathFunctionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMathFunctionsReturnsNoContent() {
        when(mathFunctionService.findFunctions(any())).thenReturn(Collections.emptyList());

        ResponseEntity<List<MathFunctionDTO>> response = mathFunctionController.getMathFunctions(null);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetMathFunctionsReturnsOk() {
        MathFunctionDTO function = new MathFunctionDTO();
        when(mathFunctionService.findFunctions(any())).thenReturn(List.of(function));

        ResponseEntity<List<MathFunctionDTO>> response = mathFunctionController.getMathFunctions(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetMathFunctionsHandlesException() {
        when(mathFunctionService.findFunctions(any())).thenThrow(new RuntimeException());

        ResponseEntity<List<MathFunctionDTO>> response = mathFunctionController.getMathFunctions(null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateMathFunctionReturnsCreated() {
        MathFunctionDTO function = new MathFunctionDTO();
        when(mathFunctionService.create(any(MathFunctionDTO.class))).thenReturn(function);

        ResponseEntity<MathFunctionDTO> response = mathFunctionController.create(function);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreateMathFunctionHandlesException() {
        MathFunctionDTO function = new MathFunctionDTO();
        when(mathFunctionService.create(any(MathFunctionDTO.class))).thenThrow(new RuntimeException());

        ResponseEntity<MathFunctionDTO> response = mathFunctionController.create(function);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testReadMathFunctionReturnsOk() {
        MathFunctionDTO function = new MathFunctionDTO();
        when(mathFunctionService.read(anyLong())).thenReturn(function);

        ResponseEntity<MathFunctionDTO> response = mathFunctionController.read(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testReadMathFunctionReturnsNotFound() {
        when(mathFunctionService.read(anyLong())).thenReturn(null);

        ResponseEntity<MathFunctionDTO> response = mathFunctionController.read(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateMathFunctionReturnsOk() {
        MathFunctionDTO function = new MathFunctionDTO();
        function.setId(1L);
        when(mathFunctionService.update(any(MathFunctionDTO.class))).thenReturn(function);

        ResponseEntity<MathFunctionDTO> response = mathFunctionController.update(1L, function);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateMathFunctionReturnsNotFound() {
        MathFunctionDTO function = new MathFunctionDTO();
        function.setId(1L);
        when(mathFunctionService.update(any(MathFunctionDTO.class))).thenThrow(new RuntimeException());

        ResponseEntity<MathFunctionDTO> response = mathFunctionController.update(1L, function);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateMathFunctionHandlesException() {
        MathFunctionDTO function = new MathFunctionDTO();
        function.setId(1L);
        when(mathFunctionService.update(any(MathFunctionDTO.class))).thenThrow(new RuntimeException());

        ResponseEntity<MathFunctionDTO> response = mathFunctionController.update(1L, function);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteMathFunctionReturnsNoContent() {
        doNothing().when(mathFunctionService).delete(anyLong());

        ResponseEntity<Void> response = mathFunctionController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteMathFunctionReturnsNotFound() {
        doThrow(new RuntimeException()).when(mathFunctionService).delete(anyLong());

        ResponseEntity<Void> response = mathFunctionController.delete(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteMathFunctionHandlesException() {
        doThrow(new RuntimeException("Deletion failed")).when(mathFunctionService).delete(anyLong());

        ResponseEntity<Void> response = mathFunctionController.delete(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
