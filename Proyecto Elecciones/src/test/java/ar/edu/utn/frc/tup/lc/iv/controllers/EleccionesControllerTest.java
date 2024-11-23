package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.FileLoader;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.*;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.IEleccionesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EleccionesControllerTest {

    private static final String MOCK_DISTRITOS = "mocks/distritos.json";
    private static final String MOCK_SECCIONES = "mocks/secciones.json";
    private static final String MOCK_CARGOS = "mocks/cargos.json";
    private static final String MOCK_RESULTADOS_CORDOBA = "mocks/resultado_cordoba.json";
    private static final String MOCK_RESULTADOS_NACIONALES = "mocks/nacionales.json";

    @Autowired
    private ObjectMapper testMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IEleccionesService eleccionesService;

    @InjectMocks
    private EleccionesController eleccionesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDistritos() throws Exception {
        List<DistritoDTO> distritos = Arrays.asList(testMapper.readValue(FileLoader.loadFrom(MOCK_DISTRITOS), DistritoDTO[].class));
        MvcResult result = this.mockMvc.perform(get("/elecciones/distritos")).andDo(print()).andExpect(status().isOk())
                .andReturn();
        List<DistritoDTO> distritosResult = List.of(testMapper.readValue(result.getResponse().getContentAsString(Charset.defaultCharset()), DistritoDTO[].class));
        assertEquals(distritos, distritosResult);
    }

}