package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IEleccionesService {

    //Obtener todos los distritos o distrito por ID
    List<DistritoDTO> getDistritos(Long id);

    // Obtener todos los cargos disponibles
    List<CargoDTO> getAllCargos();

    // Obtener todos los cargos de un distrito
    List<CargoDTO> getCargosByDistrito(Long distritoId);

    // Obtener un cargo específico de un distrito
    CargoDTO getCargoByDistritoAndId(Long distritoId, Long cargoId);

    // Obtener todas las secciones
    List<SeccionDTO> getAllSecciones();

    // Obtener todas las secciones de un distrito
    List<SeccionDTO> getSeccionesByDistrito(Long distritoId);

    // Obtener una sección específica de un distrito
    SeccionDTO getSeccionByDistritoAndId(Long distritoId, Long seccionId);

    // Obtener resultados
    ResultadoDistritoDTO getResultadosByDistrito(Long distritoId);

    // Obtener resumen resultados
    public ResultadosDTO getResultadosNacionales();

}
