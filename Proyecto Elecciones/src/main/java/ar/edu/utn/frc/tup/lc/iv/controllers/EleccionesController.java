package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.*;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.IEleccionesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/elecciones")
@AllArgsConstructor
public class EleccionesController {

    private final IEleccionesService eleccionesService;

    // Obtener todos los distritos o distrito por ID
    @GetMapping("/distritos")
    public ResponseEntity<List<DistritoDTO>> getDistritos(@RequestParam(required = false) Long id) {
        var distritos = eleccionesService.getDistritos(id);
        return ResponseEntity.ok(distritos);
    }

    // Obtener todos los cargos de un distrito o cargo específico de un distrito
    @GetMapping("/cargos")
    public ResponseEntity<?> getCargos(
            @RequestParam(required = false) Long distritoId,
            @RequestParam(required = false) Long cargoId
    ) {
        if (distritoId != null && cargoId != null) {
            var cargo = eleccionesService.getCargoByDistritoAndId(distritoId, cargoId);
            return ResponseEntity.ok(cargo);
        } else if (distritoId != null) {
            var cargos = eleccionesService.getCargosByDistrito(distritoId);
            return ResponseEntity.ok(cargos);
        } else {
            var cargos = eleccionesService.getAllCargos();
            return ResponseEntity.ok(cargos);
        }
    }

    // Obtener todas las secciones de un distrito o sección específica de un distrito
    @GetMapping("/secciones")
    public ResponseEntity<?> getSecciones(
            @RequestParam(required = false) Long distritoId,
            @RequestParam(required = false) Long seccionId
    ) {
        if (distritoId != null && seccionId != null) {
            var seccion = eleccionesService.getSeccionByDistritoAndId(distritoId, seccionId);
            return ResponseEntity.ok(seccion);
        } else if (distritoId != null) {
            var secciones = eleccionesService.getSeccionesByDistrito(distritoId);
            return ResponseEntity.ok(secciones);
        } else {
            var secciones = eleccionesService.getAllSecciones();
            return ResponseEntity.ok(secciones);
        }
    }

    // Obtener resultados
    @GetMapping("/distritos/{distritoId}/resultados")
    public ResponseEntity<ResultadoDistritoDTO> getResultadosByDistrito(@PathVariable Long distritoId) {
        var resultados = eleccionesService.getResultadosByDistrito(distritoId);
        return ResponseEntity.ok(resultados);
    }

    // Obtener resumen de resultados
    @GetMapping("/resultados")
    public ResponseEntity<ResultadosDTO> getResultadosNacionales() {
        ResultadosDTO resultados = eleccionesService.getResultadosNacionales();
        return ResponseEntity.ok(resultados);
    }

}
