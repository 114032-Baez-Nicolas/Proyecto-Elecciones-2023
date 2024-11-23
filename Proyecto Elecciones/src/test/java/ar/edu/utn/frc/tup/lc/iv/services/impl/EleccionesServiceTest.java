package ar.edu.utn.frc.tup.lc.iv.services.impl;

import ar.edu.utn.frc.tup.lc.iv.clients.EleccionesClient;
import ar.edu.utn.frc.tup.lc.iv.clients.dtos.CargoClientDTO;
import ar.edu.utn.frc.tup.lc.iv.clients.dtos.DistritoClientDTO;
import ar.edu.utn.frc.tup.lc.iv.clients.dtos.SeccionesClientDTO;
import ar.edu.utn.frc.tup.lc.iv.clients.dtos.hard.AgrupacionesClientDTO;
import ar.edu.utn.frc.tup.lc.iv.clients.dtos.hard.ResultadoClientDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EleccionesServiceTest {

    @Mock
    private EleccionesClient eleccionesClient;

    @InjectMocks
    private EleccionesService eleccionesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDistritosById() {
        // Arrange
        Long id = 1L;
        DistritoClientDTO distritoClientDTO = new DistritoClientDTO();
        distritoClientDTO.setId(id);
        distritoClientDTO.setNombre("Distrito 1");
        when(eleccionesClient.getDistritoById(id)).thenReturn(distritoClientDTO);

        // Act
        List<DistritoDTO> result = eleccionesService.getDistritos(id);

        // Assert
        assertEquals(1, result.size());
        assertEquals(id, result.get(0).getId());
        assertEquals("Distrito 1", result.get(0).getNombre());
    }

    // Test para el método getDistritos sin parámetros
    @Test
    public void testGetAllDistritos() {
        DistritoClientDTO distrito1 = new DistritoClientDTO();
        distrito1.setId(1L);
        distrito1.setNombre("Distrito 1");

        DistritoClientDTO distrito2 = new DistritoClientDTO();
        distrito2.setId(2L);
        distrito2.setNombre("Distrito 2");

        when(eleccionesClient.getAllDistritos()).thenReturn(List.of(distrito1, distrito2));

        List<DistritoDTO> result = eleccionesService.getDistritos(null);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Distrito 1", result.get(0).getNombre());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Distrito 2", result.get(1).getNombre());
    }

    // Test para el método getAllCargos
    @Test
    public void testGetAllCargos() {
        CargoClientDTO cargo1 = new CargoClientDTO();
        cargo1.setId(1L);
        cargo1.setNombre("Cargo 1");

        CargoClientDTO cargo2 = new CargoClientDTO();
        cargo2.setId(2L);
        cargo2.setNombre("Cargo 2");

        when(eleccionesClient.getAllCargos()).thenReturn(List.of(cargo1, cargo2));

        List<CargoDTO> result = eleccionesService.getAllCargos();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Cargo 1", result.get(0).getNombre());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Cargo 2", result.get(1).getNombre());
    }

    // Test para el método getCargosByDistrito
    @Test
    public void testGetCargosByDistrito() {
        Long distritoId = 1L;
        CargoClientDTO cargo1 = new CargoClientDTO();
        cargo1.setId(1L);
        cargo1.setNombre("Cargo 1");

        CargoClientDTO cargo2 = new CargoClientDTO();
        cargo2.setId(2L);
        cargo2.setNombre("Cargo 2");

        when(eleccionesClient.getCargosByDistrito(distritoId)).thenReturn(List.of(cargo1, cargo2));

        List<CargoDTO> result = eleccionesService.getCargosByDistrito(distritoId);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Cargo 1", result.get(0).getNombre());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Cargo 2", result.get(1).getNombre());
    }

    // Test para el método getCargoByDistritoAndId
    @Test
    public void testGetCargoByDistritoAndId() {
        Long distritoId = 1L;
        Long cargoId = 1L;
        CargoClientDTO cargoClientDTO = new CargoClientDTO();
        cargoClientDTO.setId(cargoId);
        cargoClientDTO.setNombre("Cargo 1");

        when(eleccionesClient.getCargoByDistritoAndId(distritoId, cargoId)).thenReturn(cargoClientDTO);

        CargoDTO result = eleccionesService.getCargoByDistritoAndId(distritoId, cargoId);

        assertEquals(cargoId, result.getId());
        assertEquals("Cargo 1", result.getNombre());
    }

    // Test para el método getAllSecciones
    @Test
    public void testGetAllSecciones() {
        SeccionesClientDTO seccion1 = new SeccionesClientDTO();
        seccion1.setId(1L);
        seccion1.setNombre("Seccion 1");

        SeccionesClientDTO seccion2 = new SeccionesClientDTO();
        seccion2.setId(2L);
        seccion2.setNombre("Seccion 2");

        when(eleccionesClient.getAllSecciones()).thenReturn(List.of(seccion1, seccion2));

        List<SeccionDTO> result = eleccionesService.getAllSecciones();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Seccion 1", result.get(0).getNombre());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Seccion 2", result.get(1).getNombre());
    }

    // Test para el método getSeccionesByDistrito
    @Test
    public void testGetSeccionesByDistrito() {
        Long distritoId = 1L;
        SeccionesClientDTO seccion1 = new SeccionesClientDTO();
        seccion1.setId(1L);
        seccion1.setNombre("Seccion 1");

        SeccionesClientDTO seccion2 = new SeccionesClientDTO();
        seccion2.setId(2L);
        seccion2.setNombre("Seccion 2");

        when(eleccionesClient.getSeccionesByDistrito(distritoId)).thenReturn(List.of(seccion1, seccion2));

        List<SeccionDTO> result = eleccionesService.getSeccionesByDistrito(distritoId);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Seccion 1", result.get(0).getNombre());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Seccion 2", result.get(1).getNombre());
    }

    // Test para el método getSeccionByDistritoAndId
    @Test
    public void testGetSeccionByDistritoAndId() {
        Long distritoId = 1L;
        Long seccionId = 1L;
        SeccionesClientDTO seccionClientDTO = new SeccionesClientDTO();
        seccionClientDTO.setId(seccionId);
        seccionClientDTO.setNombre("Seccion 1");

        when(eleccionesClient.getSeccionByDistritoAndId(distritoId, seccionId)).thenReturn(seccionClientDTO);

        SeccionDTO result = eleccionesService.getSeccionByDistritoAndId(distritoId, seccionId);

        assertEquals(seccionId, result.getId());
        assertEquals("Seccion 1", result.getNombre());
    }

    // Test para el método getResultadosByDistrito
    @Test
    public void testGetResultadosByDistrito() {
        Long distritoId = 1L;
        DistritoClientDTO distrito = new DistritoClientDTO();
        distrito.setId(distritoId);
        distrito.setNombre("Distrito 1");

        SeccionesClientDTO seccion1 = new SeccionesClientDTO();
        seccion1.setId(1L);
        seccion1.setNombre("Seccion 1");

        SeccionesClientDTO seccion2 = new SeccionesClientDTO();
        seccion2.setId(2L);
        seccion2.setNombre("Seccion 2");

        ResultadoClientDTO resultado1 = new ResultadoClientDTO();
        resultado1.setAgrupacionId(1L);
        resultado1.setVotosCantidad(100L);
        resultado1.setVotosTipo("POSITIVO");
        resultado1.setSeccionId(1L);

        ResultadoClientDTO resultado2 = new ResultadoClientDTO();
        resultado2.setAgrupacionId(2L);
        resultado2.setVotosCantidad(200L);
        resultado2.setVotosTipo("POSITIVO");
        resultado2.setSeccionId(2L);

        AgrupacionesClientDTO agrupacion1 = new AgrupacionesClientDTO();
        agrupacion1.setId(1L);
        agrupacion1.setNombre("Agrupacion 1");

        AgrupacionesClientDTO agrupacion2 = new AgrupacionesClientDTO();
        agrupacion2.setId(2L);
        agrupacion2.setNombre("Agrupacion 2");

        when(eleccionesClient.getDistritoById(distritoId)).thenReturn(distrito);
        when(eleccionesClient.getSeccionesByDistrito(distritoId)).thenReturn(List.of(seccion1, seccion2));
        when(eleccionesClient.getResultadosByDistrito(distritoId)).thenReturn(List.of(resultado1, resultado2));
        when(eleccionesClient.getAgrupacionById(1L)).thenReturn(agrupacion1);
        when(eleccionesClient.getAgrupacionById(2L)).thenReturn(agrupacion2);

        ResultadoDistritoDTO result = eleccionesService.getResultadosByDistrito(distritoId);

        assertEquals(distritoId, result.getId());
        assertEquals("Distrito 1", result.getNombre());
        assertEquals(300, result.getVotosEscrutados());
        assertEquals("Agrupacion 2", result.getAgrupacionGanadora());
        assertEquals(2, result.getSecciones().size());
        assertEquals(2, result.getResultadosAgrupaciones().size());
        assertEquals("Agrupacion 2", result.getResultadosAgrupaciones().get(0).getNombre());
        assertEquals("Agrupacion 1", result.getResultadosAgrupaciones().get(1).getNombre());
    }
}
