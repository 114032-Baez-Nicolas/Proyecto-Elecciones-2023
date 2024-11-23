package ar.edu.utn.frc.tup.lc.iv.clients.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeccionesClientDTO {

    @JsonProperty("seccionId")
    private Long id;

    @JsonProperty("seccionNombre")
    private String nombre;

    @JsonProperty("distritoId")
    private Long distritoId;
}
