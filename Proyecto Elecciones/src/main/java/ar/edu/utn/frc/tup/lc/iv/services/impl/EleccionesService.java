package ar.edu.utn.frc.tup.lc.iv.services.impl;

import ar.edu.utn.frc.tup.lc.iv.clients.EleccionesClient;
import ar.edu.utn.frc.tup.lc.iv.clients.dtos.DistritoClientDTO;
import ar.edu.utn.frc.tup.lc.iv.clients.dtos.hard.ResultadoClientDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.*;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.IEleccionesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EleccionesService implements IEleccionesService {

    private final EleccionesClient eleccionesClient;

    //Obtener todos los distritos o distrito por ID
    @Override
    public List<DistritoDTO> getDistritos(Long id) {
        if (id != null) {
            var distrito = eleccionesClient.getDistritoById(id);
            return List.of(new DistritoDTO(distrito.getId(), distrito.getNombre()));
        } else {
            var distritos = eleccionesClient.getAllDistritos();
            return distritos.stream()
                    .map(d -> new DistritoDTO(d.getId(), d.getNombre()))
                    .collect(Collectors.toList());
        }
    }

    // Obtener todos los cargos disponibles
    @Override
    public List<CargoDTO> getAllCargos() {
        var cargos = eleccionesClient.getAllCargos();
        return cargos.stream()
                .map(c -> new CargoDTO(c.getId(), c.getNombre()))
                .collect(Collectors.toList());
    }

    // Obtener todos los cargos de un distrito
    @Override
    public List<CargoDTO> getCargosByDistrito(Long distritoId) {
        var cargos = eleccionesClient.getCargosByDistrito(distritoId);
        return cargos.stream()
                .map(c -> new CargoDTO(c.getId(), c.getNombre()))
                .collect(Collectors.toList());
    }

    // Obtener un cargo específico de un distrito
    @Override
    public CargoDTO getCargoByDistritoAndId(Long distritoId, Long cargoId) {
        var cargo = eleccionesClient.getCargoByDistritoAndId(distritoId, cargoId);
        return new CargoDTO(cargo.getId(), cargo.getNombre());
    }

    // Obtener todas las secciones
    @Override
    public List<SeccionDTO> getAllSecciones() {
        var secciones = eleccionesClient.getAllSecciones();
        return secciones.stream()
                .map(s -> new SeccionDTO(s.getId(), s.getNombre()))
                .collect(Collectors.toList());
    }

    // Obtener todas las secciones de un distrito
    @Override
    public List<SeccionDTO> getSeccionesByDistrito(Long distritoId) {
        var secciones = eleccionesClient.getSeccionesByDistrito(distritoId);
        return secciones.stream()
                .map(s -> new SeccionDTO(s.getId(), s.getNombre()))
                .collect(Collectors.toList());
    }

    // Obtener una sección específica de un distrito
    @Override
    public SeccionDTO getSeccionByDistritoAndId(Long distritoId, Long seccionId) {
        var seccion = eleccionesClient.getSeccionByDistritoAndId(distritoId, seccionId);
        return new SeccionDTO(seccion.getId(), seccion.getNombre());
    }

    // Obtener resultados
    @Override
    public ResultadoDistritoDTO getResultadosByDistrito(Long distritoId) {
        List<ResultadoClientDTO> resultados = eleccionesClient.getResultadosByDistrito(distritoId);

        Map<Long, String> seccionNombres = eleccionesClient.getSeccionesByDistrito(distritoId).stream()
                .collect(Collectors.toMap(seccion -> seccion.getId(), seccion -> seccion.getNombre()));

        final int padronNacionalTotal = 27000000;

        Map<String, Integer> votosPorAgrupacion = new HashMap<>();
        Map<Long, Integer> votosPorSeccion = new HashMap<>();
        final int[] totalVotosDistrito = {0};

        for (ResultadoClientDTO resultado : resultados) {
            String claveAgrupacion = resultado.getVotosTipo().equals("POSITIVO")
                    ? String.valueOf(resultado.getAgrupacionId())
                    : resultado.getVotosTipo();

            votosPorAgrupacion.merge(claveAgrupacion, resultado.getVotosCantidad().intValue(), Integer::sum);
            votosPorSeccion.merge(resultado.getSeccionId(), resultado.getVotosCantidad().intValue(), Integer::sum);
            totalVotosDistrito[0] += resultado.getVotosCantidad();
        }

        List<ResultadoAgrupacionDTO> resultadosAgrupaciones = votosPorAgrupacion.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(entry -> {
                    String nombre = entry.getKey().matches("\\d+")
                            ? eleccionesClient.getAgrupacionById(Long.valueOf(entry.getKey())).getNombre()
                            : entry.getKey();

                    int votos = entry.getValue();
                    BigDecimal porcentaje = BigDecimal.valueOf((double) votos / totalVotosDistrito[0] * 100)
                            .setScale(2, BigDecimal.ROUND_HALF_UP);

                    return ResultadoAgrupacionDTO.builder()
                            .nombre(nombre)
                            .posicion(0)
                            .votos(votos)
                            .porcentaje(porcentaje + " %")
                            .build();
                })
                .collect(Collectors.toList());

        for (int i = 0; i < resultadosAgrupaciones.size(); i++) {
            resultadosAgrupaciones.get(i).setPosicion(i + 1);
        }

        String agrupacionGanadora = resultadosAgrupaciones.get(0).getNombre();

        List<String> secciones = votosPorSeccion.keySet().stream()
                .map(seccionNombres::get)
                .sorted()
                .collect(Collectors.toList());

        BigDecimal porcentajePadronNacional = BigDecimal.valueOf((double) totalVotosDistrito[0] / padronNacionalTotal)
                .setScale(4, BigDecimal.ROUND_HALF_UP);

        return ResultadoDistritoDTO.builder()
                .id(distritoId)
                .nombre(eleccionesClient.getDistritoById(distritoId).getNombre())
                .votosEscrutados(totalVotosDistrito[0])
                .porcentajePadronNacional(porcentajePadronNacional)
                .agrupacionGanadora(agrupacionGanadora)
                .secciones(secciones)
                .resultadosAgrupaciones(resultadosAgrupaciones)
                .build();
    }

    // Obtener resumen resultados
    @Override
    public ResultadosDTO getResultadosNacionales() {
        List<DistritoClientDTO> distritos = eleccionesClient.getAllDistritos();

        Map<String, Integer> votosNacionales = new HashMap<>();
        List<ResultadoDistritoDTO> resultadosPorDistrito = new ArrayList<>();
        final int[] totalVotosNacionales = {0};

        for (DistritoClientDTO distrito : distritos) {
            ResultadoDistritoDTO resultadoDistrito = getResultadosByDistrito(distrito.getId());

            for (ResultadoAgrupacionDTO resultado : resultadoDistrito.getResultadosAgrupaciones()) {
                votosNacionales.merge(resultado.getNombre(), resultado.getVotos(), Integer::sum);
            }

            totalVotosNacionales[0] += resultadoDistrito.getVotosEscrutados();

            resultadosPorDistrito.add(resultadoDistrito);
        }

        List<ResultadoAgrupacionDTO> resultadosNacionales = votosNacionales.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(entry -> {
                    int votos = entry.getValue();
                    BigDecimal porcentaje = BigDecimal.valueOf((double) votos / totalVotosNacionales[0] * 100)
                            .setScale(2, BigDecimal.ROUND_HALF_UP);

                    return ResultadoAgrupacionDTO.builder()
                            .nombre(entry.getKey())
                            .posicion(0)
                            .votos(votos)
                            .porcentaje(porcentaje + " %")
                            .build();
                })
                .collect(Collectors.toList());

        for (int i = 0; i < resultadosNacionales.size(); i++) {
            resultadosNacionales.get(i).setPosicion(i + 1);
        }

        String agrupacionGanadoraNacional = resultadosNacionales.get(0).getNombre();

        List<String> nombresDistritos = distritos.stream()
                .map(DistritoClientDTO::getNombre)
                .collect(Collectors.toList());

        return ResultadosDTO.builder()
                .distritos(nombresDistritos)
                .votosEscrutados(totalVotosNacionales[0])
                .agrupacionGanadora(agrupacionGanadoraNacional)
                .resultadosNacionales(resultadosNacionales)
                .resultadoDistritos(resultadosPorDistrito)
                .build();
    }

}