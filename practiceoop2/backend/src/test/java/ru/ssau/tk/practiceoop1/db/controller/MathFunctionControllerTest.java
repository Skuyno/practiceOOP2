package ru.ssau.tk.practiceoop1.db.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.ssau.tk.practiceoop1.db.DTO.MathFunctionDTO;
import ru.ssau.tk.practiceoop1.db.service.MathFunctionService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MathFunctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MathFunctionController mathFunctionController;

    @MockBean
    private MathFunctionService mathFunctionService;

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
    public void testCreateMathFunctionReturnsCreated() {
        MathFunctionDTO function = new MathFunctionDTO();
        when(mathFunctionService.create(any(MathFunctionDTO.class))).thenReturn(function);

        ResponseEntity<MathFunctionDTO> response = mathFunctionController.create(function);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testReadMathFunctionReturnsOk() {
        MathFunctionDTO function = new MathFunctionDTO();
        when(mathFunctionService.read(anyLong())).thenReturn(function);

        ResponseEntity<MathFunctionDTO> response = mathFunctionController.read(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
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
    public void testDeleteMathFunctionReturnsNoContent() {
        doNothing().when(mathFunctionService).delete(anyLong());

        ResponseEntity<Void> response = mathFunctionController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
