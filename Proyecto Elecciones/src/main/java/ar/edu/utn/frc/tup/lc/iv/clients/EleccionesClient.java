package ar.edu.utn.frc.tup.lc.iv.clients;

import ar.edu.utn.frc.tup.lc.iv.clients.dtos.CargoClientDTO;
import ar.edu.utn.frc.tup.lc.iv.clients.dtos.DistritoClientDTO;
import ar.edu.utn.frc.tup.lc.iv.clients.dtos.SeccionesClientDTO;
import ar.edu.utn.frc.tup.lc.iv.clients.dtos.hard.AgrupacionesClientDTO;
import ar.edu.utn.frc.tup.lc.iv.clients.dtos.hard.ResultadoClientDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class EleccionesClient {

    private final RestTemplate restTemplate;
    private final String url = "http://javaapi:8080";

    //Circuit breaker
    private static final String CB_NAME = "eleccionesClient";

    //--------(DISTRITOS)--------

    //1) Obtener todos los distritos
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "fallbackGetAllDistritos")
    public List<DistritoClientDTO> getAllDistritos() {
        String endpoint = url + "/distritos";
        DistritoClientDTO[] response = restTemplate.getForObject(endpoint, DistritoClientDTO[].class);
        return Arrays.asList(response);
    }

    //a) Fallback para obtener todos los distritos
    public List<DistritoClientDTO> fallbackGetAllDistritos(Throwable t) {
        System.err.println("Error al obtener distritos: " + t.getMessage());
        return Collections.emptyList();
    }

    //2) Obtener distrito por ID
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "fallbackGetDistritoById")
    public DistritoClientDTO getDistritoById(Long id) {
        String endpoint = url + "/distritos?distritoId=" + id;
        DistritoClientDTO[] response = restTemplate.getForObject(endpoint, DistritoClientDTO[].class);

        if (response != null && response.length > 0) {
            return response[0];
        } else {
            throw new RuntimeException("Distrito no encontrado para el ID: " + id);
        }
    }

    //a) Fallback para obtener distrito por ID
    public DistritoClientDTO fallbackGetDistritoById(Long id, Throwable t) {
        System.err.println("Error al obtener distrito por ID: " + t.getMessage());
        return new DistritoClientDTO(id, "Distrito desconocido");
    }

    //--------(CARGOS)--------

    //3) Obtener todos los cargos disponibles
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "fallbackGetAllCargos")
    public List<CargoClientDTO> getAllCargos() {
        String endpoint = url + "/cargos";
        CargoClientDTO[] response = restTemplate.getForObject(endpoint, CargoClientDTO[].class);
        return Arrays.asList(response);
    }

    //a) Fallback para obtener todos los cargos disponibles
    public List<CargoClientDTO> fallbackGetAllCargos(Throwable t) {
        System.err.println("Error al obtener cargos: " + t.getMessage());
        return Collections.emptyList();
    }

    //4) Obtener todos los cargos de un distrito
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "fallbackGetCargosByDistrito")
    public List<CargoClientDTO> getCargosByDistrito(Long distritoId) {
        String endpoint = url + "/cargos?distritoId=" + distritoId;
        CargoClientDTO[] response = restTemplate.getForObject(endpoint, CargoClientDTO[].class);
        return Arrays.asList(response);
    }

    //a) Fallback para obtener todos los cargos de un distrito
    public List<CargoClientDTO> fallbackGetCargosByDistrito(Long distritoId, Throwable t) {
        System.err.println("Error al obtener cargos por distrito: " + t.getMessage());
        return Collections.emptyList();
    }

    // Obtener un cargo específico de un distrito
    public CargoClientDTO getCargoByDistritoAndId(Long distritoId, Long cargoId) {
        String endpoint = url + "/cargos?distritoId=" + distritoId + "&cargoId=" + cargoId;
        CargoClientDTO[] response = restTemplate.getForObject(endpoint, CargoClientDTO[].class);

        if (response != null && response.length > 0) {
            return response[0];
        } else {
            throw new RuntimeException("No se encontró un cargo para distritoId=" + distritoId + " y cargoId=" + cargoId);
        }
    }

    //--------(SECCIONES)--------

    //5) Obtener todas las secciones
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "fallbackGetAllSecciones")
    public List<SeccionesClientDTO> getAllSecciones() {
        String endpoint = url + "/secciones";
        SeccionesClientDTO[] response = restTemplate.getForObject(endpoint, SeccionesClientDTO[].class);
        return Arrays.asList(response);
    }

    //a) Fallback para obtener todas las secciones
    public List<SeccionesClientDTO> fallbackGetAllSecciones(Throwable t) {
        System.err.println("Error al obtener secciones: " + t.getMessage());
        return Collections.emptyList();
    }

    //6) Obtener las secciones de un distrito
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "fallbackGetSeccionesByDistrito")
    public List<SeccionesClientDTO> getSeccionesByDistrito(Long distritoId) {
        String endpoint = url + "/secciones?distritoId=" + distritoId;
        SeccionesClientDTO[] response = restTemplate.getForObject(endpoint, SeccionesClientDTO[].class);
        return Arrays.asList(response);
    }

    //a) Fallback para obtener las secciones de un distrito
    public List<SeccionesClientDTO> fallbackGetSeccionesByDistrito(Long distritoId, Throwable t) {
        System.err.println("Error al obtener secciones por distrito: " + t.getMessage());
        return Collections.emptyList();
    }

    // Obtener una sección específica de un distrito
    public SeccionesClientDTO getSeccionByDistritoAndId(Long distritoId, Long seccionId) {
        String endpoint = url + "/secciones?seccionId=" + seccionId + "&distritoId=" + distritoId;
        SeccionesClientDTO[] response = restTemplate.getForObject(endpoint, SeccionesClientDTO[].class);

        if (response != null && response.length > 0) {
            return response[0];
        } else {
            throw new RuntimeException("No se encontró una sección para distritoId=" + distritoId + " y seccionId=" + seccionId);
        }
    }

    //--------(AGRUPACIONES)--------

    //7) Obtener agrupación por ID
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "fallbackGetAgrupacionById")
    public AgrupacionesClientDTO getAgrupacionById(Long agrupacionId) {
        String endpoint = url + "/agrupaciones/" + agrupacionId;
        return restTemplate.getForObject(endpoint, AgrupacionesClientDTO.class);
    }

    //a) Fallback para obtener agrupación por ID
    public AgrupacionesClientDTO fallbackGetAgrupacionById(Long agrupacionId, Throwable t) {
        System.err.println("Error al obtener agrupación por ID: " + t.getMessage());
        return new AgrupacionesClientDTO(agrupacionId, "Agrupación desconocida");
    }

    //--------(RESULTADOS)--------

    //8) Obtener resultados por distrito
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "fallbackGetResultadosByDistrito")
    public List<ResultadoClientDTO> getResultadosByDistrito(Long districtId) {
        String endpoint = url + "/resultados?districtId=" + districtId;
        ResultadoClientDTO[] response = restTemplate.getForObject(endpoint, ResultadoClientDTO[].class);
        return Arrays.asList(response);
    }

    //a) Fallback para obtener resultados por distrito
    public List<ResultadoClientDTO> fallbackGetResultadosByDistrito(Long districtId, Throwable t) {
        System.err.println("Error al obtener resultados por distrito: " + t.getMessage());
        return Collections.emptyList();
    }

}
